package com.gospinal.notificationpush6;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

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
        String token = s;
        Log.d(TAG, "onNewToken: token" +token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "onMessageReceived: ");

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data: " + remoteMessage.getData());
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), remoteMessage.getData().get("img_url"));
        }
    }

    private void showNotification(String title, String message, String image) {

        Log.d(TAG, "showNotification: ");

        String id = "coronapp";
        //Bitmap bitmap = getBitmapfromUrl("https://previews.123rf.com/images/dstarky/dstarky1702/dstarky170200477/71558432-icono-de-internet-o-logotipo-en-estilo-de-l%C3%ADnea-moderna-pictograma-de-contorno-negro-de-alta-calidad-p.jpg");

        NotificationManager nm =  (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        createChancel(nm, id);

        nm.notify(getIdNotify(), notification(id, title, message, image).build());

    }

    private void createChancel(NotificationManager nm, String id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);
        }
    }

    private int getIdNotify(){
        Random random = new Random();
        int idNotify = random.nextInt(8000);
        return idNotify;
    }

    private NotificationCompat.Builder notification(String id, String title, String message, String image){
        Bitmap bitmap = getBitmapfromUrl(image);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_av_timer_black_24dp)
                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setContentText(message)
                .setContentIntent(callPendingIntent())
                .setContentInfo("nuevo");

        if(bitmap != null){
            builder.setLargeIcon(bitmap);
        }

        return builder;
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

    private PendingIntent callPendingIntent(){
        Intent resultIntent = new Intent(this, ResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        return resultPendingIntent;
    }
}
