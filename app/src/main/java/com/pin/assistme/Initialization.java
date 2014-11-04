package com.pin.assistme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.assist.me.R;

/**
 * Created by tomer on 14/06/14.
 */
public class Initialization extends Activity {
    Button Continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.bottom_to_top_fade_in, R.anim.top_to_bottom_fade_out);
        setContentView(R.layout.initialize_screen_one);
        Continue = (Button)findViewById(R.id.continue_first_screen);
        Continue.startAnimation(AnimationUtils.loadAnimation(Initialization.this, R.anim.slide_up_left));
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), initialization_two.class));
                finish();
            }
        });
    }
}

