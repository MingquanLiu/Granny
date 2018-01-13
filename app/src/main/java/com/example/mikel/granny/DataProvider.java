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
    private static int deviceStatusMask = 1+2+4+8+16;
    private Context context;
    public DataProvider(Context context){
        this.context = context;
    }

    public void createProviders(){
        Log.e("Device State",deviceStatusMask+"");
        getLoudnessPeriodically();
        getDeviceStatePeriodically();
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
                        Log.d("Loudness", "" + loudness + " dB.");
                    }
                });
    }

    public void getDeviceStatePeriodically(){
        new UQI(context)
                .getData(DeviceState.asUpdates(INTERVAL,deviceStatusMask),Purpose.UTILITY("monitor battery and wifi"))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        String wifiName = input.getValueByField(DeviceState.WIFI_BSSID);
                        Boolean isConnected = input.getValueByField(DeviceState.IS_CONNECTED);
                        Double batteryLevel = input.getValueByField(DeviceState.BATTERY_LEVEL);
                        Boolean isScreenOn = input.getValueByField(DeviceState.IS_SCREEN_ON);
                        Log.e("DeviceState","Wifi app List:"+input.getValueByField(DeviceState.WIFI_AP_LIST)+
                                " Battery Info:"+input.getValueByField(DeviceState.BATTERY_LEVEL));
                    }
                });
    }
}
