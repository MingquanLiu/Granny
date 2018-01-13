package com.example.mikel.granny;

import io.github.privacystreams.location.LatLon;

/**
 * Created by wangyixue on 2018/01/13.
 */

public class Data {
    private LatLon Location;
    private Double Loudness;
    private String WIFIName;
    private Boolean isConnected;
    private float batteryLevel;
    private Boolean isScreenOn;

    public Data(){
        this.Location = null;
        this.Loudness = 0.0;
    }

    public void setPosition(LatLon loc){
        Location = loc;

    }

    public void setLoudness(Double loudness){
        Loudness = loudness;
    }

    public void setDeviceState(String wifi, Boolean isconnected, float battery, Boolean screen){
        WIFIName = wifi;
        isConnected = isconnected;
        batteryLevel = battery;
        isScreenOn = screen;
    }
}
