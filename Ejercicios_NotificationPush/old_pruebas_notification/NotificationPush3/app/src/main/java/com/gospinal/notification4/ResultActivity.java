package com.gospinal.notification4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ResultActivity extends AppCompatActivity {

    public static final String TAG = "NOTICIAS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle datos = this.getIntent().getExtras();
        if(datos != null ){
            String title = datos.getString("Title");
            String detalle = datos.getString("Detalle");
            Log.d(TAG, "onCreate: Title "+title);
            Log.d(TAG, "onCreate: Detalle "+detalle);
        }
    }
}
