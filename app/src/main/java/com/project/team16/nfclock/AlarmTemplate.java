package com.project.team16.nfclock;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Stefan on 2015-03-20.
 */
public class AlarmTemplate {
    public enum days {
        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
    }

    public long id;
    public String name;
    public int startHour;
    public int startMinute;
    public int endHour;
    public int endMinute;
    private boolean repeatingDays[];
    public boolean repeatWeekly;
    public boolean isOn;

    public AlarmTemplate() {
        repeatingDays = new boolean[7];
    }

    public void setRepeatingDays(int dayOfWeek, boolean value) {
        repeatingDays[dayOfWeek] = value;
    }
    public boolean getRepeatingDay(int dayOfWeek){
        if (repeatingDays[dayOfWeek]) return true;
        else return false;
    }

}
