package com.gospinal.notification4;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.gospinal.notification4.sharePreferences.Constantes;
import com.gospinal.notification4.sharePreferences.SharedPreferencesManager;

public class FirebaseBackgroundService extends WakefulBroadcastReceiver {

    private static final String TAG = "FirebaseService";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.d(TAG, "I'm in!!!");

        if (intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                Object value = intent.getExtras().get(key);
                Log.e("FirebaseDataReceiver", "Key: " + key + " Value: " + value);
                //SharedPreferencesManager.setSomeStringValue();
                if(key.equalsIgnoreCase("from")){
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_FROM, value.toString());
                }
                if(key.equalsIgnoreCase("gcm.notification.body") && value != null) {
                    /*Bundle bundle = new Bundle();
                    Intent backgroundIntent = new Intent(context, ResultActivity.class);
                    bundle.putString("push_message", value + "");
                    backgroundIntent.putExtras(bundle);
                    context.startService(backgroundIntent);*/
                    //mostrarNotificacion("prueba ffff", value.toString());
                }
            }
        }
    }

    private void mostrarNotificacion(String title, String body) {

        Log.d(TAG, "mostrarNotificacion: title "+title +" body "+body);

        /*Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);*/

        /*NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_event)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());*/

        String id = "messagge";

        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(context, ResultActivity.class);
        resultIntent.putExtra("Title", title);
        resultIntent.putExtra("Detalle", body);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        /*NotificationManager nm =  (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);
        }*/

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_blur_circular_black_24dp);

        String masTexto = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent molestie massa metus, nec laoreet lacus porta ac. Donec efficitur sapien nec erat interdum malesuada. Nam tempor posuere ornare. Curabitur at aliquam turpis, vitae mattis libero. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas lacinia ac odio ut pellentesque. Quisque varius metus vitae orci ornare, non congue velit porttitor. Praesent convallis in ante vitae molestie. ";

        /*builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_event)
                .setLargeIcon(icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(masTexto))
                .setContentIntent(resultPendingIntent)
                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotify = random.nextInt(8000);
        nm.notify(idNotify, builder.build());*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, id)
                .setSmallIcon(R.drawable.ic_event)
                .setContentTitle(title +"1")
                .setContentText(body+"1")
                .setLargeIcon(icon)
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(masTexto))
                .setContentInfo("jsjsjs");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());



    }


}
