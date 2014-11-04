package com.preferences;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.assist.me.R;
import com.pin.assistme.Main1;

/**
 * Created by tomer on 06/08/14.
 */
public class General  extends PreferenceActivity {
    boolean toReturn = true;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.general);
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        init();
        getActionBar().setTitle("General");
        if(sharedPreferences.getBoolean("prefGreetStart",true)){
            sharedPreferences.edit().putBoolean("prefListenStart",false).commit();
            findPreference("prefListenStart").setEnabled(false);
        }
        if(sharedPreferences.getBoolean("prefListenStart",true)){
            sharedPreferences.edit().putBoolean("prefGreetStart",false).commit();
            findPreference("prefGreetStart").setEnabled(false);
        }

    }
    void init(){
        findPreference("prefBitch").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                toReturn = true;
                if(!sharedPreferences.getBoolean("prefBitch",true)) {
                    AlertDialog.Builder bitchAlert = new AlertDialog.Builder(General.this);
                    bitchAlert.setTitle("WARNING");
                    bitchAlert.setMessage("By clicking confirm you accept that you are over 13 years old, you had diarrhea at least once in your life and that you are, indeed a bitch");
                    bitchAlert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPreferences.edit().putBoolean("prefBitch",true).commit();
                            toReturn = false;
                            Intent i = new Intent(General.this, Main1.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                    });
                    bitchAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPreferences.edit().putBoolean("prefBitch",false).commit();
                            toReturn = false;
                            Intent i = new Intent(General.this, Main1.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                    });
                    bitchAlert.show();
                }
                return toReturn;
            }
        });

        findPreference("prefListenStart").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(!sharedPreferences.getBoolean("prefListenStart",true)){
                    sharedPreferences.edit().putBoolean("prefGreetStart",false).commit();
                    findPreference("prefGreetStart").setEnabled(false);
                }
                else{
                    findPreference("prefGreetStart").setEnabled(true);
                }
                return true;
            }
        });
        findPreference("prefGreetStart").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(!sharedPreferences.getBoolean("prefGreetStart",true)){
                    sharedPreferences.edit().putBoolean("prefListenStart",false).commit();
                    findPreference("prefListenStart").setEnabled(false);
                }
                else{
                    findPreference("prefListenStart").setEnabled(true);
                }
                return true;
            }
        });
        findPreference("prefWake").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(!sharedPreferences.getBoolean("prefWake",true)) {
                    final AlertDialog.Builder wakeupAlert = new AlertDialog.Builder(General.this);
                    wakeupAlert.setMessage("Turning this feature on will cause you lot's of bugs and errors also this will make the app run slower and it will probably cause explosive diarrhea to your hamster");
                    wakeupAlert.setTitle("EXPERIMENTAL PLEASE READ");
                    wakeupAlert.setPositiveButton("UNDERSTOOD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    wakeupAlert.show();
                }
                return true;
            }
        });
    }
}
