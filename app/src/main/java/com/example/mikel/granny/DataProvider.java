package com.example.mikel.granny;

import android.content.Context;
import android.util.Log;

import io.github.privacystreams.audio.Audio;
import io.github.privacystreams.audio.AudioOperators;
import io.github.privacystreams.core.Callback;
import io.github.privacystreams.core.Item;
import io.github.privacystreams.core.PStream;
import io.github.privacystreams.core.UQI;
import io.github.privacystreams.core.exceptions.PSException;
import io.github.privacystreams.core.purposes.Purpose;
import io.github.privacystreams.device.DeviceState;
import io.github.privacystreams.location.Geolocation;
import io.github.privacystreams.location.GeolocationOperators;
import io.github.privacystreams.location.LatLon;
import io.github.privacystreams.utils.Globals;

/**
 * Created by mikel on 2018/1/13.
 */

public class DataProvider {
    private static final long DURATION = 10 * 1000; // 10 seconds
    private static final long INTERVAL = 1 * 18 * 1000; // 2 minutes

    private static int deviceStatusMask = 1+2+4+8+16;
    int count = 0;
    private Context context;
    private UQI uqi;
    private Data currentInfo;


    public DataProvider(Context context){
        this.context = context;
        uqi = new UQI(context);
        this.currentInfo = Data.getData();
    }

    public void createProviders(){
        Log.e("Device State",deviceStatusMask+"");
        getCurrentLocation();
//        testLocation();
//        testCurrentLocation();
//        geofence(context);
        getLoudnessPeriodically();
        getDeviceStatePeriodically();
    }
    /**
     * Get the current location.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     */
    public void getCurrentLocation() {
//        try {
//            LatLon latLon = new UQI(context)
//                    .getData(Geolocation.asCurrent(Geolocation.LEVEL_CITY), Purpose.UTILITY("check weather"))
//                    .getFirst(Geolocation.LAT_LON);
            // Do something with geolocation
//            Log.d("Location", "" + latLon.getLatitude() + ", " + latLon.getLongitude());
//        int count =0;
        Globals.LocationConfig.useGoogleService = true;
         uqi.getData(Geolocation.asUpdates(2, Geolocation.LEVEL_BUILDING), Purpose.TEST("test"))
                 .forEach(new Callback<Item>() {
                     @Override
                     protected void onInput(Item input) {

                         count++;
                         LatLon latLon = input.getValueByField(Geolocation.LAT_LON);
                         Float speed = input.getValueByField(Geolocation.SPEED);
                         Float bearing = input.getValueByField(Geolocation.BEARING);
                         Log.e("Geolocation","LatLon:"+latLon.toString()+" Speed:"+speed+
                                 " Bearing:"+bearing+ "count:"+count);
                         currentInfo.setPosition(latLon);
                     }
                 });

//
//        } catch (PSException e) {
//            e.printStackTrace();
//        }
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

    public void getDeviceStatePeriodically(){
        new UQI(context)
                .getData(DeviceState.asUpdates(INTERVAL,deviceStatusMask),Purpose.UTILITY("monitor battery and wifi"))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        String wifiName = input.getValueByField(DeviceState.WIFI_BSSID);
                        Boolean isConnected = input.getValueByField(DeviceState.IS_CONNECTED);
                        Float batteryLevel = input.getValueByField(DeviceState.BATTERY_LEVEL);
                        Boolean isScreenOn = input.getValueByField(DeviceState.IS_SCREEN_ON);
                        Log.e("DeviceState","Wifi app List:"+input.getValueByField(DeviceState.WIFI_AP_LIST)+
                                " Battery Info:"+input.getValueByField(DeviceState.BATTERY_LEVEL));
                        currentInfo.setDeviceState(wifiName, isConnected, batteryLevel, isScreenOn);
                    }
                });
    }

    public void testLocation() {
        Globals.LocationConfig.useGoogleService = true;
        PStream locationStream = uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_EXACT), Purpose.TEST("test"))
                .setField("distorted_lat_lon", GeolocationOperators.distort(Geolocation.LAT_LON, 10))
                .setField("distortion", GeolocationOperators.distanceBetween(Geolocation.LAT_LON, "distorted_lat_lon"))
                .reuse(2);

        locationStream.debug();
        locationStream.forEach("distorted_lat_lon", new Callback<LatLon>() {
            @Override
            protected void onInput(LatLon input) {
                System.out.println(input);
            }
        });

//        try {
//            Thread.sleep(10);
//            uqi.stopAll();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    public void testCurrentLocation() {
        Globals.LocationConfig.useGoogleService = true;
        //            LatLon latLon =
        uqi
        .getData(Geolocation.asCurrent(Geolocation.LEVEL_EXACT), Purpose.TEST("test")).debug();
//                    .logOverSocket("location")
//                    .getFirst(Geolocation.LAT_LON);
//            System.out.println(latLon.toString());

    }

    private static final double CENTER_LATITUDE = 40;
    private static final double CENTER_LONGITUDE = -80;
    private static final double RADIUS = 20.0;

    /**
     * Monitor location updates and callback when in a circular area.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     */
    public void geofence(Context context) {
        new UQI(context)
                .getData(Geolocation.asUpdates(10*1000, Geolocation.LEVEL_EXACT), Purpose.GAME("notify user"))
//                .setField("inRegion", GeolocationOperators.inCircle(Geolocation.LAT_LON, CENTER_LATITUDE, CENTER_LONGITUDE, RADIUS))
                .onChange("inRegion", new Callback<Boolean>() {
                    @Override
                    protected void onInput(Boolean inRegion) {
                        // Do something when enters/leaves region.
                        Log.e("Geofence", inRegion ? "entering" : "leaving");
                    }
                });
    }
}
