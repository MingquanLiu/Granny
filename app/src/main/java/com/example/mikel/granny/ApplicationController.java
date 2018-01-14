package com.example.mikel.granny;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mikel.granny.Controller.NotificationControl;
import com.example.mikel.granny.Controller.VibrateController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Lingrui on 1/13/2018.
 */

public class ApplicationController extends Service {
    final String tag = "AppController";
//    DataProvider dataProvider;
    Data currentInfo;
    VibrateController vibrateController;
    NotificationControl notifController;
    WallpaperController wallpaperController;
    private int min = 0;

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
        wallpaperController = new WallpaperController(getApplicationContext());

        //double d = getTravelInfo(42.2746, 71.8063, "Time Square");
        Thread t = new Thread(new Calculate(42.2746, -71.8063, "Time Square"));
        t.start();
    }

    public void infoUpdated(){
        int hour =  getHour();//current hour
        int minute = getMinute();//current minute
        double distance = Math.sqrt((currentInfo.getHomeLat() - currentInfo.getLocation().getLatitude())*(currentInfo.getHomeLat() - currentInfo.getLocation().getLatitude()) +
                (currentInfo.getHomeLon() - currentInfo.getLocation().getLongitude())*(currentInfo.getHomeLon() - currentInfo.getLocation().getLongitude()));//direct distance to home
        int minuteAway = (currentInfo.getHomeHour() - hour) * 60 + (currentInfo.getHomeMinute() - minute);//positive if not yet reached the set time

        //at home
        if (distance < 0.001 && currentInfo.getHomeWifiName().equals(currentInfo.getWIFIName())){
            if (minuteAway > 15 && minuteAway < 360){
                getHomeEarly();
            }else if(minuteAway < -60){
                getHomePrettyLate();
            }
            else if (currentInfo.getBatteryLevel() < 10){
                getHomeLowBattery();
            }
            else{
                getHomeDefault();
            }
        }//within 1 mile radius
        else if (distance < 0.015) {
            getNearHome();
        }//far away from home
        else{
            if (minuteAway <= 0 && !currentInfo.getConnectionStatus()){
                shouldBeHomeButNot_OnRoad();
            }else if(minuteAway<=0){
                shouldBeHomeButNot_OnWifi();
            } else if((currentInfo.getHomeHour() * 60 + currentInfo.getHomeMinute()) - currentInfo.getBatteryLife() > 5){
                batteryDyingAwayFromHome();
            }
        }
    }

    public int getHour(){
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);//current hour
    }

    public int getMinute(){
        return Calendar.getInstance().get(Calendar.MINUTE);//current minute;
    }

    private void getHomeEarly(){
        notifController.sendNotification(
                "Oops dinner not ready yet...",
                "You ought to tell me you are coming home this early! The food is just a few minute away from being done...");
        long[] pattern = {500, 300};
        vibrateController.vibrateForPattern(pattern, 3);
        try{
            wallpaperController.changeWallPaper(3);
        }catch (Exception e){
            System.err.println("Can't change to wallpaper" + 3);
        }
    }

    private void getHomePrettyLate(){
        notifController.sendNotification(
                "WHERE DID YOU GO",
                "Look at you taking so long to get home... Something went wrong? Are you cold? Sorry the food is already cold and I'll heat them up for ya"
        );
        vibrateController.vibrateForInterval(2000);
        try{
            wallpaperController.changeWallPaper(2);
        }catch (Exception e){
            System.err.println("Can't change to wallpaper " + 2);
        }
    }

    private void getHomeLowBattery(){
        notifController.sendNotification(
                "CHARGE YOUR PHONE!",
                "Your charger is on the LEFT NEAR THE BED's HEAD! Get over and GET YOUR FOOD. "
        );
        vibrateController.vibrateForInterval(3000);
    }

    private void getHomeDefault(){
        notifController.sendNotification(
                "WELCOME HOME!",
                "Congrats for making your way home on time my sweatheart. Come wash your hand and sit at the table!" +
                        "\n (Wanna cook this yourself? A recipe made from love)"
        );
        vibrateController.vibrateForInterval(1000);
        try{
            wallpaperController.changeWallPaper(5);
        }catch (Exception e){
            System.err.println("Can't change to wallpaper " + 5);
        }
        //wanna cook this yourself? Recipe recommendation
    }

    private void getNearHome(){
        notifController.sendNotification(
                "I SENSE YOUR PRESENCE",
                "Oooooo I feel my grandchild nearby... time to heat up the food!\n" +
                        "(Wanna tell Grandma you are near?)", "Ma I am almost home! "
        );
        long[] pattern = {500, 300};
        vibrateController.vibrateForPattern(pattern, 3);
        try{
            wallpaperController.changeWallPaper(6);
        }catch (Exception e){
            System.err.println("Can't change to wallpaper " + 6);
        }

    }

    private void shouldBeHomeButNot_OnRoad(){
        notifController.sendNotification(
                "WHERE ARE YOU",
                "You d*** child COME HOME AT ONCE. Even your dad got back!",
                "Ma sorry I am late... My sincere apology for not being able to tell you earlier. Please eat without me!"
        );
        long[] pattern = {500, 300};
        vibrateController.vibrateForPattern(pattern, 3);
        vibrateController.vibrateForInterval(1500);
    }

    private void shouldBeHomeButNot_OnWifi(){
        notifController.sendNotification(
                "Where did you go...",
                "Why are you not home yet...? Did something happened? Got a new plan? You gotta tell me, or I will be worried 0.0",
                "Grandma! Sorry I can't be home just yet, [PUT YOUR REASON HERE]"
        );
        vibrateController.vibrateForInterval(1500);
    }

    private void batteryDyingAwayFromHome(){
        notifController.sendNotification(
                "Uh oh your battery can't seem to survive long",
                "I told you not to play on your phone that much! Now what >:( \n" +
                        "Sigh turn off your phone and we'll look out for you.",
                "Sorry my phone is going to die! I will be back soon tho!"
        );
        vibrateController.vibrateForInterval(3000);
        //text your fam your ETA?
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
