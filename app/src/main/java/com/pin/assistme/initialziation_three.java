package com.pin.assistme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;

import com.assist.me.R;

/**
 * Created by tomer on 14/06/14.
 */

public class initialziation_three extends Activity {
    Button Continue;
    SharedPreferences.Editor prefEditor;
    CheckBox answer,onstart,learn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initialize_screen_three);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        answer = (CheckBox) findViewById(R.id.answer);
        onstart = (CheckBox)findViewById(R.id.on_start_greeting);
        learn = (CheckBox)findViewById(R.id.learn);
        Continue = (Button)findViewById(R.id.continue_first_screen);
        Continue.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_left));
        prefEditor = PreferenceManager
                .getDefaultSharedPreferences(initialziation_three.this).edit();
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer.isChecked()){
                    prefEditor.putBoolean("speakback", true);
                    prefEditor.commit();
                }
                else{
                    prefEditor.putBoolean("speakback", false);
                    prefEditor.commit();
                }
                if (onstart.isChecked()){
                    prefEditor.putBoolean("prefGreetStart", true);
                    prefEditor.commit();
                }
                else {
                    prefEditor.putBoolean("prefGreetStart", false);
                    prefEditor.commit();
                }
                if(learn.isChecked()){

                }
                else {

                }
                startActivity(new Intent(getApplicationContext(),initialization_four.class));
                finish();
            }
        });
    }
}
