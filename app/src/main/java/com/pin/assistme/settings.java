/*
package com.pin.assistme;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.*;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.assist.me.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class settings extends Activity {

    int language;
    String com_name,backuped_user_name,user_name;
    TextView animation_text;
    boolean onstart ,notifcation= true;
    boolean speakback , card_bool= true;
    boolean bitchstring,answer_in_new_activity ,listen_on_start,siri_bool = false;

    private static final int HELLO_ID = 1;
    int textsizedp = 17;
    Spinner animation;
    CheckBox bitch,
            card_cb,
            oonstartg,
            listen_on_start_check_box,
            answer_in_new_activity_checkbox,
            speak,
            notification_cb,
            siri_cb;

    RelativeLayout rel;

    Button reset,
    custom_color_but,
  //  dark,
    lang,
    image,
    setcomname,
    setname,
    choose_image;
    SeekBar textsize;
    TextView textsizetext;
    String animation_selected = "normal";
    String color = "#33B5E5";
    RelativeLayout background;
    //Navdrawer
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    CustomDrawerAdapter adapter;

    ProgressDialog dialog;

    List<DrawerItem> dataList;
    String action_bar_color, nav = color;
    String back = "fffff";
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
            setContentView(R.layout.settings);
            initialize();
            sharedpref();
            initialize2();
            handleonstarts();
            initialize_nav_drawer();
        /*
            AdView adView = (AdView)this.findViewById(R.id.adView_nav);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            AdView adView2 = (AdView)this.findViewById(R.id.adView2_nav);
            AdRequest adRequest2 = new AdRequest.Builder().build();
            adView2.loadAd(adRequest2);
            */
