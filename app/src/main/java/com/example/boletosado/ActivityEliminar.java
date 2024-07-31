package com.example.boletosado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityEliminar extends AppCompatActivity {

    private EditText etId;
    private Button btnEliminar, btnRegresar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);

        etId = findViewById(R.id.etId);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnRegresar = findViewById(R.id.btnRegresar);

        dbHelper = new DatabaseHelper(this);

        btnEliminar.setOnClickListener(v -> {
            int id = Integer.parseInt(etId.getText().toString());
            dbHelper.deleteBoleto(id);
            Toast.makeText(this, getString(R.string.informacion_eliminada, id), Toast.LENGTH_SHORT).show();
        });

        btnRegresar.setOnClickListener(v -> {
            startActivity(new Intent(ActivityEliminar.this, MainActivity.class));
        });
    }
}

