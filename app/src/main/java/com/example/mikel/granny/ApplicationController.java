package com.example.mikel.granny;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Calendar;

/**
 * Created by Lingrui on 1/13/2018.
 */

public class ApplicationController extends Service {
    final String tag = "AppController";
    DataProvider dataProvider;
    Data currentInfo;
    VibrateController vibrateController;
    NotificationControl notifController;


    @Override
    public void onCreate(){
        Log.e(tag,"AppController start");
        Intent init_info_intent = new Intent(this, InitInfoActivity.class);
        init_info_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(init_info_intent);
        currentInfo = Data.getData(this);
        vibrateController = new VibrateController(getApplicationContext());
        notifController = new NotificationControl(getApplicationContext());
    }

    public void infoUpdated(){
        int hour =  Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute =  Calendar.getInstance().get(Calendar.MINUTE);
        double distance = Math.sqrt((currentInfo.getHomeLat() - currentInfo.getLocation().getLatitude())*(currentInfo.getHomeLat() - currentInfo.getLocation().getLatitude()) +
                (currentInfo.getHomeLon() - currentInfo.getLocation().getLongitude())*(currentInfo.getHomeLon() - currentInfo.getLocation().getLongitude()));
        int minuteAway = (currentInfo.getHomeHour() - hour) * 60 + (currentInfo.getHomeMinute() - minute);

        //at home
        if (distance < 0.001){

        }//within 1 mile radius
        else if (distance < 0.015) {

        }//far away from home
        else{
            if (minuteAway >= 0){
                makeNotif();
            }
        }

    }




    public void makeNotif(){
        Intent intent = new Intent(this, NotificationControl.class);
        intent.putExtra("title", "batteryLow");
        intent.putExtra("text", "Go home asap!");
        startActivity(intent);
    }


    @Override
    public void onDestroy(){

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