/*
            onclicks();
            set_custom_color();
        }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent backtomain = new Intent(getApplicationContext(), Main1.class);
            startActivity(backtomain);
            animations();
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    public void sharedpref(){
        SharedPreferences prefEditor =getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if(prefEditor.contains("backed_up_user_name")){
            backuped_user_name = prefEditor.getString("backed_up_user_name", "");
        }
        if (prefEditor.contains("card"))
        {
            card_bool = prefEditor.getBoolean("card", true);
        }
        if (prefEditor.contains("action_bar"))
        {
            action_bar_color = prefEditor.getString("action_bar","");
        }
        else{
            action_bar_color = color;
        }
        if (prefEditor.contains("back"))
        {
            back = prefEditor.getString("back","");
        }
        if (prefEditor.contains("nav"))
        {
            nav = prefEditor.getString("nav","");
        }
        if (prefEditor.contains("color")){
            color = prefEditor.getString("color", "");
        }
        if (prefEditor.contains("siri")) {
            siri_bool = prefEditor.getBoolean("siri", true);
        }
        if (prefEditor.contains("animation")) {
            animation_selected = prefEditor.getString("animation", "");
        }
        if (prefEditor.contains("intYay")) {
            language = prefEditor.getInt("intYay", 0);
        }
        if (prefEditor.contains("notification")){
            notifcation = prefEditor.getBoolean("notification", true);
        }
        if (prefEditor.contains("textsize")){
            textsizedp = prefEditor.getInt("textsize", 0);
        }
        if (prefEditor.contains("answer_in_new_activity")){
            answer_in_new_activity = prefEditor.getBoolean("answer_in_new_activity", true);
        }
        if (prefEditor.contains("listen_on_start")){
            listen_on_start = prefEditor.getBoolean("listen_on_start", true);
        }
        if (prefEditor.contains("onstart")){
            onstart = prefEditor.getBoolean("onstart", true);
        }
        if (prefEditor.contains("com_name")){
            com_name = prefEditor.getString("com_name", "");
        }
        if (prefEditor.contains("name")){
            user_name = prefEditor.getString("name", "");
        }
        if (prefEditor.contains("speakback")){
            speakback = prefEditor.getBoolean("speakback", true);
        }
        if (prefEditor.contains("bitchstring")){
            bitchstring = prefEditor.getBoolean("bitchstring", true);
        }
    }
    public void initialize(){
      //  choose_image = (Button)findViewById(R.id.choose_background);
        card_cb = (CheckBox)findViewById(R.id.enable_card);
        background = (RelativeLayout)findViewById(R.id.rel);
        custom_color_but = (Button)findViewById(R.id.color_setting_button);
        animation_text = (TextView)findViewById(R.id.animation_text);
        animation = (Spinner)findViewById(R.id.animation);
        siri_cb = (CheckBox)findViewById(R.id.flat);
        notification_cb = (CheckBox)findViewById(R.id.notification);
        listen_on_start_check_box = (CheckBox)findViewById(R.id.listenonstart);
        answer_in_new_activity_checkbox = (CheckBox) findViewById(R.id.opennewactivity);
        reset = (Button)findViewById(R.id.reset);
        bitch = (CheckBox) findViewById(R.id.bitch);
        speak = (CheckBox)findViewById(R.id.answer);
        rel = (RelativeLayout)findViewById(R.id.rel);
        image = (Button)findViewById(R.id.imagepick);
        setname = (Button)findViewById(R.id.yourname);
        setcomname = (Button)findViewById(R.id.myname);
       // dark = (Button)findViewById(R.id.dark);
        lang = (Button)findViewById(R.id.lang);
        oonstartg = (CheckBox)findViewById(R.id.checkbox);
        textsize = (SeekBar)findViewById(R.id.textsize);
        textsizetext = (TextView)findViewById(R.id.textviewsize);
    }
    public void onclicks(){
        card_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    card_bool = true;
                }
                else{
                    card_bool = false;
                }
                SharedPreferences.Editor notification_pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                notification_pref.putBoolean("card", card_bool);
                notification_pref.commit();
            }
        });
        custom_color_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ColorPickerActivity.class));
            }
        });
        animation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                switch (position) {
                    case 0:
                        animation_selected = "normal";
                        break;
                    case 1:
                        animation_selected = "left_to_right";
                        break;
                    case 2:
                        animation_selected = "fade";
                        break;
                    case 3:
                        animation_selected = "normal_fade";
                        break;
                    case 4:
                        animation_selected = "left_to_right_fade";
                        break;
                }
                prefEditor.putString("animation", animation_selected);
                prefEditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"Nothing selected",Toast.LENGTH_SHORT).show();

            }
        });
       /* choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), pickiamgefromgallery.class));
            }
        });*//*

        notification_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    notifcation = true;
                    SharedPreferences.Editor notification_pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                    notification_pref.putBoolean("notification", notifcation);
                    notification_pref.commit();
                    String ns = Context.NOTIFICATION_SERVICE;
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
                    int icon = R.drawable.mini_icon;
                    CharSequence tickerText = "Assist Me"; // ticker-text
                    long when = System.currentTimeMillis();
                    Context context = getApplicationContext();
                    CharSequence contentTitle = "Assist-Me";
                    CharSequence contentText = "Assist Me is running in background";
                    Intent notificationIntent = new Intent(getApplicationContext(), settings.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
                    Notification notification = new Notification(icon, tickerText, when);
                    notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
                    notification.flags = Notification.FLAG_ONGOING_EVENT;
                    mNotificationManager.notify(HELLO_ID, notification);
                }
                else{
                    notifcation = false;
                    SharedPreferences.Editor notification_pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                    notification_pref.putBoolean("notification", notifcation);
                    notification_pref.commit();
                    String ns = Context.NOTIFICATION_SERVICE;
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
                    mNotificationManager.cancelAll();
                }
            }
        });
        listen_on_start_check_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    listen_on_start = true;
                }
                else{
                    listen_on_start = false;
                }
                SharedPreferences.Editor listen_on_start_pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                listen_on_start_pref.putBoolean("listen_on_start", listen_on_start);
                listen_on_start_pref.commit();
                handleonstarts();
            }
        });
        answer_in_new_activity_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    answer_in_new_activity = true;
                    image.setEnabled(true);
                }
                else {
                    answer_in_new_activity = false;
                    image.setEnabled(false);

                }
                SharedPreferences.Editor answer_in_new_activity_pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                answer_in_new_activity_pref.putBoolean("answer_in_new_activity", answer_in_new_activity);
                answer_in_new_activity_pref.commit();
                handleonstarts();
            }
        });
        textsize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textsizedp = textsize.getProgress();
                if(textsizedp < 10){
                    textsize.setProgress(10);
                    textsizedp = 10;
                }
                settextsize();
                SharedPreferences.Editor textsizepref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                textsizepref.putInt("textsize", textsizedp);
                textsizepref.commit();
                textsizetext.setText("Text size = " + textsizedp);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textsizedp = textsize.getProgress();
                if(textsizedp < 10){
                    textsize.setProgress(10);
                    textsizedp = 10;
                }
                settextsize();
                SharedPreferences.Editor textsizepref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                textsizepref.putInt("textsize", textsizedp);
                textsizepref.commit();
                textsizetext.setText("Text size = " + textsizedp);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textsizedp = textsize.getProgress();
                if(textsizedp < 10){
                    textsize.setProgress(10);
                    textsizedp = 10;
                }
                settextsize();
                SharedPreferences.Editor textsizepref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                textsizepref.putInt("textsize", textsizedp);
                textsizepref.commit();
                textsizetext.setText("Text size = " + textsizedp);
            }
        });
        bitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AlertDialog.Builder alert = new AlertDialog.Builder(settings.this);
                    alert.setTitle("Alert");
                    alert.setMessage("By pressing ok you confirm that you are over 13");
                    alert.setCancelable(false);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if(user_name!=null||user_name!="BITCH"){
                                backuped_user_name = user_name;
                                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                                prefEditor.putString("backed_up_user_name", backuped_user_name);
                                prefEditor.commit();
                            }
                            bitchstring = true;
                            SharedPreferences.Editor bitchprefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                            bitchprefs.putBoolean("bitchstring", bitchstring);
                            bitchprefs.commit();
                            user_name = "BITCH";
                            SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                            prefEditor.putString("name", user_name);
                            prefEditor.commit();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                          bitch.setChecked(false);
                        }
                    });
                    alert.show();
                }
                if(!isChecked) {
                    bitchstring = false;
                    SharedPreferences.Editor bitchprefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                    bitchprefs.putBoolean("bitchstring", bitchstring);
                    bitchprefs.commit();
                    user_name = backuped_user_name;
                    backuped_user_name = user_name;
                    SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                    prefEditor.putString("name", user_name);
                    prefEditor.commit();
                }
            }
        });
        speak.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    speakback = true;
                    SharedPreferences.Editor speekprefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                    speekprefs.putBoolean("speakback", speakback);
                    speekprefs.commit();
                }
                if (!isChecked) {
                    speakback = false;
                    SharedPreferences.Editor speekprefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                    speekprefs.putBoolean("speakback", speakback);
                    speekprefs.commit();
                }
                handleonstarts();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(settings.this);
                alert.setTitle("Reset app to default");
                alert.setMessage("By pressing ok all the app data will be deleted and the app will be closed");
                alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                                resetdata();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }
        });
        siri_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    siri_bool = true;
                    SharedPreferences.Editor siri_pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                    siri_pref.putBoolean("siri", siri_bool);
                    siri_pref.commit();

                }
                if(!isChecked){
                    siri_bool = false;
                    SharedPreferences.Editor siri_pref = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                    siri_pref.putBoolean("siri", siri_bool);
                    siri_pref.commit();
                }
                handleonstarts();
            }
        });
        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lang = new Intent(settings.this, spinnerindipendent.class);
                startActivity(lang);
                finish();
            }
        });
       /* dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent themes = new Intent(settings.this, themes.class);
                startActivity(themes);
            }
        });
       */ /*image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pick = new Intent(settings.this, chooseimage.class);
                startActivity(pick);
            }
        });
        oonstartg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    onstart = true;
                    SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                    prefEditor.putBoolean("onstart", onstart);
                    prefEditor.commit();
                }
                else{
                    onstart = false;
                    SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                    prefEditor.putBoolean("onstart", onstart);
                    prefEditor.commit();
                }
                handleonstarts();
            }
        });
        setcomname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(settings.this);
                alert.setTitle("What is my name?");
                alert.setMessage("Enter Your Assistant Name Here");
                final EditText input = new EditText(settings.this);
                input.setHint("Assistant name");
                alert.setView(input);
                input.setText(com_name);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        com_name = input.getText().toString();
                        SharedPreferences.Editor namecom = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                        namecom.putString("com_name", com_name);
                        namecom.commit();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();
            }

        });
        setname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(settings.this);
                alert.setTitle("How do you want me to call you?");
                alert.setMessage("Enter Your Name Here");
                final EditText input = new EditText(settings.this);
                input.setHint("Your name");
                alert.setView(input);
                input.setText(backuped_user_name);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (bitch.isChecked()) {
                            Toast.makeText(getApplicationContext(), "Disable Bitch Mode First, Bitch", Toast.LENGTH_SHORT).show();
                        } else {
                            user_name = input.getText().toString();
                            SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                            prefEditor.putString("name", user_name);
                            prefEditor.commit();
                            if (!user_name.equals("BITCH") || user_name.equals(null)) {
                                backuped_user_name = user_name;
                                prefEditor.putString("backed_up_user_name", backuped_user_name);
                                prefEditor.commit();
                            }
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();
            }
        });
    }
    void set_custom_color() {
        mDrawerList.setBackgroundColor(Color.parseColor(nav));

        getActionBar().setBackgroundDrawable(new
                ColorDrawable(Color.parseColor(action_bar_color)));
        if(back.contains("#")) {
            background.setBackgroundColor(Color.parseColor(back));
        }
        else{
            background.setBackgroundColor(Color.WHITE);
        }
    }
    public void initialize2() {
        textsize.setProgress(textsizedp);
        settextsize();
         switch (language){
             case 1:
                break;
            case 2:
                custom_color_but.setGravity(Gravity.CENTER | Gravity.RIGHT);
                animation_text.setGravity(Gravity.CENTER | Gravity.RIGHT);
                reset.setGravity(Gravity.CENTER | Gravity.RIGHT);
                //dark.setGravity(Gravity.CENTER | Gravity.RIGHT);
                lang.setGravity(Gravity.CENTER | Gravity.RIGHT);
                image.setGravity(Gravity.CENTER | Gravity.RIGHT);
                setcomname.setGravity(Gravity.CENTER | Gravity.RIGHT);
                setname.setGravity(Gravity.CENTER | Gravity.RIGHT);
                bitch.setGravity(Gravity.CENTER | Gravity.RIGHT);
                oonstartg.setGravity(Gravity.CENTER | Gravity.RIGHT);
                listen_on_start_check_box.setGravity(Gravity.CENTER | Gravity.RIGHT);
                answer_in_new_activity_checkbox.setGravity(Gravity.CENTER | Gravity.RIGHT);
                speak.setGravity(Gravity.CENTER | Gravity.RIGHT);
                notification_cb.setGravity(Gravity.CENTER | Gravity.RIGHT);
                siri_cb.setGravity(Gravity.CENTER | Gravity.RIGHT);
                card_cb.setGravity(Gravity.CENTER| Gravity.RIGHT);
                card_cb.setText("אפשר קלפים חכמים");
                custom_color_but.setText("בחר צבע ערכת נושא");
                animation_text.setText("אנימציות מעבר");
                reset.setText("שחזור לברירת מחדל");
                //dark.setText("ערכות נושא");
                lang.setText("שפה");
                image.setText("תמונה");
                setcomname.setText("שם העוזרת");
                setname.setText("השם שלך");
                bitch.setText("אמאשך");
                oonstartg.setText("שלום בפתיחה");
                listen_on_start_check_box.setText("הקשב בפתיחה");
                answer_in_new_activity_checkbox.setText("מענה במסך חדש");
                speak.setText("מענה לאחר שאלה");
                notification_cb.setText("אפשר התראה");
                siri_cb.setText("הראה כמו סירי");


                break;
            case 3:

                break;
    }
        if(animation_selected.equals("normal")){
           animation.setSelection(0);
        }
        else if(animation_selected.equals("left_to_right")){
            animation.setSelection(1);
        }
        else if(animation_selected.equals("fade")){
            animation.setSelection(2);
        }
        else if(animation_selected.equals("normal+fade")){
            animation.setSelection(3);
        }
        else if(animation_selected.equals("left_to_right_fade")){
            animation.setSelection(4);
        }
        else{
            animation.setSelection(0);
        }
        if(!card_bool){
            card_cb.setChecked(false);
        }
        if(notifcation == false){
            notification_cb.setChecked(false);
        }
        if(answer_in_new_activity==true){
            answer_in_new_activity_checkbox.setChecked(true);
        }
        else{
            answer_in_new_activity_checkbox.setChecked(false);
        }
        if(bitchstring==true){
            bitch.setChecked(true);
        }
        if(listen_on_start ==true){
            listen_on_start_check_box.setChecked(true);
        }
        else{
            listen_on_start_check_box.setChecked(false);
        }
        if(answer_in_new_activity_checkbox.isChecked()){
            image.setEnabled(true);
        }
        else{
            image.setEnabled(false);
            image.setHint("you must enable answer in new activity");
        }
        if(onstart==true){
            oonstartg.setChecked(true);
        }
        else if(onstart =false){
            oonstartg.setChecked(false);
        }
        if(speakback==false){
            speak.setChecked(false);
        }
        if(siri_bool==true) {
            siri_cb.setChecked(true);
        }
 }
    public void resetdata() {
        clearApplicationData();
    }
    public void Makeitflat(){
        rel.setBackgroundResource(R.drawable.siri_back);
    }
    public void settextsize(){
        card_cb.setTextSize(textsizedp);
        custom_color_but.setTextSize(textsizedp);
        animation_text.setTextSize(textsizedp);
        lang.setTextSize(textsizedp);
        siri_cb.setTextSize(textsizedp);
        setname.setTextSize(textsizedp);
        setcomname.setTextSize(textsizedp);
        oonstartg.setTextSize(textsizedp);
        //dark.setTextSize(textsizedp);
        image.setTextSize(textsizedp);
        reset.setTextSize(textsizedp);
        bitch.setTextSize(textsizedp);
        speak.setTextSize(textsizedp);
        answer_in_new_activity_checkbox.setTextSize(textsizedp);
        listen_on_start_check_box.setTextSize(textsizedp);
        notification_cb.setTextSize(textsizedp);
    }
    public void handleonstarts(){
     if(speak.isChecked()){
         answer_in_new_activity_checkbox.setEnabled(true);
     }
        else{
         answer_in_new_activity_checkbox.setEnabled(false);
         answer_in_new_activity_checkbox.setChecked(false);

     }
        if(listen_on_start_check_box.isChecked()){
            oonstartg.setChecked(false);
            oonstartg.setEnabled(false);
        }
        else{
            oonstartg.setEnabled(true);
        }
        if(oonstartg.isChecked()){
            listen_on_start_check_box.setChecked(false);
            listen_on_start_check_box.setEnabled(false);
        }
        else{
            listen_on_start_check_box.setEnabled(true);
        }
    }
    public void clearApplicationData() {

        try {
            // clearing app data
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear com.assist.me");
            Intent i = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.assist.me");
            getApplicationContext().startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            Intent i = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.assist.me");
            getApplicationContext().startActivity(i);
        }
        finally {
            Intent i = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.assist.me");
            getApplicationContext().startActivity(i);
        }
    }
    void animations() {
        if (animation_selected.equals("normal")) {
            overridePendingTransition(R.anim.bottom_to_top, R.anim.top_to_bottom);
        } else if (animation_selected.equals("left_to_right")) {
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        } else if(animation_selected.equals("fade")){
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        }
        else if (animation_selected.equals("normal_fade")){
            overridePendingTransition(R.anim.bottom_to_top_fade_in, R.anim.top_to_bottom_fade_out);
        }
        else{
            overridePendingTransition(R.anim.left_to_rigt_fade_in, R.anim.right_to_left_fade_out);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
            set_custom_color();
    }
    //navigation drawer
    public void initialize_nav_drawer(){
    dataList = new ArrayList<DrawerItem>();
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerList = (ListView) findViewById(R.id.left_drawer);

    mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
            GravityCompat.START);
    dataList.add(new DrawerItem("Home", R.drawable.mini_icon));
    dataList.add(new DrawerItem("Features", R.drawable.ic_action_help_inverted));
    dataList.add(new DrawerItem("Become an alpha tester", R.drawable.ic_action_group_inverted));
    dataList.add(new DrawerItem("About", R.drawable.ic_action_about_inverted));
    adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
            dataList);

    mDrawerList.setAdapter(adapter);
    mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
   mDrawerList.setBackgroundColor(R.color.Black);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);

    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
            R.drawable.ic_drawer, R.string.open,
            R.string.close) {
        public void onDrawerClosed(View view) {
            getActionBar().setTitle("Assist-Me");
            invalidateOptionsMenu(); // creates call to
            // onPrepareOptionsMenu()
        }

        public void onDrawerOpened(View drawerView) {
            getActionBar().setTitle(mDrawerTitle);
            getActionBar().setTitle("Assist-Me");
            invalidateOptionsMenu(); // creates call to
            // onPrepareOptionsMenu()
        }
    };

    mDrawerLayout.setDrawerListener(mDrawerToggle);
}
    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 0:

                startActivity(new Intent(getApplicationContext(),Main1.class));
                animations();
                finish();
                break;
            case 1:

                startActivity(new Intent(getApplicationContext(),menu.class));
                animations();
                finish();
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(),alpha.class));
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(),about.class));
                break;
            default:
                break;
        }



        mDrawerList.setItemChecked(possition, true);
        setTitle(dataList.get(possition).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.syncState();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
            switch (item.getItemId()) {
                case R.id.settings:
                    switch (language) {
                        case 1:
                            startActivity(new Intent(getApplicationContext(), Main1.class));
                            break;
                        case 2:
                            startActivity(new Intent(getApplicationContext(), Main1.class));
                            break;
                        case 3:
                            startActivity(new Intent(getApplicationContext(), Main1.class));
                            finish();
                            break;
                    }
                    break;
                case R.id.item1:
                    switch (language) {
                        case 1:
                            startActivity(new Intent(getApplicationContext(), menu.class));
                            break;
                        case 2:
                            startActivity(new Intent(getApplicationContext(), menu_heb.class));
                            break;
                        case 3:
                            startActivity(new Intent(getApplicationContext(), menu_russ.class));
                            finish();
                            break;
                    }
                    break;
                case R.id.item2:
                    switch (language) {
                        case 1:
                            startActivity(new Intent(getApplicationContext(), alpha.class));
                            break;
                        case 2:
                            startActivity(new Intent(getApplicationContext(), alpha.class));
                            break;
                        case 3:
                            startActivity(new Intent(getApplicationContext(), alpha.class));
                            finish();
                            break;
                    }
                    break;
                case R.id.item3:
                    switch (language) {
                        case 1:
                            startActivity(new Intent(getApplicationContext(), about.class));
                            break;
                        case 2:
                            startActivity(new Intent(getApplicationContext(), about.class));
                            break;
                        case 3:
                            startActivity(new Intent(getApplicationContext(), about.class));
                            finish();
                            break;
                        default:
                    }

            }
        return false;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);

        }
    }

}
*/