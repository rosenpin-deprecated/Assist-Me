package com.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.assist.me.R;
import com.pin.assistme.ColorPickerActivity;

/**
 * Created by tomer on 06/08/14.
 */
public class MakeItMine extends PreferenceActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.make_it_mine);
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        init();
        getActionBar().setTitle("Make It Mine");

    }
    void init(){
        Preference color = (Preference) findPreference("prefColor");
        color.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getApplicationContext(), ColorPickerActivity.class));
                return false;
            }
        });
    }
}
