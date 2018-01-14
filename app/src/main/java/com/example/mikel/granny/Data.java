package com.example.mikel.granny;

import android.util.Log;

import java.util.LinkedList;

import io.github.privacystreams.location.LatLon;

/**
 * Created by wangyixue on 2018/01/13.
 */

public class Data {
    private static Data _data;//singleton pattern
    private ApplicationController applicationController;//for observer pattern use

    private String address;//string version of the home address
    private LatLon homeLoc;//latitude and longtitude of the home address
    private LatLon location;//current location
    private int homeHour;//the hour you should get home by
    private int homeMinute;//the minute you should get home by
    private Double loudness;//test
    private String WIFIName;
    private String homeWifiName;
    private Boolean isConnected;//whether it is connected to WIFI
    private float batteryLevel;//percentage
    private Boolean isscreenon;//is the screen on
    private LinkedList<BatteryTime> batteryData;
    private double batteryETA;//at what time is the battery dead

    private Data(){
        this.location = new LatLon(0.0, 0.0);
        this.homeLoc = new LatLon(0.0, 0.0);
        this.homeMinute = 0;
        this.homeHour = 0;
        this.WIFIName = "";
        this.address = "";
        this.isConnected = false;
        this.isscreenon = false;
        this.batteryLevel = 10.0f;
        this.loudness = 0.0;
        batteryData = new LinkedList<BatteryTime>();
        this.batteryETA = 0.0;
        this.homeWifiName = "WPI-Wireless";
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

    public void setHomeAddress(String address){
        this.address = address;
    }

    public void setPosition(LatLon loc){
        location = loc;
        applicationController.infoUpdated();
    }

    public void setLoudness(Double loudness){
        this.loudness = loudness;
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
        if (batteryETA < 10){
            return 99999;
        }
        return batteryETA;
    }

    public String getHomeWifiName(){
        return this.homeWifiName;
    }

    //============Others========================

    public void logData(){
        Log.i("Home Location: ", "address: " + address + "\tHomeLoc: " + homeLoc.toString() + "\t Current Location: " + location);
        Log.i("Set Arrival Time: ", homeHour + ":" + homeMinute);
        Log.i("Connection: ", "current Wifi name: " + WIFIName + "\tHomewifi: " + homeWifiName + "\t connected: " + isConnected);
        Log.i("Battery: ", "battery level: " + batteryLevel + "\t isScreenOn: " + isscreenon + "\tbatteryETA" + batteryETA);
    }

    private void linearRegression(){
        double sumxy = 0.0, sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        int n = batteryData.size();
        for (BatteryTime e : this.batteryData){
            sumxy += e.battery * e.minuteOfTheDay;
            sumx += e.minuteOfTheDay;
            sumy += e.battery;
            sumx2 += e.minuteOfTheDay * e.minuteOfTheDay;
        }
        double batterySlope = (n*sumxy - sumx * sumy) / (n * sumx2 - sumx * sumx);
        double intercept = (sumy - batterySlope * sumx) / n;
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
