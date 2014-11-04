package com.initializations;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.assist.me.R;
import com.pin.assistme.JSONWeatherParser;
import com.pin.assistme.MultipleItemList;
import com.pin.assistme.Weather;
import com.pin.assistme.WeatherForecast;
import com.pin.assistme.WeatherHttpClient;

import org.json.JSONException;

/**
 * Created by tomer on 28/07/14.
 */
public class initOne extends Activity {
    boolean weather;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MakeTopBarSexy();
        setContentView(R.layout.cards_init_one);
        /**CALLING SCREEN ONE**/
        startAnimation();
        initScreenOne();
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

    /*FIRST SCREEN*/
    void startAnimation(){
        findViewById(R.id.day).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
        findViewById(R.id.puthandhere).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_right));
    }
    void startAnimationOut(){
        findViewById(R.id.day).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
        findViewById(R.id.puthandhere).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_out));
        findViewById(R.id.day).getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                setContentView(R.layout.cards_init_two);
                startAnimation2();
                initScreenTwo();
            }
        });
    }
    void initScreenOne(){
        findViewById(R.id.puthandhere).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimationOut();
            }
        });
    }

    /*SECOND SCREEN*/
    void startAnimation2(){
        findViewById(R.id.container).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
        findViewById(R.id.day).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down));
        findViewById(R.id.puthandhere).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_right));
    }
    void startAnimationOut2(){
        findViewById(R.id.container).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
        findViewById(R.id.day).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down_out));
        findViewById(R.id.puthandhere).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_out));
        findViewById(R.id.day).getAnimation().setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                CheckBox cb = (CheckBox)findViewById(R.id.radioButton);
                if(cb.isChecked()) {
                    PreferenceManager
                            .getDefaultSharedPreferences(initOne.this).edit().putBoolean("CardsWeather", true).commit();
                    setContentView(R.layout.card_init_weather);
                    startAnimation3();
                    initScreenThree();
                }
                else{
                    PreferenceManager
                            .getDefaultSharedPreferences(initOne.this).edit().putString("CardsWeatherLocation", "").commit();
                    startActivity(new Intent(getApplicationContext(), MultipleItemList.class));
                    finish();
                }
            }
        });
    }
    void initScreenTwo(){
        findViewById(R.id.puthandhere).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimationOut2();
            }
        });
    }

    /*WEATHER SCREEN*/
    void startAnimation3(){
        findViewById(R.id.mail).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
        findViewById(R.id.day).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down));
        findViewById(R.id.puthandhere).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_right));
    }
    void startAnimationOut3(){
        findViewById(R.id.mail).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
        findViewById(R.id.day).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down_out));
        findViewById(R.id.puthandhere).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_out));
        findViewById(R.id.day).getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), MultipleItemList.class));

            }
        });
    }
    void initScreenThree(){

        findViewById(R.id.puthandhere).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new ProgressDialog(initOne.this);
                pDialog.setTitle("Verifying weather location");
                pDialog.show();
                EditText edit = (EditText) findViewById(R.id.mail);
                edit.setAllCaps(true);
                if (edit.getText().toString().trim().equals("")) {
                    edit.setError("Required Field!");
                    edit.setFocusable(true);
                    PreferenceManager
                            .getDefaultSharedPreferences(initOne.this).edit().putString("CardsWeatherLocation", "").commit();
                    pDialog.hide();
                } else {
                    verifyWeather(edit.getText().toString());
                }
            }
        });
    }

    void verifyWeather(String city){
        JSONForecastWeatherTask_cards task1 = new JSONForecastWeatherTask_cards();
        task1.execute(new String[]{city, "en", "3"});
        JSONWeatherTask_cards task = new JSONWeatherTask_cards(); //whats the difference one is for getting the weather information from the server and one is for configuring from another class and executing the adapter
        task.execute(new String[]{city, "en"});
    }


    private class JSONWeatherTask_cards extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            try {
                String data = ((new WeatherHttpClient()).getWeatherData(params[0], params[1]));
                try {
                    weather = JSONWeatherParser.getWeather(data);
                    System.out.println("Weather [" + weather + "]");
                    // Let's retrieve the icon
                    weather.iconData = ((new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return weather;
        }


        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            EditText edit = (EditText) findViewById(R.id.mail);
            String tmp = ("" + Math.round((weather.temperature.getTemp() - 275.15)));
            if(tmp.equals("-275")) {
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "City not found", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Make sure you have internet connection and try again",Toast.LENGTH_LONG).show();
            }
            else {
                startAnimationOut3();
                PreferenceManager
                        .getDefaultSharedPreferences(initOne.this).edit().putString("CardsWeatherLocation", edit.getText().toString()).commit();
                pDialog.hide();
            }
        }

    }

    private class JSONForecastWeatherTask_cards extends AsyncTask<String, Void, WeatherForecast> {

        @Override
        protected WeatherForecast doInBackground(String... params) {

            String data = ( (new WeatherHttpClient()).getForecastWeatherData(params[0], params[1], params[2]));
            WeatherForecast forecast = new WeatherForecast();
            try {
                forecast = JSONWeatherParser.getForecastWeather(data);
                System.out.println("Weather ["+forecast+"]");
                // Let's retrieve the icon
                //weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            return forecast;

        }
    }

}
