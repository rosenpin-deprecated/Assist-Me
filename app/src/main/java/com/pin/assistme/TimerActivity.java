package com.pin.assistme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.assist.me.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tomer on 10/07/14.
 */
public class TimerActivity extends Activity{
    Timer myTimer;
    Button textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new Button(getApplicationContext());
        textView.setTextSize(100);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new ViewGroup.MarginLayoutParams(300, 300));
        textView.setPadding(300,200,300,200);
        setContentView(textView);
        createTimer();

    }
    private void TimerMethod()
    {
        this.runOnUiThread(Timer_Tick);
    }
    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            todo_on_tick();
        }
    };
    void createTimer(){
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 500);
    }
    void todo_on_tick(){
        textView.setText(String.valueOf(timer_service.time_to_share));
        if(textView.getText().toString().equals("1")){
            textView.setText("");
            textView.setBackgroundResource(R.drawable.v);
        }
    }
}
