package com.project.team16.nfclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.project.team16.nfclock.AlarmStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Stefan on 2015-03-22.
 */
public class DBManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarm.db";

    private static final String SQL_CREATE_ALARM =
            "CREATE TABLE " + AlarmStorage.Alarm.TABLE_NAME + " (" +
                    AlarmStorage.Alarm._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_NAME + " TEXT," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_START_TIME_HOUR + " INTEGER," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_START_TIME_MINUTE + " INTEGER," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_END_TIME_HOUR + " INTEGER," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_END_TIME_MINUTE + " INTEGER," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS + " TEXT," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY + " BOOLEAN," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_INTERVAL + " DOUBLE," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_TONE + " TEXT," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_ENABLED + " BOOLEAN," +
                    AlarmStorage.Alarm.COLUMN_NAME_ALARM_VIBRATE + " BOOLEAN" + ");";

    private static final String SQL_DELETE_ALARM = "DROP TABLE IF EXISTS " + AlarmStorage.Alarm.TABLE_NAME;

    public DBManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ALARM);
        onCreate(db);
    }

    private AlarmTemplate fillModel(Cursor c){
        AlarmTemplate model = new AlarmTemplate();
        model.id = c.getLong((c.getColumnIndex(AlarmStorage.Alarm._ID)));
        model.name = c.getString(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_NAME));
        model.startHour = c.getInt(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_START_TIME_HOUR));
        model.startMinute = c.getInt(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_START_TIME_MINUTE));
        model.endHour = c.getInt(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_END_TIME_HOUR));
        model.endMinute = c.getInt(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_END_TIME_MINUTE));
        model.interval = c.getDouble(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_INTERVAL));
        model.repeatWeekly = c.getInt(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY))!=0;
        model.alarmTone = Uri.parse(c.getString(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_TONE)));
        model.isOn = c.getInt(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_ENABLED))!=0;
        model.vibrate = c.getInt(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_VIBRATE))!=0;
        String[] days = c.getString(c.getColumnIndex(AlarmStorage.Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS)).split(",");
        for (int i=0; i<days.length; i++){
            model.setRepeatingDays(i, !days[i].equals("false"));
        }
        return model;
    }

    private ContentValues fillModel(AlarmTemplate model){
        Log.d("HIT","contentValues");
        ContentValues values = new ContentValues();
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_NAME, model.name);
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_START_TIME_HOUR, model.startHour);
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_START_TIME_MINUTE, model.startMinute);
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_END_TIME_HOUR, model.endHour);
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_END_TIME_MINUTE, model.endMinute);
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY, model.repeatWeekly);
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_TONE, model.alarmTone.toString());
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_VIBRATE, model.vibrate);
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_INTERVAL, model.interval);
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_ENABLED, model.isOn);


        String days = "";
        for (int i=0; i<7; i++){
            days += model.getRepeatingDay(i) + ",";
        }
        values.put(AlarmStorage.Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS, days);
        Log.d("HIT","contentValues_Finished");
        return values;

    }

    public long createAlarm(AlarmTemplate model){
        Log.d("HIT","createAlarm_Start");
        ContentValues values = fillModel(model);
        Log.d("HIT","createAlarm_End");
        printContentValues(values);
        return getWritableDatabase().insert(AlarmStorage.Alarm.TABLE_NAME, null, values);
    }

    public void printContentValues(ContentValues vals)
    {
        Set<Map.Entry<String, Object>> s=vals.valueSet();
        Iterator itr = s.iterator();

        Log.d("DatabaseSync", "ContentValue Length :: " +vals.size());

        while(itr.hasNext())
        {
            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();

            Log.d("DatabaseSync", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
        }
    }



    public AlarmTemplate getAlarm(long id){
        Log.d("Start","GetAlarm");
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + AlarmStorage.Alarm.TABLE_NAME + " WHERE " + AlarmStorage.Alarm._ID + " = " + id;
        Cursor c = db.rawQuery(select,null);

        Log.d("Finsh","getAlarm");
        if (c.moveToNext()) {
            return fillModel(c);
        }

        return null;
    }

    public long updateAlarm(AlarmTemplate model){
        ContentValues values = fillModel(model);
        return getWritableDatabase().update(AlarmStorage.Alarm.TABLE_NAME, values, AlarmStorage.Alarm._ID + " = ?", new String[] { String.valueOf(model.id) });
    }

    public int deleteAlarm(long id) {
        return getWritableDatabase().delete(AlarmStorage.Alarm.TABLE_NAME, AlarmStorage.Alarm._ID + " = ?", new String[] { String.valueOf(id)});
    }


    public List<AlarmTemplate> getAlarms() {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + AlarmStorage.Alarm.TABLE_NAME;
        Cursor c = db.rawQuery(select,null);
        List<AlarmTemplate> alarmList = new ArrayList<AlarmTemplate>();

        while (c.moveToNext()) {
            alarmList.add(fillModel(c));
        }

        db.close();

        if (!alarmList.isEmpty()){
            return alarmList;
        }

        return null;
    }

}