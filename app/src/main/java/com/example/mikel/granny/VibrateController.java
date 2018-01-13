package com.example.mikel.granny;

import android.content.Context;
import android.os.Vibrator;

/**
 * This is the class for controlling the vibration of your phone
 * Created by mikel on 2018/1/13.
 */

public class VibrateController {
    private Vibrator v;
    //This is the constructor of the vibrateController which intakes a Context to get the vibrate system service
    public VibrateController(Context context){
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    //millis as params to control the length of vibration
    public void vibrateForInterval(long millis){
        assert v != null;
        v.vibrate(millis);
    }
    //the array for long is in order of vibration, sleep, vibration, sleep and ...
    // the int is for repeat times, 0 means repeat indefinitely
    public void vibrateForPattern(long[] pattern,int repeat){
        v.vibrate(pattern,repeat);
    }
}
