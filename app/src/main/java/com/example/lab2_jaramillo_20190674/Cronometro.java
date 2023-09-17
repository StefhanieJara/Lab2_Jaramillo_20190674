package com.example.lab2_jaramillo_20190674;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;

public class Cronometro extends AppCompatActivity {
    private Chronometer chronometer;

    private long pausaOfset;

    private boolean contando;
    private boolean cero = true;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);
        chronometer = findViewById(R.id.chronometer);
        Button inicio= findViewById(R.id.inicio);
        Button pararCuenta = findViewById(R.id.pararCuenta);
        Button retomarCuenta = findViewById(R.id.RetomarCuenta);
        Button limpiar = findViewById(R.id.Limpiar);

        inicio.setOnClickListener(view -> {
            if(!contando && cero){
                chronometer.setBase(SystemClock.elapsedRealtime()-pausaOfset);
                chronometer.start();
                contando = true;
                cero =false;
            }

        });

        pararCuenta.setOnClickListener(view -> {
            if(contando && !cero){
                chronometer.stop();
                pausaOfset=SystemClock.elapsedRealtime()-chronometer.getBase();
                contando=false;

            }
        });
        retomarCuenta.setOnClickListener(view -> {
            if(!contando && !cero){
                chronometer.setBase(SystemClock.elapsedRealtime()-pausaOfset);
                chronometer.start();
                contando=true;
            }
        });

        limpiar.setOnClickListener(view -> {
            chronometer.setBase(SystemClock.elapsedRealtime());
            pausaOfset=0;
            cero= true;
        });




    }
}