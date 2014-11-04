package com.pin.assistme;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

import com.assist.me.R;

import org.json.JSONException;

/**
 * Created by tomer on 09/07/14.
 */
public class weather_preferences extends PreferenceActivity {
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.weather_pref);
        findPreference("CardsWeatherLocation").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                PreferenceManager
                        .getDefaultSharedPreferences(weather_preferences.this).edit().putString("CardsWeatherLocationBackup",  PreferenceManager
                        .getDefaultSharedPreferences(weather_preferences.this).getString("CardsWeatherLocation","")).commit();
                pDialog = new ProgressDialog(weather_preferences.this);
                pDialog.setTitle("Verifying weather location");
                pDialog.show();
                EditTextPreference dit = (EditTextPreference) findPreference("CardsWeatherLocation");
                if (dit.getText().toString().trim().equals("")) {
                    PreferenceManager
                            .getDefaultSharedPreferences(weather_preferences.this).edit().putString("CardsWeatherLocation", "").commit();
                    pDialog.hide();
                } else {
                    verifyWeather(dit.getText().toString());
                }
                return true;
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
            EditTextPreference edit = (EditTextPreference) findPreference("CardsWeatherLocation");
            String tmp = ("" + Math.round((weather.temperature.getTemp() - 275.15)));
            if(tmp.equals("-275")) {
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "City not found", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Make sure you have internet connection and try again",Toast.LENGTH_LONG).show();
                PreferenceManager
                        .getDefaultSharedPreferences(weather_preferences.this).edit().putString("CardsWeatherLocation",  PreferenceManager
                        .getDefaultSharedPreferences(weather_preferences.this).getString("CardsWeatherLocationBackup","")).commit();
            }
            else {
                PreferenceManager
                        .getDefaultSharedPreferences(weather_preferences.this).edit().putString("CardsWeatherLocation", edit.getText().toString()).commit();
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
