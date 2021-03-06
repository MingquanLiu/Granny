package com.example.mikel.granny.Controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.mikel.granny.R;
import com.example.mikel.granny.UpdateNotiStatusActivity;
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



    public void sendNotification(int version,String title, String text) {

        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Granny";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        Notification notification;
        Intent intent = new Intent(context, UpdateNotiStatusActivity.class);
        intent.putExtra("version", version);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_action_name)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(pendingIntent)
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
                    .setContentIntent(pendingIntent)
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

    public void sendNotification(int version, String title, String text, String whatappText) {

        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Granny";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        resultIntent = new Intent(context, WhatsappAutoSelectActivity.class);
        resultIntent.putExtra("task",whatappText);
        resultIntent.putExtra("version", version + "");
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

    public void sendNotification2(int version,String title, String text,String address) {

        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Granny";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        String[] splited = address.split("\\s+");
        StringBuilder uri = new StringBuilder("google.navigation:q=");
        if(splited.length > 0) {
            for (String s : splited) {
                uri.append(s);
                uri.append("+");
            }
            uri.deleteCharAt(uri.length() - 1);
            Log.e("Google Navigation", uri.toString());
            Uri gmmIntentUri = Uri.parse(uri.toString());

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.putExtra("version",version+"");
// Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, mapIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.grandma3)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(resultPendingIntent)
                    .build();


            NM.notify(001, notification);

//        NotificationCompat.InboxStyle inboxStyle =new NotificationCompat.InboxStyle().addLine("kk").addLine("kkk");
//
//        noti.setStyle(inboxStyle);
        }
    }
}
