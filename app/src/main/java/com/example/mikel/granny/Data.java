package com.example.mikel.granny;

import io.github.privacystreams.location.LatLon;

/**
 * Created by wangyixue on 2018/01/13.
 */

public class Data {
    private LatLon Location;
    private Double Loudness;

    public Data(LatLon loc = null, Double loudness = 0.0){
        this.Location = loc;
        this.Loudness = loudness;
    }

    public void setPosition(LatLon loc){
        Location = loc;

    }
    public void setLoudness(Double loudness){
        Loudness = loudness;
    }
}
