package com.example.mikel.granny;

import io.github.privacystreams.location.LatLon;

/**
 * Created by wangyixue on 2018/01/13.
 */

public class Data {
    private static Data _data;

    private String address;
    private String homeTime;
    private LatLon location;
    private Double loudness;
    private ApplicationController applicationController;
    private String WIFIName;
    private Boolean isConnected;
    private float batteryLevel;
    private Boolean isScreenOn;

    private Data(){
        this.location = null;
        this.loudness = 0.0;
    }
//
//    public Data(ApplicationController ac){
//        this.applicationController = ac;
//        this.location = null;
//        this.loudness = 0.0;
//    }

    public static Data getData(){
        if(_data == null){
            _data = new Data();
        }
        return _data;
    }

    public static Data getData(ApplicationController ac){
        getData().applicationController = ac;
        return _data;
    }

    public void setPosition(LatLon loc){
        location = loc;
        applicationController.infoUpdated();
    }


    public void setLoudness(Double loudness){
        this.loudness = loudness;
        applicationController.infoUpdated();
    }

    public void setAddress(String a){
        this.address = a;
    }

    public void setHomeTime(String time){
        this.homeTime = time;
    }

    public LatLon getLocation(){
        return location;
    }

    public Double getLoudness(){
        return loudness;
    }

    public String getAddress(){
        return address;
    }

    public String getHomeTime(){
        return homeTime;
    }

    public void setDeviceState(String wifi, Boolean isconnected, float battery, Boolean screen){
        WIFIName = wifi;
        isConnected = isconnected;
        batteryLevel = battery;
        isScreenOn = screen;
    }
}
