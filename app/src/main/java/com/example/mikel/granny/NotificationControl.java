package com.example.mikel.granny;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NotificationControl extends Activity{
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

//        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent intent = getIntent();
////        String title= intent.getStringExtra("title");
////        String context = intent.getStringExtra("text");
//        Notification noti = new Notification.Builder(getApplicationContext()).setContentText("aaa").setContentTitle("bbb").setSmallIcon(R.drawable.ic_action_name).build();
//        NM.notify(0, noti);
        sendNotification();
    }

    public void sendNotification() {

        //Get an instance of NotificationManager//
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_action_name)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!").setPriority(Notification.VISIBILITY_PUBLIC);


        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Log.e("Notification","In Notification");
        // When you issue multiple notifications about the same type of event,
        // it’s best practice for your app to try to update an existing notification
        // with this new information, rather than immediately creating a new notification.
        // If you want to update this notification at a later date, you need to assign it an ID.
        // You can then use this ID whenever you issue a subsequent notification.
        // If the previous notification is still visible, the system will update this existing notification,
        // rather than create a new one. In this example, the notification’s ID is 001//

        mNotificationManager.notify(001, mBuilder.build());
    }
}
