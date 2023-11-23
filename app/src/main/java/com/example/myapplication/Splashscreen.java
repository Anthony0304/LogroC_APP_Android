package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splashscreen extends AppCompatActivity {
    Timer tiempo1 =new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

    TimerTask tarea = new TimerTask() {
        @Override
        public void run() {
            Intent intent = new Intent(Splashscreen.this, LoginActivity.class );
            startActivity(intent);
            finish();

        }
    };

    tiempo1.schedule(tarea,3000);
}
}