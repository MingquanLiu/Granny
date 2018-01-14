package com.example.mikel.granny;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Notification;
import android.app.NotificationManager;
import android.widget.Toast;

import java.io.IOException;

import io.github.privacystreams.audio.Audio;
import io.github.privacystreams.audio.AudioOperators;
import io.github.privacystreams.core.Callback;
import io.github.privacystreams.core.UQI;
import io.github.privacystreams.core.exceptions.PSException;
import io.github.privacystreams.core.purposes.Purpose;
import io.github.privacystreams.location.Geolocation;
import io.github.privacystreams.location.LatLon;

public class MainActivity extends AppCompatActivity {

    final String tag = "Main Activity";
    //DataProvider dataProvider;

    final String number = "4129446375";
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                NotificationControl controller = new NotificationControl(MainActivity.this);
                controller.sendNotification("My notification", "Hello World!");
//                sendNotification();
                startService(new Intent(MainActivity.this, ApplicationController.class));
//                openWhatsappContact1(number);


//                VibrateController vibrateController = new VibrateController(getApplicationContext());
//                vibrateController.vibrateForInterval(2000);

                WallpaperController wallpaperController = new WallpaperController(getApplicationContext());
                try {
                    wallpaperController.changeWallPaper();
                } catch (IOException e) {
                    e.printStackTrace();
                }


//                Intent intent = new Intent(MainActivity.this,WhatsappAutoSelectActivity.class);
//                intent.putExtra("task"," ");
//                startActivity(intent);

//                Log.e(tag,"I am here");
                //dataProvider= new DataProvider(getApplicationContext(), applicationController.currentInfo);
                //dataProvider.createProviders();

            }
        });
    }


//    public void sendNotification() {
//
//        //Get an instance of NotificationManager//
//        String CHANNEL_ID = "my_channel_01";// The id of the channel.
//        CharSequence name = "Granny";// The user-visible name of the channel.
//        int importance = NotificationManager.IMPORTANCE_HIGH;
//        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//        Notification notification =  new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_action_name)
//                .setContentTitle("My notification")
//                .setContentText("Hello World!"+ count)
//                .setChannelId(CHANNEL_ID)
//                .build();
//
//        // Gets an instance of the NotificationManager service//
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.createNotificationChannel(mChannel);
//        mNotificationManager.notify(001, notification);
//    }
}


