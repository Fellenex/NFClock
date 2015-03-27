package com.project.team16.nfclock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Stefan on 2015-03-27.
 */
public class AlarmManagerExtra extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        setAlarms(context);
    }

    public static void setAlarms(Context context){
        voidAlarms(context);

        DBManager dbManager = new DBManager(context);

        List<AlarmTemplate> alarmList = dbManager.getAlarms();


        for (AlarmTemplate alarm : alarmList){
            if (alarm.isOn){

                PendingIntent pendingIntent = createPendingIntent(context, alarm);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,alarm.startHour);
                calendar.set(Calendar.MINUTE,alarm.startMinute);
                calendar.set(Calendar.SECOND, 00);

                final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                boolean alarmSet = false;

                for (int day = Calendar.MONDAY; day <= Calendar.SUNDAY; day++){
                    if (alarm.getRepeatingDay(day -1) && day >= nowDay &&
                            !(day == nowDay && alarm.startHour < nowHour) &&
                            !(day == nowDay && alarm.startHour == nowHour && alarm.startMinute <= nowMinute)){
                        calendar.set(Calendar.DAY_OF_WEEK,day);

                        setAlarm(context, calendar, pendingIntent);
                        alarmSet = true;
                        break;
                    }


                }


                if (!alarmSet) {
                    for (int day = Calendar.MONDAY; day <= Calendar.SUNDAY; day++){
                        if (alarm.getRepeatingDay(day - 1) && day <= nowDay && alarm.repeatWeekly) {
                            calendar.set(Calendar.DAY_OF_WEEK,day);
                            calendar.add(Calendar.WEEK_OF_YEAR,1);
                            setAlarm(context,calendar,pendingIntent);
                            alarmSet = true;
                            break;
                        }
                    }
                }


            }
        }

    }


    @SuppressLint("NewAPI")
    private static void setAlarm(Context context, Calendar calendar, PendingIntent pendingIntent){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public static void voidAlarms(Context context){
        Intent intent = new Intent(context, AlarmManager.class);
        DBManager dbManager = new DBManager(context);
        List<AlarmTemplate> alarmList = dbManager.getAlarms();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        if (alarmList != null){
            for (AlarmTemplate alarm : alarmList){
                if (alarm.isOn) {
                    PendingIntent pendingIntent = createPendingIntent(context,alarm);
                    alarmManager.cancel(pendingIntent);

                }
            }
        }

    }

    private static PendingIntent createPendingIntent(Context context, AlarmTemplate alarmTemplate) {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.putExtra("id", alarmTemplate.id);
        intent.putExtra("name", alarmTemplate.name);
        intent.putExtra("startHour", alarmTemplate.startHour);
        intent.putExtra("startMinute", alarmTemplate.startMinute);
        intent.putExtra("endHour", alarmTemplate.endHour);
        intent.putExtra("endMinute", alarmTemplate.endMinute);
        intent.putExtra("alarmTone", alarmTemplate.alarmTone.toString());

        return PendingIntent.getService(context, (int) alarmTemplate.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }



}
