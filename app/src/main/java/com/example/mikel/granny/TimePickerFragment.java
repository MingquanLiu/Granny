package com.example.mikel.granny;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import 	android.app.TimePickerDialog;
import 	android.app.Dialog;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by Lingrui on 1/13/2018.
 * reference: https://developer.android.com/guide/topics/ui/controls/pickers.html
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    Data data;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        data = Data.getData();
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, 0, 0,//set initial time to 00:00
                true);
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        data.setHomeTime(hourOfDay, minute);
//        final TextView display = findViewById(R.id.timeDisplay);
    }
}
