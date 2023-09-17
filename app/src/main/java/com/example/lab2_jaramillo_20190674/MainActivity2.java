package com.example.lab2_jaramillo_20190674;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent1 = getIntent();
        String nombre_apellido = intent1.getStringExtra("nombre_apellido");
        String user_name = intent1.getStringExtra("userName");
        String urlPicture = intent1.getStringExtra("urlPicture");

        TextView nombreApellido = findViewById(R.id.NombreApellido);
        nombreApellido.setText(nombre_apellido);

        TextView userName = findViewById(R.id.Usuario);
        userName.setText(user_name);

        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this)
                .load(urlPicture)
                .placeholder(R.drawable.persona)
                .into(imageView);
    }


    public void cronometro(View view){
        Intent intent = new Intent(this, Cronometro.class);
        startActivity(intent);
    }

    public void Contador(View view){
        Intent intent = new Intent(this, Contador.class);
        startActivity(intent);
    }
}