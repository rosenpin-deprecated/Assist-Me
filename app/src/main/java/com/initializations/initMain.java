package com.initializations;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.assist.me.R;
import com.pin.assistme.Main1;
import com.pin.assistme.instructions;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tomer on 02/08/14.
 */
public class initMain extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // MakeTopBarSexy();
        setContentView(R.layout.initialize_screen_one);

        startAnimation();
        initScreenOne();
    }
    /*FIRST SCREEN*/
    void startAnimation(){
        findViewById(R.id.hand).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
        findViewById(R.id.day).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
        findViewById(R.id.desc).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
        findViewById(R.id.continue_first_screen).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_right));
    }
    void startAnimationOut(){
        findViewById(R.id.hand).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down_out));
        findViewById(R.id.day).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
        findViewById(R.id.continue_first_screen).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_out));
        findViewById(R.id.desc).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));

        findViewById(R.id.day).getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                setContentView(R.layout.initialize_screen_three);
                startAnimation2();
                initScreenTwo();
            }
        });
    }
    void initScreenOne(){
        final SharedPreferences.Editor sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(initMain.this).edit();
        final Spinner language = (Spinner)findViewById(R.id.desc);
        findViewById(R.id.continue_first_screen).setOnClickListener(new View.OnClickListener() {
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
                startAnimationOut();
            }
        });
    }

    /*SECOND SCREEN*/
    void startAnimation2(){
        findViewById(R.id.container).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
        findViewById(R.id.hand).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down));
        findViewById(R.id.continue_first_screen).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_right));
    }
    void startAnimationOut2(){
        findViewById(R.id.container).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
        findViewById(R.id.hand).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down_out));
        findViewById(R.id.continue_first_screen).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_out));
        findViewById(R.id.hand).getAnimation().setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                setContentView(R.layout.initialize_screen_four);
                initScreenThree();
                startAnimation3();
            }
        });
    }
    void initScreenTwo(){
        findViewById(R.id.continue_first_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences.Editor prefEditor = PreferenceManager
                        .getDefaultSharedPreferences(initMain.this).edit();
                CheckBox answer = (CheckBox) findViewById(R.id.answer);
                CheckBox onstart = (CheckBox)findViewById(R.id.on_start_greeting);
                CheckBox learn = (CheckBox)findViewById(R.id.learn);
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
                startAnimationOut2();
            }
        });
    }

    public String getUsername(){
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        if(!possibleEmails.isEmpty() && possibleEmails.get(0) != null){
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");
            if(parts.length > 0 && parts[0] != null)
                return parts[0];
            else
                return null;
        }else
            return null;
    }

    private String getMyPhoneNumber(){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    private String getMy10DigitPhoneNumber(){
        String s = getMyPhoneNumber();
        return s.substring(2);
    }

    void startAnimation3(){
        findViewById(R.id.email_address).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
        findViewById(R.id.name).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
        findViewById(R.id.phone_number).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
        findViewById(R.id.hand).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down));
        findViewById(R.id.continue_first_screen).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_right));
        TextView mail = (TextView)findViewById(R.id.email_address);
        TextView name = (TextView)findViewById(R.id.name);
        TextView phone = (TextView)findViewById(R.id.phone_number);
        try {
            AccountManager accountManager = AccountManager.get(this);

            Account[] accounts =
                    accountManager.getAccountsByType("com.google");
            mail.setText(accounts[0].name);


        name.setText(getUsername());
        phone.setText(getMy10DigitPhoneNumber());
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    void startAnimationOut3(){
        findViewById(R.id.email_address).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
        findViewById(R.id.name).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
        findViewById(R.id.phone_number).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
        findViewById(R.id.hand).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down_out));
        findViewById(R.id.continue_first_screen).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_out));
        findViewById(R.id.name).getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                final CheckBox checkBox = new CheckBox(getApplicationContext());
                checkBox.setText("Go through instructions");
                checkBox.setTextColor(getResources().getColor(android.R.color.black));
                Dialog dialog = new AlertDialog.Builder(initMain.this)
                        .setTitle("Completed")
                        .setView(checkBox)
                        .setMessage("Initialization completed")
                        .setPositiveButton("Let's GO!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (checkBox.isChecked()) {
                                    startActivity(new Intent(getApplicationContext(), instructions.class));
                                } else {
                                    startActivity(new Intent(getApplicationContext(),Main1.class));
                                }
                                finish();
                                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                                prefEditor.putBoolean("initialized", true);
                                prefEditor.commit();
                            }
                        })
                        .show();

            }
        });
    }
    void initScreenThree(){
        final EditText name = (EditText)findViewById(R.id.name);
        final EditText email = (EditText)findViewById(R.id.email_address);
        final EditText phone = (EditText)findViewById(R.id.phone_number);
        final SharedPreferences.Editor prefEditor = PreferenceManager
                .getDefaultSharedPreferences(initMain.this).edit();
        findViewById(R.id.continue_first_screen).setOnClickListener(new View.OnClickListener() {
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
                    startAnimationOut3();
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

    void MakeTopBarSexy(){
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0BFFFFFF")));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#0BFFFFFF")));
        int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        if (actionBarTitleId > 0) {
            TextView title = (TextView) findViewById(actionBarTitleId);
            if (title != null) {
                title.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }
}
