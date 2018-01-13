package com.example.mikel.granny;

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

                startActivity(new Intent(MainActivity.this, NotificationControl.class));
                startService(new Intent(MainActivity.this, ApplicationController.class));
//                openWhatsappContact1(number);


                VibrateController vibrateController = new VibrateController(getApplicationContext());
                vibrateController.vibrateForInterval(2000);

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


    public void sendNotification() {

        //Get an instance of NotificationManager//
        count++;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_action_name)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!"+ count).setPriority(Notification.VISIBILITY_PUBLIC);


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


