package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button btnStart = findViewById(R.id.btnStart);
        Button btnHelp = findViewById(R.id.btnHelp);

        // Configura el clic del botón "Iniciar"
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirige a la pantalla de inicio de sesión
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        });

        // Configura el clic del botón "Ayuda" (por ahora no hace nada)
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Puedes agregar lógica adicional aquí según tus requisitos
            }
        });
    }
}
