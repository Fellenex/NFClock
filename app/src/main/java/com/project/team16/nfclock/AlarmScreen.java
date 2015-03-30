package com.project.team16.nfclock;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


/*
import org.w3c.dom.Text;

*/
/**
 * Created by Stefan on 2015-03-27.
 */

public class AlarmScreen extends Activity {

    public final String tag = this.getClass().getSimpleName();

    private PowerManager.WakeLock wakeLock;
    private MediaPlayer mediaPlayer;
    //private Handler handler;
    private static final int WAKELOCK_TIMEOUT = 60*1000;


    @Override
    protected void onCreate(Bundle alarmState){
        Log.d("ALRMSTUFF","STUFF");
        super.onCreate(alarmState);

        this.setContentView(R.layout.on_alarm_screen);

        String alarmName = getIntent().getStringExtra(AlarmManagerExtra.ID);
        TextView textView = (TextView) findViewById(R.id.alarmNameScreen);
        textView.setText(alarmName);
        String alarmTone = getIntent().getStringExtra(AlarmManagerExtra.TONE);


        Button endAlarm = (Button) findViewById(R.id.endAlarmButton);
        endAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                finish();
            }
        });

        mediaPlayer = new MediaPlayer();
        try {
            if (alarmTone != null && !alarmTone.equals("")){
                Uri uri = Uri.parse(alarmTone);
                if (uri != null){
                    mediaPlayer.setDataSource(this,uri);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

                if (wakeLock != null && wakeLock.isHeld()) {
                    wakeLock.release();
                }
            }
        };

    new Handler().postDelayed(runnable, WAKELOCK_TIMEOUT);

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (wakeLock == null) {
            wakeLock = powerManager.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), tag);
        }

        if (!wakeLock.isHeld()){
            wakeLock.acquire();
            Log.i(tag,"Wakelock aquired");
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        if (wakeLock != null && wakeLock.isHeld()){
            wakeLock.release();
        }
    }

}
