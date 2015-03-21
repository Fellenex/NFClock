package com.project.team16.nfclock;

import android.app.TimePickerDialog;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Dialog;

import org.w3c.dom.Text;

import java.util.Calendar;


public class CreateAlarm extends ActionBarActivity {

    private TextView startTime;
    private TextView endTime;
    private TimePicker TimePicker_;
    private int current;
    private int hour_;
    private int minute_;
    static final int TIME_START_ID = 1;
    static final int TIME_END_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        addTextEditListener();
    }


    public void addTextEditListener(){
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_END_ID );
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_START_ID );
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id) {
            case TIME_START_ID :
                current = TIME_START_ID;
                return new TimePickerDialog(this, timePickerListener, hour_, minute_, false);
            case TIME_END_ID :
                current = TIME_END_ID;
                return new TimePickerDialog(this, timePickerListener, hour_, minute_, false );
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener(){
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute){
            hour_ = selectedHour;
            minute_ = selectedMinute;
            if (current == TIME_START_ID){
                startTime.setText( hour_ + ":" + minute_ );
            }
            if (current == TIME_END_ID){
                endTime.setText( hour_ + ":" + minute_ );
            }
        }
    };

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
