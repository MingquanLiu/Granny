package com.example.mikel.granny;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
        resultIntent = new Intent(context, WhatsappAutoSelectActivity.class);
        PendingIntent resultPendingIntent =  PendingIntent.getActivity(context, 0, resultIntent, 0);
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Notification notification =  new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_action_name)
                .setContentTitle(title)
                .setContentText(text)
                .setChannelId(CHANNEL_ID)
//                .setSound(Uri.parse("android.resource://"
//                        + context.getPackageName() + "/" + R.raw.trouble))
                .setContentIntent(resultPendingIntent)
                .build();

        // Gets an instance of the NotificationManager service//

//        NotificationManager mNotificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notification.defaults |= Notification.DEFAULT_SOUND;
//        notification.sound = Uri.parse("android.resource://"
//                + context.getPackageName() + "/" + R.raw.trouble);

        NM.createNotificationChannel(mChannel);
        NM.notify(001, notification);

//        NotificationCompat.InboxStyle inboxStyle =new NotificationCompat.InboxStyle().addLine("kk").addLine("kkk");
//
//        noti.setStyle(inboxStyle);
    }
}
