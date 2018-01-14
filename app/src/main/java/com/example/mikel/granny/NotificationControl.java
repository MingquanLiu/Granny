package com.example.mikel.granny;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
         NM = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    public void sendNotification(String title, String text) {
        /*AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }*/

        NotificationCompat.Builder noti = new NotificationCompat.Builder(context).setContentText(text).setContentTitle(title).setSmallIcon(R.drawable.ic_action_name);
        resultIntent = new Intent(context, WhatsappAutoSelectActivity.class);
        //resultIntent.putExtra("task", "a");
        //Log.e("Test 0",resultIntent.getStringExtra("task"));
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //stackBuilder.addParentStack(WhatsappAutoSelectActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
        //stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =  PendingIntent.getActivity(context, 0, resultIntent, 0);
        noti.setContentIntent(resultPendingIntent);

        NotificationCompat.InboxStyle inboxStyle =new NotificationCompat.InboxStyle().addLine("kk").addLine("kkk");
// Sets a title for the Inbox in expanded layout
// Moves events into the expanded layout

        noti.setStyle(inboxStyle);
        NM.notify(0, noti.build());
    }
}
