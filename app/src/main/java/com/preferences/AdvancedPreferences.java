package com.preferences;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.assist.me.R;
import com.pin.assistme.Main1;

/**
 * Created by tomer on 06/08/14.
 */
public class AdvancedPreferences extends PreferenceActivity {
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.advanced);
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        init();
        getActionBar().setTitle("Advanced");

    }
    void init(){
        findPreference("prefNotification").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(sharedPreferences.getBoolean("prefNotification",true)) {
                    final AlertDialog.Builder notificationAlert = new AlertDialog.Builder(AdvancedPreferences.this);
                    notificationAlert.setMessage("Disabling the notification will cause Assist-Me to not run in background, by not running in background Assist-Me won't be able to learn from you");
                    notificationAlert.setTitle("EXPERIMENTAL PLEASE READ");
                    notificationAlert.setPositiveButton("UNDERSTOOD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String ns = Context.NOTIFICATION_SERVICE;
                            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                            nMgr.cancelAll();
                        }
                    });
                    notificationAlert.show();
                }
                else {
                    if (Build.VERSION.SDK_INT >= 16) {

                        Intent resultIntent = new Intent(getApplicationContext(), Main1.class);
                        PendingIntent resultPendingIntent =
                                PendingIntent.getActivity(
                                        AdvancedPreferences.this,
                                        0,
                                        resultIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT
                                );
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(AdvancedPreferences.this)
                                        .setSmallIcon(R.drawable.ic_launcher)
                                        .setContentTitle("Assist Me")
                                        .setOngoing(true)
                                        .setContentText("Assist Me is running in background");
                        int mNotificationId = 001;
                        NotificationManager mNotifyMgr =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        mBuilder.setContentIntent(resultPendingIntent);
                        mNotifyMgr.notify(mNotificationId, mBuilder.build());

                    } else {
                        String ns = Context.NOTIFICATION_SERVICE;
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
                        int icon = R.drawable.ic_launcher_on_focus;
                        CharSequence tickerText = "Assist Me"; // ticker-text
                        long when = System.currentTimeMillis();
                        Context context = getApplicationContext();
                        CharSequence contentTitle = "Assist-Me";
                        CharSequence contentText = "Assist Me is running in background";
                        Intent notificationIntent = new Intent(AdvancedPreferences.this, AdvancedPreferences.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(AdvancedPreferences.this, 0, notificationIntent, 0);
                        Notification notification = new Notification(icon, tickerText, when);
                        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

// and this
                        notification.flags = Notification.FLAG_ONGOING_EVENT;
                        mNotificationManager.cancelAll();

                    }
                }
                return true;
            }
        });
        Preference reset = (Preference) findPreference("prefReset");
        reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder clearAlert = new AlertDialog.Builder(AdvancedPreferences.this);
                clearAlert.setTitle("Clear Data");
                clearAlert.setMessage("By confirming all assist me data will be deleted");
                clearAlert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearApplicationData();
                    }
                });
                clearAlert.setNegativeButton("Cancel", null);
                clearAlert.show();
                return false;
            }
        });
    }
    public void clearApplicationData() {
        try {
            // clearing app data
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear com.assist.me");
            Intent i = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.assist.me");
            getApplicationContext().startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            Intent i = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.assist.me");
            getApplicationContext().startActivity(i);
        } finally {
            Intent i = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.assist.me");
            getApplicationContext().startActivity(i);
        }
    }

}
