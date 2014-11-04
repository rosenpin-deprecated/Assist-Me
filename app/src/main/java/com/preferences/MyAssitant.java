package com.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.assist.me.R;

/**
 * Created by tomer on 06/08/14.
 */
public class MyAssitant extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.my_assistant);
        getActionBar().setTitle("My Assistant");

    }
}
