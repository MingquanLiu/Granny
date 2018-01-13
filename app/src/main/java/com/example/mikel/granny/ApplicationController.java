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


    @Override
    public void onCreate(){
        Log.e(tag,"AppController start");

        Intent init_info_intent = new Intent(this, InitInfo.class);
        init_info_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(init_info_intent);

        currentInfo = Data.getData(this);
        dataProvider = new DataProvider(getApplicationContext());
        dataProvider.createProviders();
        //TODO
        //if no record on database, initiate an activity to
        //ask user input for address
        //ask user input for expected time to arrive home
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
