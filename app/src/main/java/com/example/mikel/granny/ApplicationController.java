package com.example.mikel.granny;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by Lingrui on 1/13/2018.
 */

public class ApplicationController extends Service {
    final String tag = "AppController";
    DataProvider dataProvider;
    Data currentInfo;
    VibrateController vibrateController;
    NotificationControl notifController;
    Thread thread;

    @Override
    public void onCreate(){
        Log.e(tag,"AppController start");
        Intent init_info_intent = new Intent(this, InitInfoActivity.class);
        init_info_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(init_info_intent);
        currentInfo = Data.getData(this);
        vibrateController = new VibrateController(getApplicationContext());
        notifController = new NotificationControl(getApplicationContext());

        //double d = getTravelInfo(42.2746, 71.8063, "Time Square");
        Thread t = new Thread(new Calculate(42.2746, -71.8063, "Time Square"));
        t.start();
    }

    public void infoUpdated(){
        int hour =  Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute =  Calendar.getInstance().get(Calendar.MINUTE);
        double distance = Math.sqrt((currentInfo.getHomeLat() - currentInfo.getLocation().getLatitude())*(currentInfo.getHomeLat() - currentInfo.getLocation().getLatitude()) +
                (currentInfo.getHomeLon() - currentInfo.getLocation().getLongitude())*(currentInfo.getHomeLon() - currentInfo.getLocation().getLongitude()));
        int minuteAway = (currentInfo.getHomeHour() - hour) * 60 + (currentInfo.getHomeMinute() - minute);

        //at home
        if (distance < 0.001){

        }//within 1 mile radius
        else if (distance < 0.015) {

        }//far away from home
        else{
            if (minuteAway >= 0){

            }
        }

    }

    @Override
    public void onDestroy(){

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}



class Calculate implements  Runnable{
    private double currentlat;
    private double currentlng;
    private String destinationAddress;

    public Calculate(double lat, double lng, String dest){
        currentlat = lat;
        currentlng = lng;
        destinationAddress = dest;
    }
    public void run(){
        StringBuilder stringbuilder = new StringBuilder();
        double estimatetime;

        try{
            HttpsURLConnection urlConnection = null;
            destinationAddress = destinationAddress.replaceAll(" ", "+");
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+currentlat+","+currentlng+"&destinations="+destinationAddress+"&key=AIzaSyB9iyYjFvVw4KqOB_c0fOqc2jhibdKQnqo";
//            HttpPost httppost = new HttpPost(url);
//            HttpClient client = new DefaultHttpClient();
//            HttpResponse response;

            URL urlObj = new URL(url);
            urlConnection = (HttpsURLConnection) urlObj.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */ );
            urlConnection.setConnectTimeout(15000 /* milliseconds */ );
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            InputStreamReader stream = new InputStreamReader(urlConnection.getInputStream());



            Log.e("granny", "Started");

//                response = client.execute(httppost);
//                HttpEntity entity = response.getEntity();

            int b;
            while((b = stream.read()) != -1) {
                stringbuilder.append((char) b);
            }
            //Log.e("granny", stringbuilder.toString());
        } catch (MalformedURLException e) {
            Log.e("Teg", "Error processing Distance Matrix API URL");


        } catch ( IOException e){

        }

        //Log.e("granny", stringbuilder.toString());

        JSONObject jsonObject;
        try{
            jsonObject  = new JSONObject(stringbuilder.toString());
            JSONObject rows = jsonObject.getJSONArray("rows").getJSONObject(0);
            JSONObject elements = rows.getJSONArray("elements").getJSONObject(0);
            JSONObject distance = elements.getJSONObject("distance");
            double dist = distance.getDouble("value");
            JSONObject duration = elements.getJSONObject("duration");
            double dur = duration.getDouble("value");
            Log.e("grannyrows: ", elements.toString());
            Log.e("grannyduration: ", "is "+dur);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
