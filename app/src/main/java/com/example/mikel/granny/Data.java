package com.example.mikel.granny;

import android.util.Log;

import java.util.LinkedList;

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
    private LinkedList<BatteryTime> batteryData;
    private double batterySlope;
    private double batteryETA;//at what time is the battery dead

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
        this.batteryLevel = 100.0f;
        this.loudness = 0.0;
        batteryData = new LinkedList<BatteryTime>();
        this.batterySlope = 0.0;
        this.batteryETA = 0.0;
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

    //====================Setters============================

    public void setHomeLoc(double lat, double lon){
        homeLoc = new LatLon(lat, lon);
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
        applicationController.infoUpdated();
    }

    public void setDeviceState(String wifi, Boolean isconnected, float battery, Boolean screen){
        WIFIName = wifi;
        isConnected = isconnected;

        //monitor the battery change
        if (batteryLevel != battery){
            batteryLevel = battery;
            batteryData.addLast(new BatteryTime(applicationController.getHour(), applicationController.getMinute(), batteryLevel));
            if (batteryData.size() > 10){
                batteryData.removeFirst();
            }
            if (batteryData.size() > 1){
                linearRegression();
            }
        }
        isscreenon = screen;
        applicationController.infoUpdated();
    }


//===========Getters==========================

    public double getHomeLat(){
        return homeLoc.getLatitude();
    }

    public double getHomeLon(){
        return homeLoc.getLongitude();
    }

    public String getAddress(){
        return address;
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

    public Boolean getIsscreenon(){
        return isscreenon;
    }

    public float getBatteryLevel(){
        return batteryLevel;
    }

    public double getBatteryLife(){
        if (batterySlope > 0){
            return 99999;
        }
        return batteryETA;
    }

    public double getBatterySlope(){
        return batterySlope;
    }



    //============Others========================
    private void linearRegression(){
        double sumxy = 0.0, sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        int n = batteryData.size();
        for (BatteryTime e : this.batteryData){
            sumxy += e.battery * e.minuteOfTheDay;
            sumx += e.minuteOfTheDay;
            sumy += e.battery;
            sumx2 += e.minuteOfTheDay * e.minuteOfTheDay;
        }
        batterySlope = (n*sumxy - sumx * sumy) / (n * sumx2 - sumx * sumx);
        double intercept = (sumy - batterySlope * sumy) / n;
        batteryETA = - intercept / batterySlope;
    }


    private class BatteryTime{
        int minuteOfTheDay;//x
        float battery;//y

        BatteryTime(int hour, int minute, float battery){
            this.minuteOfTheDay = 60 *  hour + minute;
            this.battery = battery;
        }
    }
}
