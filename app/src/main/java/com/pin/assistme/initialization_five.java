package com.pin.assistme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import com.assist.me.R;

/**
 * Created by tomer on 14/06/14.
 */
public class initialization_five extends Activity {
    Button Continue;
    CheckBox instructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initialize_screen_five);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        Continue = (Button)findViewById(R.id.continue_first_screen);
        Continue.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_left));
        instructions = (CheckBox)findViewById(R.id.go_through_instructions);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(instructions.isChecked()){
                    startActivity(new Intent(getApplicationContext(), instructions.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), Main1.class));
                    finish();
                }
                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                prefEditor.putBoolean("initialized", true);
                prefEditor.commit();

            }
        });
    }
}