package com.example.mikel.granny;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mikel.granny.Controller.GoogleNavigationController;
import com.example.mikel.granny.Controller.NotificationControl;

//import com.bumptech.glide.Glide;

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

                startService(new Intent(MainActivity.this, ApplicationController.class));
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(R.raw.gramma).into(imageView);
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

    public void initGoogleMap(){
        // Create a Uri from an intent string. Use the result to create an Intent.
//        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
        Uri gmmIntentUri = Uri.parse("google.navigation:q=fuller+lab");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }
}


