package com.example.mikel.granny;

import io.github.privacystreams.location.LatLon;

/**
 * Created by wangyixue on 2018/01/13.
 */

public class Data {
    private static Data _data;

    private String address;
    private LatLon homeLoc;
    private LatLon location;
    private float speed;
    private int homeHour;
    private int homeMinute;
    private Double loudness;
    private ApplicationController applicationController;
    private String WIFIName;
    private Boolean isConnected;
    private float batteryLevel;
    private Boolean isscreenon;

    private Data(){
        this.location = new LatLon(0.0, 0.0);
        this.homeLoc = new LatLon(0.0, 0.0);
        //this.homeLoc = new LatLon(39.0, 116.0);
        this.speed = 0;
        this.homeMinute = 0;
        this.homeHour = 0;
        this.WIFIName = "";
        this.address = "";
        this.isConnected = false;
        this.isscreenon = false;
        this.batteryLevel = 0.0f;
        this.loudness = 0.0;
    }

    /**
     * Single instance of Data
     * @return
     */
    public static Data getData(){
        if(_data == null){
            _data = new Data();
        }
        return _data;
    }

    /**
     * Set the given ApplicationController as the listener of the class
     * @param ac
     * @return
     */
    public static Data getData(ApplicationController ac){
        getData().applicationController = ac;
        return _data;
    }


    public void setAddress(String a){
        this.address = a;
    }

    public String getAddress(){
        return address;
    }

    public void setHomeLoc(double lat, double lon){
        homeLoc = new LatLon(lat, lon);
    }

    public double getHomeLat(){
        return homeLoc.getLatitude();
    }

    public double getHomeLon(){
        return homeLoc.getLongitude();
    }

    public void setHomeTime(int hour, int minute){
        this.homeHour = hour;
        this.homeMinute = minute;
    }
    public void setPosition(LatLon loc){
        location = loc;
        applicationController.infoUpdated();
    }


    public void setLoudness(Double loudness){
        this.loudness = loudness;
        applicationController.infoUpdated();
    }

    public void setSpeed(Float speed){
        this.speed = speed;
    }



    public LatLon getLocation(){
        return location;
    }

    public Double getLoudness(){
        return loudness;
    }


    public int getHomeHour(){
        return homeHour;
    }

    public int getHomeMinute(){
        return homeMinute;
    }

    public String getWIFIName(){
        return WIFIName;
    }

    public Boolean getConnectionStatus(){
        return isConnected;
    }

    public Boolean isScreenOn(){
        return isscreenon;
    }

    public float getBatteryLevel(){
        return batteryLevel;
    }

    public void setDeviceState(String wifi, Boolean isconnected, float battery, Boolean screen){
        WIFIName = wifi;
        isConnected = isconnected;
        batteryLevel = battery;
        isscreenon = screen;
    }
}
