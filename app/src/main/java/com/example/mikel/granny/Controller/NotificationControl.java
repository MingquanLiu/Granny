package com.example.mikel.granny.Controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.mikel.granny.R;
import com.example.mikel.granny.WhatsappAutoSelectActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationControl {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private NotificationManager NM;
    private Context context;
    Intent resultIntent;


    public NotificationControl(Context context){
        this.context =  context;
         NM = (NotificationManager) this.context.getSystemService(NOTIFICATION_SERVICE);
    }



    public void sendNotification(String title, String text) {

        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Granny";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_action_name)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setChannelId(CHANNEL_ID)
                    .build();


//        NotificationManager mNotificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NM.createNotificationChannel(mChannel);
        }else{
            notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_action_name)
                    .setContentTitle(title)
                    .setContentText(text)
//                    .setChannelId(CHANNEL_ID)
                    .build();
        }
//        notification.defaults |= Notification.DEFAULT_SOUND;
//        notification.sound = Uri.parse("android.resource://"
//                + context.getPackageName() + "/" + R.raw.trouble);

        NM.notify(001, notification);

//        NotificationCompat.InboxStyle inboxStyle =new NotificationCompat.InboxStyle().addLine("kk").addLine("kkk");
//
//        noti.setStyle(inboxStyle);
    }

    public void sendNotification(String title, String text, String whatappText) {

        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Granny";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        resultIntent = new Intent(context, WhatsappAutoSelectActivity.class);
        resultIntent.putExtra("task",whatappText);
        PendingIntent resultPendingIntent =  PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.grandma3)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(resultPendingIntent)
                    .build();


//        NotificationManager mNotificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NM.createNotificationChannel(mChannel);
        }else{
            notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.grandma3)
                    .setContentTitle(title)
                    .setContentText(text)
//                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(resultPendingIntent)
                    .build();
        }
//        notification.defaults |= Notification.DEFAULT_SOUND;
//        notification.sound = Uri.parse("android.resource://"
//                + context.getPackageName() + "/" + R.raw.trouble);

        NM.notify(001, notification);

//        NotificationCompat.InboxStyle inboxStyle =new NotificationCompat.InboxStyle().addLine("kk").addLine("kkk");
//
//        noti.setStyle(inboxStyle);
    }
}
