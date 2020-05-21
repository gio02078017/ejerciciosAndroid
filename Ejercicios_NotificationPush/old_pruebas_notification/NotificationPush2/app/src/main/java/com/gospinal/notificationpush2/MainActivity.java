package com.gospinal.notificationpush2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    Button especifico, atopico;

    private TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        especifico = findViewById(R.id.btn_especifico);
        atopico = findViewById(R.id.btn_atopico);

        infoTextView = (TextView) findViewById(R.id.infoTextView);

        FirebaseMessaging.getInstance().subscribeToTopic("enviaratodos").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "suscrito a enviar a todos!", Toast.LENGTH_SHORT).show();
            }
        });

        especifico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarEspecifico();
            }
        });

        atopico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarTopico();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle datos = this.getIntent().getExtras();
        if(datos != null ){
            String color = datos.getString("color");
            Log.d(TAG, "onCreate: color "+color);
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                infoTextView.append("\n" + key + ": " + value);
            }
        }
    }

    private void llamarEspecifico() {
        Log.d(TAG, "llamarEspecifico: ");
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try{

            String token ="cPdeQllU6ZY:APA91bGri0MwqrrIG6ViJT-Qo1XoOLZTEazkMK7hu9hM9H8MzvuzlP9thsxXlhKY1Giw_Yz4JONqdOicoxJf-ivEG93pL__3BMKSLl8dLWOqgTi_vgcowKNYlFsgicudWaZbmwan6b1A";
            json.put("to", token);
            JSONObject notification = new JSONObject();

            notification.put("titulo", "soy un titulo :)");
            notification.put("detalle", "soy un detalle :(");

            json.put("data", notification);

            String URL = "https://fcm.googleapis.com/fcm/send";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, json, null, null){
                @Override
                public Map<String, String> getHeaders()  {
                    Map<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");
                    header.put("Authorization","key=AAAAaFVQngw:APA91bHIihHWKGQSS6krN4FvYtdv1SWDF71acIac9r8d6y4Z-284Qv5M6C3ZwSF_kCjgFTu0TritVBtH8D5l-27epZ7GJwulqnQRV8LfOY6rny0gprIlUx8UZ9WzFa7VqovDUYzHZgP7");
                    return header;
                }
            };

            myRequest.add(request);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void llamarTopico() {
        Log.d(TAG, "llamarTopico: ");
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try{

            //String token ="cPdeQllU6ZY:APA91bGri0MwqrrIG6ViJT-Qo1XoOLZTEazkMK7hu9hM9H8MzvuzlP9thsxXlhKY1Giw_Yz4JONqdOicoxJf-ivEG93pL__3BMKSLl8dLWOqgTi_vgcowKNYlFsgicudWaZbmwan6b1A";
            json.put("to", "/topics/enviaratodos");
            JSONObject notification = new JSONObject();

            notification.put("titulo", "soy un titulo :)");
            notification.put("detalle", "soy un detalle :(");

            json.put("data", notification);

            String URL = "https://fcm.googleapis.com/fcm/send";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, json, null, null){
                @Override
                public Map<String, String> getHeaders()  {
                    Map<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");
                    header.put("Authorization","key=AAAAaFVQngw:APA91bHIihHWKGQSS6krN4FvYtdv1SWDF71acIac9r8d6y4Z-284Qv5M6C3ZwSF_kCjgFTu0TritVBtH8D5l-27epZ7GJwulqnQRV8LfOY6rny0gprIlUx8UZ9WzFa7VqovDUYzHZgP7");
                    return header;
                }
            };

            myRequest.add(request);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
