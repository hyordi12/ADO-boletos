package com.example.boletosado;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnVender = findViewById(R.id.btnVender);
        Button btnConsultar = findViewById(R.id.btnConsultar);
        Button btnCambiar = findViewById(R.id.btnCambiar);
        Button btnEliminar = findViewById(R.id.btnEliminar);

        btnVender.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivityVender.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivityConsultar.class)));
        btnCambiar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivityCambiar.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivityEliminar.class)));
    }
}
