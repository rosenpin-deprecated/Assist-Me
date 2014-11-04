package com.pin.assistme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.assist.me.R;


public class ColorPickerActivity extends Activity {

    private ColorPicker colorPicker;
    private Button button,button2;
    String color = "#33B5E5";
    String actionbar_color,nav_color,back_color;
    boolean nav_checked,actionbar_checked = false;
    boolean back_checked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_picker);
        SharedPreferences prefEditor =getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if (prefEditor.contains("color")){
            color = prefEditor.getString("color", "");
        }
        colorPicker = (ColorPicker) findViewById(R.id.colorPicker);
        set_custom_color();
        button = (Button) findViewById(R.id.puthandhere);
        button2 = (Button) findViewById(R.id.default_button);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ColorPickerActivity.this);
                alert.setTitle("Confirm");
                alert.setMessage("Apply this color for");
                TableLayout color_alert = new TableLayout(getApplicationContext());
                final CheckBox action_bar = new CheckBox(getApplicationContext());
                action_bar.setText("Action Bar");
                final CheckBox navigation_drawer = new CheckBox(getApplicationContext());
                navigation_drawer.setText("Navigation Drawer");
                final CheckBox background = new CheckBox(getApplicationContext());
                background.setText("Background");
                color_alert.addView(action_bar);
                color_alert.addView(navigation_drawer);
                color_alert.addView(background);
                alert.setView(color_alert);
                alert.setPositiveButton("Confirm",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor color_pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                        color_pref.putString("color", "#33B5E5");

                        if (navigation_drawer.isChecked()) {
                            nav_checked = true;
                            color_pref.putString("nav", "");

                        }
                        if (action_bar.isChecked()) {
                            color_pref.putString("action_bar", "");
                        }
                        if (background.isChecked()) {
                            color_pref.putString("back", "");
                        }
                        color_pref.commit();
                        Intent intent = new Intent(getApplicationContext(), Main1.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alert.show();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ColorPickerActivity.this);
                alert.setTitle("Confirm");
                alert.setMessage("Apply this color for");
                TableLayout color_alert = new TableLayout(getApplicationContext());
                final CheckBox action_bar = new CheckBox(getApplicationContext());
                action_bar.setText("Action Bar");
                final CheckBox navigation_drawer = new CheckBox(getApplicationContext());
                navigation_drawer.setText("Navigation Drawer");
                final CheckBox background = new CheckBox(getApplicationContext());
                background.setText("Background");
                color_alert.addView(action_bar);
                color_alert.addView(navigation_drawer);
                color_alert.addView(background);
                alert.setView(color_alert);
                alert.setPositiveButton("Confirm",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int color = colorPicker.getColor();
                        String hexValue = Integer.toHexString(Integer.valueOf(Color.red(color)));
                        String hexValue2 = Integer.toHexString(Integer.valueOf(Color.blue(color)));
                        String hexValue3 = Integer.toHexString(Integer.valueOf(Color.green(color)));
                        String numberToPrint = "#"+hexValue+hexValue3+hexValue2;
                        int len = numberToPrint.length();
                        if(len<7&len>5){
                            numberToPrint = numberToPrint+"8";
                        }
                        else if(len<6&&len>4){
                            numberToPrint = numberToPrint+"88";
                        }
                        else if(len<5&&len>3){
                            numberToPrint = numberToPrint+"888";
                        }
                        Toast.makeText(ColorPickerActivity.this, numberToPrint, Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor color_pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();

                        if(navigation_drawer.isChecked()){
                            nav_checked = true;
                            color_pref.putString("nav", numberToPrint);

                        }
                        if(action_bar.isChecked())
                        {
                            actionbar_checked = true;
                            color_pref.putString("action_bar", numberToPrint);
                        }
                        if(background.isChecked()){
                            back_checked = true;
                            color_pref.putString("back", numberToPrint);
                        }
                        color_pref.putString("color", numberToPrint);
                        color_pref.commit();
                        Intent intent = new Intent(getApplicationContext(),Main1.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alert.show();
               }
        });

    }
    void set_custom_color() {
        SharedPreferences prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if (prefEditor.contains("color")) {
            color = prefEditor.getString("color", "");
        }
    }


}
