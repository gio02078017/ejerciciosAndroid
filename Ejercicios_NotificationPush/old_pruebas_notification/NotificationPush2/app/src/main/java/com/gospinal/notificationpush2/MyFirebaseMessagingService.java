package com.gospinal.notificationpush2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG = "MyFirebaseMessagingService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: "+s);
        guardarToken(s);
    }

    private void guardarToken(String s) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("token");
        ref.child("gospinal").setValue(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();

        Log.d(TAG, "mensaje recibido de: "+from);

        if (remoteMessage.getNotification() != null){
            Log.d(TAG, "onMessageReceived: el titulo es "+remoteMessage.getNotification().getTitle());
            Log.d(TAG, "onMessageReceived: el detalle es "+remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData() != null ){
            Log.d(TAG, "onMessageReceived: el titulo es "+remoteMessage.getData().get("titulo"));
            Log.d(TAG, "onMessageReceived: el detalle es "+remoteMessage.getData().get("detalle"));
            Log.d(TAG, "onMessageReceived: el color es "+remoteMessage.getData().get("color"));

            String titulo = remoteMessage.getData().get("titulo");
            String detalle = remoteMessage.getData().get("detalle");

            createNotification(titulo, detalle);
        }


    }

    private void createNotification(String titulo, String detalle){
        String id = "messagge";

        NotificationManager nm =  (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);
        }

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentText(detalle)
                .setContentIntent(clickNoti())
                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotify = random.nextInt(8000);
        nm.notify(idNotify, builder.build());
    }

    private PendingIntent clickNoti(){
        Log.d(TAG, "clickNoti: Evento");
        Intent nf = new Intent(getApplicationContext(), MainActivity.class);
        nf.putExtra("color","verde");
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0, nf, 0);
    }

    public MyFirebaseMessagingService() {
        super();
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(TAG, "onDeletedMessages: ");
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Log.d(TAG, "onMessageSent: ");
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        Log.d(TAG, "onSendError: ");
    }
}
