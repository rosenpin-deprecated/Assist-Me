package com.preferences;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.assist.me.R;

/**
 * Created by tomer on 06/08/14.
 */
public class About extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            findPreference("appversion").setSummary(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        getActionBar().setTitle("About");

    }
}
