package com.project.team16.nfclock;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;
import java.util.Objects;


public class Adapter extends BaseAdapter {

    private Context context_;
    private List<AlarmTemplate> Alarms_;

    public Adapter(Context context, List<AlarmTemplate> alarms){
        context_ = context;
        Alarms_ = alarms;
    }

    public void setAlarms(List<AlarmTemplate> alarms){
        Alarms_ = alarms;
    }




    @Override
    public int getCount() {
        if (Alarms_ != null){
            return Alarms_.size();
        }
            return 0;
    }

    @Override
    public Object getItem(int index){
        if (Alarms_ != null){
            return Alarms_.get(index);
        }
            return null;
    }

    @Override
    public long getItemId(int index){
        if (Alarms_ != null){
            return Alarms_.get(index).id;
        }
            return 0;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater_ = (LayoutInflater) context_.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater_.inflate(R.layout.alarm_list_template, parent, false);

        }

        AlarmTemplate model = (AlarmTemplate) getItem(index);
        TextView starttime = (TextView) view.findViewById(R.id.alarmvalue_time);
        TextView endtime = (TextView) view.findViewById(R.id.alarmvalue_time2);

        starttime.setText(String.format("%02d:%02d", model.startHour, model.startMinute));
        endtime.setText(String.format("%02d:%02d", model.endHour, model.endMinute));

        TextView name = (TextView) view.findViewById(R.id.alarmvalue_name);
        name.setText(model.name);

        toggleOnOff((TextView) view.findViewById(R.id.ifMonday), model.getRepeatingDay(0));
        toggleOnOff((TextView) view.findViewById(R.id.ifTuesday), model.getRepeatingDay(1));
        toggleOnOff((TextView) view.findViewById(R.id.ifWednesday), model.getRepeatingDay(2));
        toggleOnOff((TextView) view.findViewById(R.id.ifThursday), model.getRepeatingDay(3));
        toggleOnOff((TextView) view.findViewById(R.id.ifFriday), model.getRepeatingDay(4));
        toggleOnOff((TextView) view.findViewById(R.id.ifSaturday), model.getRepeatingDay(5));
        toggleOnOff((TextView) view.findViewById(R.id.ifSunday), model.getRepeatingDay(6));

        view.setTag(Long.valueOf(model.id));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((homePage) context_).openAlarm(((Long) view.getTag()).longValue());
            }
        });

        return view;
    }

        private void toggleOnOff(TextView view, boolean isOn){
        if (isOn) {

            view.setTextColor(context_.getResources().getColor(R.color.PrimaryText));
            view.setTypeface(null, Typeface.BOLD);
        } else {
            view.setTextColor(context_.getResources().getColor(R.color.LightText));
            view.setTypeface(null, Typeface.NORMAL);
        }
        }


    }
