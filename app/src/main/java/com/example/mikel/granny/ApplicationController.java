package com.example.mikel.granny;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Lingrui on 1/13/2018.
 */

public class ApplicationController extends Service {
    final String tag = "AppController";
    DataProvider dataProvider;
    Data currentInfo;
    //NotificationController NotifController;


    @Override
    public void onCreate(){
        Log.e(tag,"AppController start");

        Intent init_info_intent = new Intent(this, InitInfo.class);
        init_info_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(init_info_intent);

        currentInfo = Data.getData(this);



        //TODO
        //if no record on database, initiate an activity to
        //ask user input for address
        //ask user input for expected time to arrive home
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


    public void infoUpdated(){
        //TODO
        //check Data
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
