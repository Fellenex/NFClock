package com.project.team16.nfclock;

import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CreateAlarm extends ActionBarActivity {

    private TextView startTimeDisplay;
    private TextView endTimeDisplay;
    private Calendar startTime;
    private Calendar endTime;


    static final int DATE_DIALOG_ID = 0;

    private TextView activeTimeDisplay;
    private Calendar activeTime;

    TimePickerDialog.OnTimeSetListener from_timeListener,to_timeListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);

        startTimeDisplay = (TextView) findViewById(R.id.startTimeDisplay);

        startTimeDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTimeDialog(startTimeDisplay, startTime);
            }
        });

        endTimeDisplay = (TextView) findViewById(R.id.endTimeDisplay);

        endTimeDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTimeDialog(endTimeDisplay, endTime);
            }
        });

        updateDisplay(startTimeDisplay, startTime);
        updateDisplay(endTimeDisplay, endTime);

    }

    private void updateDisplay(TextView timeDisplay, Calendar date){
        timeDisplay.setText(
                new SimpleDateFormat("HH:mm").format(date.getTime())
        );
    }

    public void showTimeDialog(TextView timeDisplay, Calendar date){
        activeTimeDisplay = timeDisplay;
        activeTime = date;
        showDialog(DATE_DIALOG_ID);
    }



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
