package com.project.team16.nfclock;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import com.project.team16.nfclock.CreateAlarm;


import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Stefan on 2015-03-21.
 */

public class timePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private EditText activeDisplay;
    private Calendar activeDate;
    private int callCount = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(callCount ==1)
        {
            String timeString = hourOfDay + ":" + minute;
            Log.d("TEST","" + timeString);
            CreateAlarm call = (CreateAlarm) getActivity();
            call.onUserSetTime(timeString);
        }
        callCount++;
        //activeDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        //activeDate.set(Calendar.MINUTE,minute);


        //String results = new SimpleDateFormat("HH:mm").format(activeDate.getTime());

    }
}