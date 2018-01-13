package com.example.mikel.granny;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by mikel on 2018/1/13.
 */

public class VibrateController {
    private Context context;
    Vibrator v;
    public VibrateController(Context context){
        this.context = context;
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    public void vibrateForInterval(long millis){
        assert v != null;
        v.vibrate(millis);
    }

    public void vibrateForPattern(long[] pattern,int repeat){
        v.vibrate(pattern,repeat);
    }
}
