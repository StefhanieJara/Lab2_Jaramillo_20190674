package com.example.lab2_jaramillo_20190674;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab2_jaramillo_20190674.databinding.ActivityContadorBinding;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class Contador extends AppCompatActivity {

    private boolean iniciando=true;
    UUID uuid = UUID.randomUUID();

    private ActivityContadorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        Button button =findViewById(R.id.button5);
        TextView textView = findViewById(R.id.textView2);
        String numberStr = textView.getText().toString();
        int numero = Integer.parseInt(numberStr);
        button.setOnClickListener(view -> {
            if(iniciando){
                iniciando=false;
                Data dataBuilder = new Data.Builder()
                        .putInt("number", numero)
                        .build();
                WorkRequest workRequest = new OneTimeWorkRequest.Builder(ContadorWorker.class)
                        .setId(uuid)
                        .setInputData(dataBuilder)
                        .build();
                WorkManager.getInstance(Contador.this.getApplicationContext())
                        .enqueue(workRequest);

            }
        });

        WorkManager.getInstance(Contador.this.getApplicationContext())
                .getWorkInfoByIdLiveData(uuid)
                .observe(Contador.this, workInfo -> {
                    if(workInfo != null){
                        if(workInfo.getState() == WorkInfo.State.RUNNING){
                            Data progress = workInfo.getProgress();
                            int contador = progress.getInt("contador", 0);
                            Log.d("msg-test", "progress: " + contador);
                            textView.setText(String.format("%d", contador));
                        } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Data outputData = workInfo.getOutputData();
                            String texto = outputData.getString("info");
                            assert texto != null;
                            Log.d("msg-test", texto);
                        }
                    }else{
                        Log.d("msg-test", "work info == null ");
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textView = findViewById(R.id.textView2);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String uuidString = preferences.getString("uuid", null);
        if (uuidString != null) {
            UUID storedUUID = UUID.fromString(uuidString);
            observe(textView,storedUUID);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uuid", uuid.toString()); // Store the UUID as a string
        editor.apply();
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    private void observe(TextView textView, UUID uuid1){
        WorkManager.getInstance(Contador.this.getApplicationContext())
                .getWorkInfoByIdLiveData(uuid1)
                .observe(Contador.this, workInfo -> {
                    if(workInfo != null){
                        if(workInfo.getState() == WorkInfo.State.RUNNING){
                            Data progress = workInfo.getProgress();
                            int contador = progress.getInt("contador", 0);
                            Log.d("msg-test", "progress: " + contador);
                            textView.setText(String.format("%d", contador));
                        } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Data outputData = workInfo.getOutputData();
                            String texto = outputData.getString("info");
                            assert texto != null;
                            Log.d("msg-test", texto);
                        }
                    }else{
                        Log.d("msg-test", "work info == null ");
                    }
                });
    }



}