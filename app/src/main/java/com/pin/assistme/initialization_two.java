package com.pin.assistme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.assist.me.R;

/**
 * Created by tomer on 14/06/14.
 */

public class initialization_two extends Activity {
    Button Continue;
    Spinner language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initialize_screen_two);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        Continue = (Button)findViewById(R.id.continue_first_screen);
        Continue.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_left));
        language = (Spinner)findViewById(R.id.language_spinner);
        Continue.setOnClickListener(new View.OnClickListener() {
            SharedPreferences.Editor sharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(initialization_two.this).edit();
            @Override
            public void onClick(View v) {
                switch (language.getSelectedItemPosition()){
                    case 0:
                        sharedPrefs.putString("prefLang", "english");
                        sharedPrefs.commit();
                        break;
                    case 1:
                        sharedPrefs.putString("prefLang", "hebrew");
                        sharedPrefs.commit();
                        break;

                }
                startActivity(new Intent(getApplicationContext(),initialziation_three.class));
                finish();
            }
        });
    }
}

