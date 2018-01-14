package com.example.mikel.granny;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Calendar;
import java.util.Currency;

/**
 * Created by Lingrui on 1/13/2018.
 */

public class ApplicationController extends Service {
    final String tag = "AppController";
//    DataProvider dataProvider;
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
        int hour =  getHour();
        int minute = getMinute();
        double distance = Math.sqrt((currentInfo.getHomeLat() - currentInfo.getLocation().getLatitude())*(currentInfo.getHomeLat() - currentInfo.getLocation().getLatitude()) +
                (currentInfo.getHomeLon() - currentInfo.getLocation().getLongitude())*(currentInfo.getHomeLon() - currentInfo.getLocation().getLongitude()));
        int minuteAway = (currentInfo.getHomeHour() - hour) * 60 + (currentInfo.getHomeMinute() - minute);//positive if not yet reached the current time

        //at home
        if (distance < 0.001){
            if (minuteAway > 15 && minuteAway < 360){
                notifController.sendNotification(
                        "Oops dinner not ready yet...",
                        "You ought to tell me you are coming home this early! The food is just a few minute away from being done...");
            }else if(minuteAway < -60){
                notifController.sendNotification(
                        "WHERE DID YOU GO",
                        "Look at you taking so long to get home... Something went wrong? Are you cold? Sorry the food is already cold and I'll heat them up for ya"
                );
            }
            else if (currentInfo.getBatteryLevel() < 10){
                notifController.sendNotification(
                        "CHARGE YOUR PHONE!",
                        "Your charger is on the LEFT NEAR THE BED's HEAD! Get over and GET YOUR FOOD. "
                );
            }
            else{
                notifController.sendNotification(
                        "WELCOME HOME!",
                        "Congrats for making your way home on time my sweatheart. Come wash your hand and sit at the table!" +
                                "\n (Wanna cook this yourself? A recipe made from love)"
                );
                //wanna cook this yourself?
            }
        }//within 1 mile radius
        else if (distance < 0.015) {
            notifController.sendNotification(
                    "I SENSE YOUR PRESENCE",
                    "Oooooo I feel my grandchild nearby... time to heat up the food!"
            );
            //WhatsApp Grandma telling that you are near?
        }//far away from home
        else{
            if (minuteAway <= 0){
                notifController.sendNotification(
                        "WHERE ARE YOU",
                        "You d*** child COME HOME AT ONCE. Even your dad got back!"
                );
                //not coming home soon...?
            }else if((currentInfo.getHomeHour() * 60 + currentInfo.getHomeMinute()) - currentInfo.getBatteryLife() > 5){
                notifController.sendNotification(
                        "Uh oh your battery can't seem to survive long",
                        "I told you not to play on your phone that much! Now what >:("
                );
                //text your fam your ETA?
            }
        }
    }

    public int getHour(){
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);//current hour
    }

    public int getMinute(){
        return Calendar.getInstance().get(Calendar.MINUTE);//current minute;
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
