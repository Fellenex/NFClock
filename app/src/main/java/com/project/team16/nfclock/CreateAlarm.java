package com.project.team16.nfclock;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.database.Cursor;
import android.net.Uri;
import android.widget.TextView;
import com.project.team16.nfclock.AlarmTemplate;
import android.widget.TimePicker;
import android.app.Dialog;
import android.widget.ToggleButton;

import com.project.team16.nfclock.timePickerFragment;


import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateAlarm extends ActionBarActivity {


    private EditText startTimeDisplay;
    private EditText endTimeDisplay;
    private EditText activeTimeDisplay;


    private ToggleButton isMonday;
    private ToggleButton isTuesday;
    private ToggleButton isWednesday;
    private ToggleButton isThurdsay;
    private ToggleButton isFriday;
    private ToggleButton isSaturday;
    private ToggleButton isSunday;

    private CheckBox isVibrate;
    private CheckBox isRepeating;

    private EditText nameDisplay;
    private EditText intervalDisplay;

    private Calendar endTime;
    private Calendar startTime;
    private Calendar activeTime;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private int nameIndex;

    private DBManager dbManager = new DBManager(this);
    private AlarmTemplate alarmInstance;
    private Uri toneUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        //alarmInstance = new AlarmTemplate();

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

        long id = getIntent().getExtras().getLong("id");

        //Connect display to variables
        startTimeDisplay = (EditText) findViewById(R.id.startTimeDisplay);
        endTimeDisplay = (EditText) findViewById(R.id.endTimeDisplay);

        intervalDisplay = (EditText) findViewById(R.id.alarmIntervalDisplay);
        isRepeating = (CheckBox) findViewById(R.id.repeatWeekly);
        isVibrate = (CheckBox) findViewById(R.id.toggleVibrate);
        nameDisplay = (EditText) findViewById(R.id.alarmNameDisplay);

        isMonday = (ToggleButton) findViewById(R.id.toggleMonday);
        isTuesday = (ToggleButton) findViewById(R.id.toggleTuesday);
        isWednesday = (ToggleButton) findViewById(R.id.toggleWednesday);
        isThurdsay = (ToggleButton) findViewById(R.id.toggleThursday);
        isFriday = (ToggleButton) findViewById(R.id.toggleFriday);
        isSaturday = (ToggleButton) findViewById(R.id.toggleSaturday);
        isSunday = (ToggleButton) findViewById(R.id.toggleSunday);







        if (id == -1) { //new alarm
            alarmInstance = new AlarmTemplate();
        } else { //Populate the activity with previously created alarms details
            String longstr = Long.toString(id);
            Log.d("ID",longstr);
            alarmInstance = dbManager.getAlarm(id);

            startTimeDisplay.setText(String.format("%02d:%02d", alarmInstance.startHour, alarmInstance.startMinute));
            endTimeDisplay.setText(String.format("%02d:%02d", alarmInstance.startHour, alarmInstance.startMinute));

            startHour = alarmInstance.startHour;
            startMinute = alarmInstance.startMinute;

            endHour = alarmInstance.endHour;
            endMinute = alarmInstance.endMinute;

            nameDisplay.setText(alarmInstance.name);
            String results = String.valueOf(alarmInstance.interval);
            intervalDisplay.setText(results);
            isRepeating.setChecked(alarmInstance.repeatWeekly);
            isVibrate.setChecked(alarmInstance.vibrate);

            isMonday.setChecked(alarmInstance.getRepeatingDay(0));
            isTuesday.setChecked(alarmInstance.getRepeatingDay(1));
            isWednesday.setChecked(alarmInstance.getRepeatingDay(2));
            isThurdsay.setChecked(alarmInstance.getRepeatingDay(3));
            isFriday.setChecked(alarmInstance.getRepeatingDay(4));
            isSaturday.setChecked(alarmInstance.getRepeatingDay(5));
            isSunday.setChecked(alarmInstance.getRepeatingDay(6));


        }


        //final LinearLayout toneSelection = (LinearLayout) findViewById(R.id.alarmToneLayout);
        //final TextView toneSelection2 = (TextView) findViewById(R.id.alarmTone);
        //final EditText toneSelection3 = (EditText) findViewById(R.id.alarmToneDisplay);
    }


    public void onToneClick(View view){
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        startActivityForResult(intent, 1);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
/*        if (resultCode == Activity.RESULT_OK && requestCode == 1){
            toneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (toneUri!=null){
                TextView ringToneDisplay = (EditText) findViewById(R.id.alarmToneDisplay);
                ringToneDisplay.setText(RingtoneManager.getRingtone(this, toneUri).getTitle(this));
            }
        }*/
    }

    private void showTimePickerDialog(EditText timeDisplay, Calendar date) {
        activeTimeDisplay = timeDisplay;
        activeTime = date;
        Log.d("WIN","WINNING");

        DialogFragment newFragment = new timePickerFragment();
        newFragment.show(getFragmentManager(), "myDialog");
    }

    public void onUserSetTime(int HourofDay, int minute){
        //Log.d("TIME", "" + HourofDay + ":" + minute);
        String time = String.format("%02d:%02d", HourofDay, minute);
        if (activeTimeDisplay == startTimeDisplay){
            startHour = HourofDay;
            startMinute = minute;
           startTimeDisplay.setText(time);
        } else if (activeTimeDisplay == endTimeDisplay) {
            endHour = HourofDay;
            endMinute = minute;
            endTimeDisplay.setText(time);
        } else {
            Log.d("ERROR", "No active time display");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_alarm_menu, menu);
        return true;
    }

    public void saveAlarm(View v){
        updateAlarmTemplate();

        DBManager dbManager = new DBManager(this);
        if (alarmInstance.id < 0){
            dbManager.createAlarm(alarmInstance);
        } else {
            dbManager.updateAlarm(alarmInstance);
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateAlarmTemplate() { //method updates the alarmInstance to be filled out with the values from the Activity.

        alarmInstance.startHour = startHour;
        alarmInstance.startMinute = startMinute;
        alarmInstance.endHour = endHour;
        alarmInstance.endMinute = endMinute;

        CheckBox isRepeating = (CheckBox) findViewById(R.id.repeatWeekly);
        alarmInstance.repeatWeekly = isRepeating.isChecked();

        CheckBox isVibrating = (CheckBox) findViewById(R.id.toggleVibrate);
        alarmInstance.vibrate = isVibrating.isChecked();

        EditText name = (EditText) findViewById(R.id.alarmNameDisplay);
        alarmInstance.name = name.getText().toString();

       // alarmInstance.alarmTone = toneUri;

        alarmInstance.isOn = true;

        EditText interval = (EditText) findViewById(R.id.alarmIntervalDisplay);
        alarmInstance.interval = Double.parseDouble(interval.getText().toString());

        ToggleButton monday = (ToggleButton) findViewById(R.id.toggleMonday);
        ToggleButton tuesday = (ToggleButton) findViewById(R.id.toggleTuesday);
        ToggleButton wednesday = (ToggleButton) findViewById(R.id.toggleWednesday);
        ToggleButton thursday = (ToggleButton) findViewById(R.id.toggleThursday);
        ToggleButton friday = (ToggleButton) findViewById(R.id.toggleFriday);
        ToggleButton saturday = (ToggleButton) findViewById(R.id.toggleSaturday);
        ToggleButton sunday = (ToggleButton) findViewById(R.id.toggleSunday);

        alarmInstance.setRepeatingDays(0, monday.isChecked());
        alarmInstance.setRepeatingDays(1, tuesday.isChecked());
        alarmInstance.setRepeatingDays(2, wednesday.isChecked());
        alarmInstance.setRepeatingDays(3, thursday.isChecked());
        alarmInstance.setRepeatingDays(4, friday.isChecked());
        alarmInstance.setRepeatingDays(5, saturday.isChecked());
        alarmInstance.setRepeatingDays(6, sunday.isChecked());

    }


}
