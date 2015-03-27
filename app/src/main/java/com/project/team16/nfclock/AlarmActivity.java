package com.project.team16.nfclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Stefan on 2015-03-27.
 */
public class AlarmActivity extends Service {

    public static String tag = AlarmActivity.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        AlarmManagerExtra.setAlarms(this);
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

}
