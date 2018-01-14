package com.example.mikel.granny;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Lingrui on 1/14/2018.
 */

public class UpdateNotiStatusActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //update the status of the calling notification
        int index = Integer.parseInt(intent.getStringExtra("version"));
        Data.getData().setNotificationStatus(index, false);
        finish();
    }
}
