package com.example.boletosado;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ActivityCambiar extends AppCompatActivity {

    private EditText etFecha, etHora, etId;
    private Spinner spOrigen, spDestino;
    private TextView tvTotal;
    private Button btnActualizar, btnCancelar;
    private DatabaseHelper dbHelper;

    private int precioOrigen, precioDestino;

    private static final String[] CIUDADES_ORIGEN = {"Merida", "Cancun", "Ciudad de Mexico", "Aguascalientes", "Nuevo Leon"};
    private static final String[] CIUDADES_DESTINO = {"Chihuahua", "Ciudad Juarez", "Jalisco", "Coahuila", "Baja California"};
    private static final int[] PRECIOS_ORIGEN = {100, 150, 200, 250, 300};
    private static final int[] PRECIOS_DESTINO = {120, 170, 220, 270, 320};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar);

        etId = findViewById(R.id.etId);
        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);
        spOrigen = findViewById(R.id.spOrigen);
        spDestino = findViewById(R.id.spDestino);
        tvTotal = findViewById(R.id.tvTotal);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnCancelar = findViewById(R.id.btnCancelar);

        dbHelper = new DatabaseHelper(this);


        ArrayAdapter<String> adapterOrigen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CIUDADES_ORIGEN);
        adapterOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrigen.setAdapter(adapterOrigen);

        ArrayAdapter<String> adapterDestino = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CIUDADES_DESTINO);
        adapterDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDestino.setAdapter(adapterDestino);

        spOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                precioOrigen = PRECIOS_ORIGEN[position];
                calcularTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                precioDestino = PRECIOS_DESTINO[position];
                calcularTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        etFecha.setOnClickListener(v -> showDatePicker());
        etHora.setOnClickListener(v -> showTimePicker());

        btnActualizar.setOnClickListener(v -> actualizarBoleto());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void calcularTotal() {
        int total = precioOrigen + precioDestino;
        tvTotal.setText("Total: $" + total);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> etFecha.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1), year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> etHora.setText(hourOfDay + ":" + minute1), hour, minute, true);
        timePickerDialog.show();
    }

    private void actualizarBoleto() {
        String idStr = etId.getText().toString();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idStr);
        String origen = spOrigen.getSelectedItem().toString();
        String destino = spDestino.getSelectedItem().toString();
        String fecha = etFecha.getText().toString();
        String hora = etHora.getText().toString();
        int total = precioOrigen + precioDestino;

        if (origen.isEmpty() || destino.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.updateBoleto(id, origen, destino, fecha, hora, total);
        Toast.makeText(this, "Boleto actualizado correctamente.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
