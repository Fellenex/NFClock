package com.project.team16.nfclock;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.database.Cursor;
import android.net.Uri;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Dialog;
import com.project.team16.nfclock.timePickerFragment;


import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CreateAlarm extends ActionBarActivity {

    private EditText startTimeDisplay;
    private EditText endTimeDisplay;
    private EditText activeTimeDisplay;
    private Calendar endTime;
    private Calendar startTime;
    private Calendar activeTime;
    private int nameIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);

        startTimeDisplay = (EditText) findViewById(R.id.startTimeDisplay);

        startTimeDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTimePickerDialog(startTimeDisplay, startTime);
            }
        });

        endTimeDisplay = (EditText) findViewById(R.id.endTimeDisplay);

        endTimeDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTimePickerDialog(endTimeDisplay, endTime);
            }
        });

        final LinearLayout toneSelection = (LinearLayout) findViewById(R.id.bottomLayout);
        toneSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1){
            Uri toneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (toneUri!=null){
                TextView ringToneDisplay = (TextView) findViewById(R.id.alarmToneFull);
                ringToneDisplay.setText(RingtoneManager.getRingtone(this, toneUri).getTitle(this));
            }
        }
    }

    public void showTimePickerDialog(EditText timeDisplay, Calendar date) {
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
        if (id == R.id.createAlarmButton) {
          //  updateAlarmTemplate(); Needs to be implemented
            finish();
        }

        return super.onOptionsItemSelected(item);
    }










}
