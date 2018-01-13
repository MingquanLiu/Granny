package com.example.mikel.granny;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Notification;
import android.app.NotificationManager;
import android.widget.Toast;

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
    ApplicationController applicationController;
    final String number = "4129446375";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


//                startService(new Intent(MainActivity.this, ApplicationController.class));
//                openWhatsappContact1(number);
                Intent intent = new Intent(MainActivity.this,WhatsappAutoSelect.class);
                intent.putExtra("task"," ");
                startActivity(intent);
//                Log.e(tag,"I am here");
                //dataProvider= new DataProvider(getApplicationContext(), applicationController.currentInfo);
                //dataProvider.createProviders();

            }
        });
    }



}
