package com.gospinal.notification4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.gospinal.notification4.sharePreferences.Constantes;
import com.gospinal.notification4.sharePreferences.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "NOTICIAS";

    private TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoTextView = (TextView) findViewById(R.id.infoTextView);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                if(!key.contains("google")){
                    String value = getIntent().getExtras().getString(key);
                    infoTextView.append("\n" + key + ": " + value);
                }
            }
        }

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Token: " + token);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("canal 1", "MainChannel", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(true);
            notificationChannel.setDescription("Notifications");
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{400, 200, 400});
            //notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.e("FirebaseDataOnResume", "Key: " + key + " Value: " + value);
                if(key.equalsIgnoreCase("from")){
                    if(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_FROM).equalsIgnoreCase(value.toString())){
                        Intent intent = new Intent(this, ResultActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }
    }
}
