package com.example.mikel.granny;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.FragmentActivity;
import android.widget.TimePicker;

/**
 * Created by Lingrui on 1/13/2018.
 */

public class InitInfoActivity extends FragmentActivity {

    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = Data.getData();

        // Get intent, action and MIME type
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_init_info);
        final Button initialize = findViewById(R.id.initialize);
        final EditText addressField = findViewById(R.id.addressField);
//        final EditText homeLat = findViewById(R.id.homeLat);
//        final EditText homeLon = findViewById(R.id.homeLong);
        final TimePicker timePicker = findViewById(R.id.timePicker);
        initialize.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                data.setHomeLoc(Double.parseDouble(homeLat.getText().toString()), Double.parseDouble(homeLon.getText().toString()));
                data.setHomeAddress(addressField.getText().toString());
                data.setHomeTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                Log.e("InitInfoActivity: ", "get Address: " + data.getAddress() + "\t get home time: " +  data.getHomeHour() + ":" + data.getHomeMinute());
                DataProvider dataProvider = new DataProvider(getApplicationContext());
                dataProvider.createProviders();
                if (!data.getAddress().equals("")
                        && (data.getHomeHour() != 0 || data.getHomeMinute() != 0)){
                    finish();
                }
            }
        });

    }
//
//    public void showTimePickerDialog(View v) {
//        DialogFragment newFragment = new TimePickerFragment();
//        newFragment.show(this.getSupportFragmentManager(), "timePicker");
//    }

}
