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

    @Override
    public void onCreate(){
        Log.e(tag,"AppController start");
        //TODO
        //if no record on database, initiate an activity to
        //ask user input for address
        //ask user input for expected time to arrive home
        //proceed to monitor user location using cell tower
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
