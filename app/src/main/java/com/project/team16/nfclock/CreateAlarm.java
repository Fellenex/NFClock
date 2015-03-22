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
    private TextView activeTimeDisplay;
    private Calendar endTime;
    private Calendar startTime;
    private Calendar activeTime;

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
                showTimePickerDialog(endTimeDisplay, endTime);
            }
        });

    }

    public void showTimePickerDialog(TextView timeDisplay, Calendar date) {
        activeTimeDisplay = timeDisplay;
        activeTime = date;
        Log.d("WIN","WINNING");

        DialogFragment newFragment = new timePickerFragment();
        newFragment.show(getFragmentManager(), "myDialog");
    }

    public void onUserSetTime(String displayName){
        if (activeTimeDisplay == startTimeDisplay){
            startTimeDisplay.setText(displayName);
        }
        else if (activeTimeDisplay == endTimeDisplay){
            endTimeDisplay.setText(displayName);
        }
        else {
            Log.d("ERROR", "No active time display");
        }
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
