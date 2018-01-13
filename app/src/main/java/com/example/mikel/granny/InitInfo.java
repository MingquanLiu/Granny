package com.example.mikel.granny;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Lingrui on 1/13/2018.
 */

public class InitInfo extends Activity {

    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = Data.getData();

        // Get intent, action and MIME type
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_init_info);
        final Button initialize = findViewById(R.id.initialize);
        final EditText addressField = findViewById(R.id.addressField);
        final EditText timeField = findViewById(R.id.timeField);
        initialize.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                data.setAddress(addressField.getText().toString());
                data.setHomeTime(timeField.getText().toString());
                Log.e("InitInfo: ", "get Address: " + data.getAddress() + "\t get home time: " +  data.getHomeTime());
                if (!data.getAddress().equals("") && !data.getHomeTime().equals("")){
                    finish();
                }
            }
        });

    }
//
//    @Override
//    public void onDestroyed(){
//
//    }

}
