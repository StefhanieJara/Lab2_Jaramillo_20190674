package com.example.lab2_jaramillo_20190674;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab2_jaramillo_20190674.dto.Profile;
import com.example.lab2_jaramillo_20190674.dto.Results;
import com.example.lab2_jaramillo_20190674.services.RandomUser;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registrarse extends AppCompatActivity {
    RandomUser randomUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        randomUser = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RandomUser.class);

        Log.d("msg-llegue0","llegue0");
        fetchProfileFromWs();
    }

    public void irAInicio(View view) {

        EditText nombre = findViewById(R.id.etNombre);
        EditText apellido = findViewById(R.id.etApellido);
        TextView usuario = findViewById(R.id.textView7);
        TextView picture = findViewById(R.id.textView8);
        String nombre1 = nombre.getText().toString();
        String apellido1 = apellido.getText().toString();
        String userName = usuario.getText().toString();
        String urlPicture = picture.getText().toString();

        String nombre_apellido = nombre1+" "+apellido1;

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("nombre_apellido", nombre_apellido);
        intent.putExtra("userName",userName);
        intent.putExtra("urlPicture",urlPicture);
        startActivity(intent);

    }

    public void fetchProfileFromWs(){
        randomUser.getResults().enqueue(new Callback<Profile>(){
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Log.d("msg-llegue0","llegue1");
                if (response.isSuccessful()) {
                    Profile profile = response.body();
                    Log.d("msg-llegue0","llegue2");

                    if (profile != null) {
                        List<Results> results = profile.getResults();

                        if(results!=null && !results.isEmpty()){
                            String nombre = results.get(0).getName().getFirst();
                            String apellido = results.get(0).getName().getLast();
                            String correo = results.get(0).getEmail();
                            String contrasenha = results.get(0).getLogin().getPassword();
                            String username = results.get(0).getLogin().getUsername();
                            String picture = results.get(0).getPicture().getThumbnail();

                            TextInputEditText etNombre = findViewById(R.id.etNombre);
                            etNombre.setText(nombre);

                            TextInputEditText etApellido = findViewById(R.id.etApellido);
                            etApellido.setText(apellido);

                            TextInputEditText etCorreo = findViewById(R.id.etCorreo);
                            etCorreo.setText(correo);

                            TextInputEditText etContraseña = findViewById(R.id.etContraseña);
                            etContraseña.setText(contrasenha);

                            TextView userName = findViewById(R.id.textView7);
                            userName.setText(username);

                            TextView urlPicture = findViewById(R.id.textView8);
                            urlPicture.setText(picture);
                        }
                    } else {
                        Log.d("msg-Error", "La lista de resultados está vacía o nula.");
                    }
                } else {
                    Log.d("msg-Error", "La respuesta no fue exitosa: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("msg-llegue0", "Error al realizar la solicitud: " + t.getMessage());
            }
        });

    }

}