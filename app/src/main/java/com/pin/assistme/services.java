package com.pin.assistme;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;


/**
 * Created by tomer on 4/17/2014.
 */
public class services extends Service {
    Handler handler;
    public static boolean killed = false;
    SharedPreferences sharedPrefs;
    int sdk = Build.VERSION.SDK_INT;
    private static final int HELLO_ID = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadDemo td = new ThreadDemo();
        td.start();
        handler = new Handler();
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    private class ThreadDemo extends Thread {
        @Override
        public void run() {
            super.run();
            while (1 == 1) {
                try {
                    sleep(7000);
                    ActivityManager am = (ActivityManager) services.this.getSystemService(ACTIVITY_SERVICE);
// The first in the list of RunningTasks is always the foreground task.
                    ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
                    String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();
//Set the values
                    if (!foregroundTaskPackageName.toLowerCase().contains("system") && !foregroundTaskPackageName.toLowerCase().contains("ui")&&!foregroundTaskPackageName.toLowerCase().contains("launcher")) {
                        if (sharedPrefs.contains(foregroundTaskPackageName)) {
                            String appname = foregroundTaskPackageName;
                            int timesOpened = sharedPrefs.getInt(foregroundTaskPackageName, 0) + 1;
                            favorite_apps info = new favorite_apps(services.this);
                            info.open();
                            info.find_clause(foregroundTaskPackageName, sharedPrefs.getInt(foregroundTaskPackageName, 0) + 1);
                            String data = info.getData();
                            if (data.contains(foregroundTaskPackageName)) {
                                // info.delete(foregroundTaskPackageName);
                                Log.d("found", foregroundTaskPackageName);
                            } else {
                                info.close();
                                favorite_apps entry = new favorite_apps(services.this);
                                entry.open();

                                entry.createEntry(appname, timesOpened);
                                entry.close();
                            }
                            sharedPrefs.edit().putInt(foregroundTaskPackageName, sharedPrefs.getInt(foregroundTaskPackageName, 0) + 1).commit();
                        } else {
                            String appname = foregroundTaskPackageName;
                            int timesOpened = sharedPrefs.getInt(foregroundTaskPackageName, 0) + 1;
                            favorite_apps entry = new favorite_apps(services.this);
                            entry.open();
                            entry.createEntry(appname, timesOpened);
                            entry.close();
                            sharedPrefs.edit().putInt(foregroundTaskPackageName, sharedPrefs.getInt(foregroundTaskPackageName, 0) + 1).commit();
                        }
                    }
                        //  Log.d("app",foregroundTaskPackageName+sharedPrefs.getInt(foregroundTaskPackageName,0));
                    }catch(Exception e){
                        e.printStackTrace();

                    }
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        killed = true;
        Intent intent = new Intent(getApplicationContext(),services.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(intent);
    }
}