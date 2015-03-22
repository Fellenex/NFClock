package com.project.team16.nfclock;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Dialog;
import com.project.team16.nfclock.timePickerFragment;


import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CreateAlarm extends ActionBarActivity {

    private TextView startTimeDisplay;
    private TextView endTimeDisplay;
    private Calendar startTime;
    private Calendar endTime;


    static final int DATE_DIALOG_ID = 0;

    public TextView activeTimeDisplay;
    public Calendar activeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);

        startTimeDisplay = (TextView) findViewById(R.id.startTimeDisplay);

        startTimeDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTimePickerDialog(startTimeDisplay, startTime);
            }
        });

        endTimeDisplay = (TextView) findViewById(R.id.endTimeDisplay);

        endTimeDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTimePickerDialog(startTimeDisplay, startTime);
            }
        });

    }

    public TextView getActiveTimeDisplay() {
        return (activeTimeDisplay);
    }

    public Calendar getActiveTime() {
        return (activeTime);
    }

    public void setActiveTimeDisplay(TextView display) {
        activeTimeDisplay = display;
    }

    public void setActiveTime(Calendar date){
        activeTime = date;
    }
    public void showTimePickerDialog(TextView timeDisplay, Calendar date) {
        activeTimeDisplay = timeDisplay;
        activeTime = date;
        Log.d("WIN","WINNING");

        DialogFragment newFragment = new timePickerFragment();
        //Bundle args = new Bundle();
        //args.putString("name",activeTimeDisplay.toString());
        //newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "myDialog");
    }

    public void onUserSetTime(String displayName){
        Log.d("String",displayName);
        //activeTimeDisplay.setText(displayName);

    }
     public static void updateDisplay(String timeDisplayName, Calendar date){
        // if
        //timeDisplay.setText(new SimpleDateFormat("HH:mm",java.util.Locale.getDefault()).format(date.getTime())
       // );
    }

/*
    public void showTimeDialog(TextView timeDisplay, Calendar date){
        activeTimeDisplay = timeDisplay;
        activeTime = date;
        showDialog(DATE_DIALOG_ID);
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            unregisterTimeDisplay();
        }
    };

    private void unregisterTimeDisplay() {
        activeTime = null;
        activeTimeDisplay = null;
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id) {
            case DATE_DIALOG_ID:
                return new TimePickerDialog(this, timeSetListener, activeTime.get(Calendar.HOUR_OF_DAY), activeTime.get(Calendar.MINUTE),false);
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id,@NonNull Dialog dialog){
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case DATE_DIALOG_ID:
                ((TimePickerDialog) dialog).updateTime(activeTime.get(Calendar.HOUR_OF_DAY),activeTime.get(Calendar.MINUTE));
                //((TimePickerDialog) dialog).updateTime(activeTime.get(Calendar.HOUR_OF_DAY),activeTime.get(Calendar.Mi),false);
                break;
        }
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
