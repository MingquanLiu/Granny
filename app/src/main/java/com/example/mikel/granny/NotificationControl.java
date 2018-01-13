package com.example.mikel.granny;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotificationControl{
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = getIntent();
        String title= intent.getStringExtra("title");
        String context = intent.getStringExtra("text");
        Notification noti = new Notification.Builder(NotificationControl.this).setContentText("hello").setContentTitle("hellotoo").setSmallIcon(R.drawable.ic_launcher_background).build();
        NM.notify(0, noti);
    }
}
