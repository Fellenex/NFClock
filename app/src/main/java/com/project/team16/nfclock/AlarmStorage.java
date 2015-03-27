package com.project.team16.nfclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Stefan on 2015-03-22.
 */
public final class AlarmStorage {

    public AlarmStorage() {}

    public static abstract class Alarm implements BaseColumns{
        public static final String TABLE_NAME = "alarm";
        public static final String COLUMN_NAME_ALARM_NAME = "name";
        public static final String COLUMN_NAME_ALARM_START_TIME_HOUR = "startHour";
        public static final String COLUMN_NAME_ALARM_END_TIME_HOUR = "endHour";
        public static final String COLUMN_NAME_ALARM_START_TIME_MINUTE = "startMinute";
        public static final String COLUMN_NAME_ALARM_END_TIME_MINUTE = "endMinute";
        public static final String COLUMN_NAME_ALARM_INTERVAL = "interval";
        public static final String COLUMN_NAME_ALARM_REPEAT_WEEKLY = "repeatWeekly";
        public static final String COLUMN_NAME_ALARM_REPEAT_DAYS= "repeatingDays";
        public static final String COLUMN_NAME_ALARM_TONE = "alarmTone";
        public static final String COLUMN_NAME_ALARM_ENABLED = "isOn";
        public static final String COLUMN_NAME_ALARM_VIBRATE = "vibrate";

    }

}
