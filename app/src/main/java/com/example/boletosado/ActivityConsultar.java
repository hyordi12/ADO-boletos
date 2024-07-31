package com.example.boletosado;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityConsultar extends AppCompatActivity {

    private EditText etId;
    private TextView tvInfo;
    private Button btnConsultar, btnCancelar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        etId = findViewById(R.id.etId);
        tvInfo = findViewById(R.id.tvInfo);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnCancelar = findViewById(R.id.btnCancelar);

        dbHelper = new DatabaseHelper(this);

        btnConsultar.setOnClickListener(v -> consultarBoleto());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void consultarBoleto() {
        String idStr = etId.getText().toString();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID inválido.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = dbHelper.getBoletoById(id);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String origen = cursor.getString(cursor.getColumnIndex("origen"));
            @SuppressLint("Range") String destino = cursor.getString(cursor.getColumnIndex("destino"));
            @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
            @SuppressLint("Range") String hora = cursor.getString(cursor.getColumnIndex("hora"));
            @SuppressLint("Range") int total = cursor.getInt(cursor.getColumnIndex("total"));

            tvInfo.setText("ID: " + id + "\n" +
                    "Origen: " + origen + "\n" +
                    "Destino: " + destino + "\n" +
                    "Fecha: " + fecha + "\n" +
                    "Hora: " + hora + "\n" +
                    "Total: $" + total);

            cursor.close();
        } else {
            Toast.makeText(this, "No se encontró un boleto con ese ID.", Toast.LENGTH_SHORT).show();
        }
    }
}
