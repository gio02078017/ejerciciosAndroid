package com.gospinal.notificationpush5;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MiFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "Notification";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d(TAG, "Mensaje recibido de: " + from);

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notificación: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "onMessageReceived: Notificación "+remoteMessage.getNotification());
            Log.d(TAG, "onMessageReceived: image "+remoteMessage.getNotification().getIcon());


            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getIcon());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data: " + remoteMessage.getData());
        }

    }

    private void showNotification(String title, String body, String image) {

        Log.d(TAG, "showNotification: ");

        String id = "messagge";

        //String id = "canal 1";

        Bitmap bitmap = getBitmapfromUrl("https://previews.123rf.com/images/dstarky/dstarky1702/dstarky170200477/71558432-icono-de-internet-o-logotipo-en-estilo-de-l%C3%ADnea-moderna-pictograma-de-contorno-negro-de-alta-calidad-p.jpg");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.ic_timer_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //.setLargeIcon(bitmap)
                .setContentInfo("dddd");

        //Log.d(TAG, "showNotification: builder "+builder);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Random random = new Random();
        int idNotify = random.nextInt(8000);
        notificationManager.notify(idNotify, builder.build());

        // notificationId is a unique int for each notification that you must define
        //notificationManager.notify(20, builder.build());



        //Bitmap bitmap = getBitmapfromUrl("https://previews.123rf.com/images/dstarky/dstarky1702/dstarky170200477/71558432-icono-de-internet-o-logotipo-en-estilo-de-l%C3%ADnea-moderna-pictograma-de-contorno-negro-de-alta-calidad-p.jpg");

        /*NotificationManager nm =  (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);
        }

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_timer_black_24dp)
                .setLargeIcon(bitmap)
                .setContentText(body)
                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotify = random.nextInt(8000);
        nm.notify(idNotify, builder.build());*/

    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            Log.e("awesome", "Error in getting notification image: " + e.getLocalizedMessage());
            return null;
        }
    }
}
