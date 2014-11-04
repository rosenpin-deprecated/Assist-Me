package com.pin.assistme;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.assist.me.R;

import java.lang.reflect.Method;

/**
 * Created by tomer on 10/07/14.
 */
public class timer_service extends Service {
    NotificationManager mNotifyMgr;
    MediaPlayer mediaPlayer;
    public static boolean killed = false;
    public static long time_to_share;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.timer_end_sound);
        new CountDownTimer(Main1.time_timer, 1000) {
            public void onTick(long millisUntilFinished) {
                time_to_share = millisUntilFinished / 1000;
                notification(millisUntilFinished / 1000);
            }

            public void onFinish() {
                mediaPlayer.start();
                Intent intent = new Intent(getApplicationContext(),TimerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                try {
                    mNotifyMgr.cancelAll();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                try{
                    Object service  = getApplicationContext().getSystemService("statusbar");
                    Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
                    Method collapse = statusbarManager.getMethod("collapse");
                    collapse.invoke(service);
                }catch(Exception ex){}
                stopService(new Intent(getApplicationContext(),timer_service.class));

            }
        }.start();
    }
    void notification(long time){
        Intent resultIntent = new Intent(getApplicationContext(), TimerActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(String.valueOf(time))
                        .setOngoing(true)
                        .setContentText("Timer");
        int mNotificationId = 001;
        mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder.setContentIntent(resultPendingIntent);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
    private class ThreadDemo extends Thread {
        @Override
        public void run() {
            super.run();
            new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {
                   notification(millisUntilFinished);
                }

                public void onFinish() {

                }
            }.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        killed = true;
    }
}
