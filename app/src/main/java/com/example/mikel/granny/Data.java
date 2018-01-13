package com.example.mikel.granny;

import io.github.privacystreams.location.LatLon;

/**
 * Created by wangyixue on 2018/01/13.
 */

public class Data {
    private LatLon location;
    private Double loudness;
//    private double battery;
//    private double wifiInfo;
    private ApplicationController applicationController;

    public Data(ApplicationController ac){
        this.applicationController = ac;
        this.location = null;
        this.loudness = 0.0;
    }

    public void setPosition(LatLon loc){
        location = loc;
        applicationController.infoUpdated();
    }


    public void setLoudness(Double loudness){
        this.loudness = loudness;
        applicationController.infoUpdated();
    }

    public LatLon getLocation(){
        return location;
    }

    public Double getLoudness(){
        return loudness;
    }
}
