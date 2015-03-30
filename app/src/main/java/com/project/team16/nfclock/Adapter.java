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

        Long id_ = Long.valueOf(model.id);
        view.setTag(id_);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((homePage) context_).openAlarm(((Long) view.getTag()).longValue());
            }
        });

        ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.toggleSwitch);
        toggleButton.setChecked(model.isOn);
        toggleButton.setTag(id_);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((homePage) context_).setAlarmEnabled(((Long) buttonView.getTag()).longValue(), isChecked);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((homePage) context_).deleteAlarm(((Long) view.getTag()).longValue());
                return true;
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
