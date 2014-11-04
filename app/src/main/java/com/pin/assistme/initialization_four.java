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
import android.widget.EditText;
import android.widget.TextView;
import com.assist.me.R;

/**
 * Created by tomer on 14/06/14.
 */
public class initialization_four extends Activity {
    Button Continue;
    SharedPreferences.Editor prefEditor;
    EditText name,email,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initialize_screen_four);
        prefEditor = PreferenceManager
                .getDefaultSharedPreferences(initialization_four.this).edit();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        Continue = (Button)findViewById(R.id.continue_first_screen);
        Continue.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_left));
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email_address);
        phone = (EditText)findViewById(R.id.phone_number);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( name.getText().toString().trim().equals(""))
                {
                    name.setError("Name is required");
                }
                else
                {
                    prefEditor.putString("prefUsername", name.getText().toString());
                    prefEditor.commit();
                    startActivity(new Intent(getApplicationContext(),initialization_five.class));
                    finish();
                }
                if (email.getText()!=null){
                    prefEditor.putString("email", email.getText().toString());
                    prefEditor.commit();
                }
                if (phone.getText()!=null){
                    prefEditor.putString("phone_number", phone.getText().toString());
                    prefEditor.commit();
                }
            }
        });
    }
}
