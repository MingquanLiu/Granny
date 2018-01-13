package com.example.mikel.granny;

import android.content.Context;
import android.util.Log;

import io.github.privacystreams.audio.Audio;
import io.github.privacystreams.audio.AudioOperators;
import io.github.privacystreams.core.Callback;
import io.github.privacystreams.core.Item;
import io.github.privacystreams.core.UQI;
import io.github.privacystreams.core.exceptions.PSException;
import io.github.privacystreams.core.purposes.Purpose;
import io.github.privacystreams.device.DeviceState;
import io.github.privacystreams.location.Geolocation;
import io.github.privacystreams.location.LatLon;

/**
 * Created by mikel on 2018/1/13.
 */

public class DataProvider {
    private static final long DURATION = 10 * 1000; // 10 seconds
    private static final long INTERVAL = 2 * 60 * 1000; // 2 minutes
    private static int deviceStatusMask = 0x1101;
    private Data currentInfo;

    private Context context;
    public DataProvider(Context context, Data currentInfo){
        this.context = context;
        this.currentInfo = currentInfo;
    }

    public void createProviders(){
        getLoudnessPeriodically();
        getDeviceState();

    }
    /**
     * Get the current location.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     */
    public void getCurrentLocation() {
        try {
            LatLon latLon = new UQI(context)
                    .getData(Geolocation.asCurrent(Geolocation.LEVEL_CITY), Purpose.UTILITY("check weather"))
                    .getFirst(Geolocation.LAT_LON);
            // Do something with geolocation
            currentInfo.setPosition(latLon);
            Log.d("Location", "" + latLon.getLatitude() + ", " + latLon.getLongitude());
        } catch (PSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get microphone loudness periodically.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.RECORD_AUDIO" />
     */
    public void getLoudnessPeriodically() {
        new UQI(context)
                .getData(Audio.recordPeriodic(DURATION, INTERVAL), Purpose.HEALTH("monitor sleep"))
                .setField("loudness", AudioOperators.calcLoudness(Audio.AUDIO_DATA))
                .forEach("loudness", new Callback<Double>() {
                    @Override
                    protected void onInput(Double loudness) {
                        // Do something with the loudness value.
                        currentInfo.setLoudness(loudness);
                        Log.d("Loudness", "" + loudness + " dB.");
                    }
                });
    }

    public void getDeviceState(){
        new UQI(context)
                .getData(DeviceState.asUpdates(INTERVAL,deviceStatusMask),Purpose.UTILITY("monitor battery and wifi")).debug();
//                .forEach(new Callback<Item>() {
//                    @Override
//                    protected void onInput(Item input) {
//
//                    }
//                });
    }
}
