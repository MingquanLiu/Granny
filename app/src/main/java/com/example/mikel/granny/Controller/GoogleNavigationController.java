package com.example.mikel.granny.Controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by mikel on 2018/1/14.
 */

public class GoogleNavigationController {
    private Context context;
    public GoogleNavigationController(Context context){
        this.context = context;
    }
    public void startNavigation(String address){
        String[] splited = address.split("\\s+");
        StringBuilder uri = new StringBuilder("google.navigation:q=");
        if(splited.length > 0) {
            for (String s : splited) {
                uri.append(s);
                uri.append("+");
            }
            uri.deleteCharAt(uri.length() - 1);
            Log.e("Google Navigation", uri.toString());
            Uri gmmIntentUri = Uri.parse(uri.toString());

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
            context.startActivity(mapIntent);
        }
    }
}
