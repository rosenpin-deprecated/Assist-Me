package com.pin.assistme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.assist.me.R;
import com.initializations.initOne;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

/**
 * Created by tomer on 24/07/14.
 */
public class MultipleItemList extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    ArrayList<Drawable> topassdraw = new ArrayList<Drawable>();
    ArrayList<View.OnClickListener> topassonclick = new ArrayList<View.OnClickListener>();
    private MyCustomAdapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ListView list;
    String weatherfor;
    boolean looking_for_weather = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInitialized();
            setContentView(R.layout.list);
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light,
                    android.R.color.white, android.R.color.holo_blue_light,
                    android.R.color.white);
        swipeRefreshLayout.setRefreshing(true);
            list = (ListView) findViewById(R.id.list);
            init();

            weather(PreferenceManager
                    .getDefaultSharedPreferences(MultipleItemList.this).getString("CardsWeatherLocation",""));
        }
    void checkInitialized(){
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        // if(sharedPrefs.contains("PrefCardInit"))
        if(!sharedPrefs.contains("CardsWeatherLocation")){
            startActivity(new Intent(getApplicationContext(),initOne.class));
            finish();
        }
    }
    void init_gps(){
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if(gpsStatus){
        try {
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
        else{
            weather("No location");
        }
    }
    void oncreatewithoutview(){

        setContentView(R.layout.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light,
                android.R.color.white, android.R.color.holo_blue_light,
                android.R.color.white);
        list = (ListView) findViewById(R.id.list);
        init();
        weather(PreferenceManager
                .getDefaultSharedPreferences(MultipleItemList.this).getString("CardsWeatherLocation",""));
    }

    void init_fav_apps(int place_in_list){
        try {
            favorite_apps info = new favorite_apps(MultipleItemList.this);
            info.open();
            String data = info.getData();
            info.close();
            String[] lines = data.split(System.getProperty("line.separator"));
            String appName_temp = lines[lines.length - place_in_list];
            final String appName = appName_temp.substring(appName_temp.lastIndexOf("|| ") + 3);
            try {
                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    topassdraw.add(getPackageManager().getApplicationIcon(appName));
                } else {
                    topassdraw.add(getPackageManager().getApplicationIcon(appName));
                }
                topassonclick.add(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(appName);
                        try {
                            startActivity(LaunchIntent);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
    void init(){
        setTheme(android.R.style.Theme_Holo_Light);
        list.setBackgroundColor(Color.parseColor("#e3e3e3"));
        list.setDivider(null);
        mAdapter = new MyCustomAdapter(MultipleItemList.this,R.layout.weather_item);
        View.OnTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        list,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mAdapter.remove(mAdapter.getItem(position));
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
    }
    void weather(String city){
        weatherfor = city;
        JSONForecastWeatherTask_cards task1 = new JSONForecastWeatherTask_cards();
        task1.execute(new String[]{weatherfor, "en", "3"});
        JSONWeatherTask_cards task = new JSONWeatherTask_cards(); //whats the difference one is for getting the weather information from the server and one is for configuring from another class and executing the adapter
        task.execute(new String[]{weatherfor, "en"});
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
        oncreatewithoutview();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 5 * 1000);
    }

    public static int getTypes(String type){
        return 0;
    }

    //Weather
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(android.location.Location loc) {
            if (!looking_for_weather) {
                looking_for_weather = true;

                String longitude = "Longitude: " + loc.getLongitude();
                String latitude = "Latitude: " + loc.getLatitude();

        /*------- To get city name from coordinates -------- */
                String cityName = null;
                Geocoder gcd = new Geocoder(getBaseContext(), Locale.ENGLISH);
                List<Address> addresses;
                try {
                    addresses = gcd.getFromLocation(loc.getLatitude(),
                            loc.getLongitude(), 1);
                    if (addresses.size() > 0)
                        System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                try {
                    String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                            + cityName;
                    Log.d("Location = ", s);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                try {
                    if (!cityName.equals(null)) {
                        weather(cityName);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error finding location", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
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
            String tmp = ("" + Math.round((weather.temperature.getTemp() - 275.15)));
            String condition = (weather.currentCondition.getCondition() + " (" + weather.currentCondition.getDescr() + ")");
            String windSpeed = ("" + weather.wind.getSpeed() + " mps");
            String Humidity = ("" + weather.currentCondition.getHumidity() + "%");
            int image;
            if (condition.contains("rain")) {
                image = (R.drawable.rain);
            } else if (condition.contains("light")) {
                image =R.drawable.lightning;
            } else if (condition.contains("cloud")) {
                image =(R.drawable.cloudy);
            } else if (condition.contains("wind")) {
                image =(R.drawable.wind);
            } else {
                image =(R.drawable.sun);
            }
            if(weatherfor.equals("No location")){
                mAdapter.addWeather("x", image, "x", Humidity, windSpeed, condition);
            }
            else {
                if (Integer.valueOf(tmp) != -275) {
                    mAdapter.addWeather(weatherfor, image, tmp, Humidity, windSpeed, condition);
                } else {
                    mAdapter.addWeather("y", image, "y", Humidity, windSpeed, condition);
                }
            }
            Calendar c = Calendar.getInstance();
            String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
            String minute = String.valueOf(c.get(Calendar.MINUTE));
            if (hour.length() < 2) {
                hour = "0" + hour;
            }
            if (minute.length() < 2) {
                minute = "0" + minute;
            }

          //  mAdapter.addTop(imageee);
            mAdapter.addItem(hour + ":" + minute);
            for(int i = 0;i<12;i++) {
                init_fav_apps(i);
            }
            mAdapter.addFavapp(topassdraw,topassonclick);
            mAdapter.addSettings();
            // Create a ListView-specific touch listener. ListViews are given special treatment because
            // by default they handle touches for their list items... i.e. they're in charge of drawing
            // the pressed state (the list selector), handling list item clicks, etc.
            list.setAdapter(mAdapter);
            swipeRefreshLayout.setRefreshing(false);

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

    //ADAPTER
    private class MyCustomAdapter extends ArrayAdapter<String> {
        private String place;
        int image;
        private String temp;
        private String humidity;
        private String windspeed;
        private String condition;
        private List<Drawable> apps_icon;
        private List<View.OnClickListener> onClickListeners;
        private int imageTop;

        private String time;

        public static final int TYPE_WEATHER = 0;
        private static final int TYPE_FAV_APPS= 1;
        private static final int TYPE_SETTINGS= 3;
        private static final int TYPE_TIME = 2;
        private static final int TYPE_TOP = 5;
        private static final int TYPE_MAX_COUNT = 4 + 1;

        private List<String> mData = new ArrayList<String>();

        private LayoutInflater mInflater;

        private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();

        public MyCustomAdapter(Context context, int resource) {
            super(context, resource);
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addWeather(final String place,final int image , final String temp,final String humidity,final String windspeed,final String condition) {
            mData.add(place);
            this.place = place;
            this.image = image;
            this.temp = temp;
            this.humidity = humidity;
            this.windspeed = windspeed;
            this.condition = condition;
            notifyDataSetChanged();
        }

        public void addTop(final int helloworld){
            mData.add(place);
            this.imageTop = helloworld;
            notifyDataSetChanged();
        }

        public void addFavapp(ArrayList<Drawable> icon,ArrayList<View.OnClickListener> onClickListeners){
            mData.add(place);
            this.apps_icon = icon;
            this.onClickListeners = onClickListeners;
            notifyDataSetChanged();
        }

        public void addItem(final String item) {
            this.time = item;
            mData.add(item);
            // save separator position
            mSeparatorsSet.add(mData.size() - 1);
            notifyDataSetChanged();
        }

        public void addSettings() {
            mData.add("");
            // save separator position
            mSeparatorsSet.add(mData.size() - 1);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return TYPE_MAX_COUNT;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            int type = getItemViewType(position);
            System.out.println("getView " + position + " " + convertView + " type = " + type);
            if (convertView == null) {
                holder = new ViewHolder();
                switch (type) {
                    case TYPE_WEATHER:
                        if(temp.equals("x")){
                            convertView = mInflater.inflate(R.layout.location_based_cards_item, null);
                            holder.textView = (TextView)convertView.findViewById(R.id.settings_location_card);
                            holder.textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent gps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//GPS settings
                                    startActivity(gps);
                                }
                            });
                        }
                        else if(temp.equals("y")) {
                            convertView = mInflater.inflate(R.layout.no_network, null);
                            holder.textView = (TextView)convertView.findViewById(R.id.day);
                            holder.textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder network_alert = new AlertDialog.Builder(MultipleItemList.this);
                                    network_alert.setPositiveButton("Turn on wifi", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                            wifiManager.setWifiEnabled(true);
                                        }
                                    });
                                    network_alert.setNegativeButton("Turn on mobile data", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {

                                                ConnectivityManager conman = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                                                Class conmanClass = null;
                                                try {
                                                    conmanClass = Class.forName(conman.getClass().getName());
                                                } catch (ClassNotFoundException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(getApplicationContext(), "FAILED, you have to do it manually", Toast.LENGTH_LONG).show();

                                                }
                                                Field connectivityManagerField = null;
                                                try {
                                                    connectivityManagerField = conmanClass.getDeclaredField("mService");
                                                } catch (NoSuchFieldException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(getApplicationContext(), "FAILED, you have to do it manually", Toast.LENGTH_LONG).show();

                                                }
                                                connectivityManagerField.setAccessible(true);
                                                Object connectivityManager = null;
                                                try {
                                                    connectivityManager = connectivityManagerField.get(conman);
                                                } catch (IllegalAccessException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(getApplicationContext(), "FAILED, you have to do it manually", Toast.LENGTH_LONG).show();

                                                }
                                                Class connectivityManagerClass = null;
                                                try {
                                                    connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
                                                } catch (ClassNotFoundException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(getApplicationContext(), "FAILED, you have to do it manually", Toast.LENGTH_LONG).show();

                                                }
                                                Method setMobileDataEnabledMethod = null;
                                                try {
                                                    setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
                                                } catch (NoSuchMethodException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(getApplicationContext(), "FAILED, you have to do it manually", Toast.LENGTH_LONG).show();

                                                }
                                                setMobileDataEnabledMethod.setAccessible(true);

                                                try {
                                                    setMobileDataEnabledMethod.invoke(connectivityManager, true);
                                                } catch (IllegalAccessException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(getApplicationContext(), "FAILED, you have to do it manually", Toast.LENGTH_LONG).show();

                                                } catch (InvocationTargetException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(getApplicationContext(), "FAILED, you have to do it manually", Toast.LENGTH_LONG).show();

                                                }
                                            } catch (NullPointerException e) {
                                                e.printStackTrace();
                                                Toast.makeText(getApplicationContext(), "FAILED, you have to do it manually", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                    network_alert.show();
                                }
                            });
                        }
                            else {
                            convertView = mInflater.inflate(R.layout.weather_item, null);
                            holder.textView = (TextView) convertView.findViewById(R.id.city);
                            holder.txtTemp = (TextView) convertView.findViewById(R.id.info_one);
                            holder.imageView = (ImageView) convertView.findViewById(R.id.info_image);
                            holder.txthumidity = (TextView) convertView.findViewById(R.id.humidity);
                            holder.txtWind = (TextView) convertView.findViewById(R.id.wind_speed);
                            holder.txtCond = (TextView) convertView.findViewById(R.id.info_two);
                            holder.txtCond.setText(condition);
                            holder.txtWind.setText(windspeed);
                            holder.txthumidity.setText(humidity);
                            holder.textView.setText(weatherfor);
                            holder.txtTemp.setText(String.valueOf(temp) + "°");
                            holder.imageView.setImageResource(image);
                        }
                        break;
                    case TYPE_TIME:
                        convertView = mInflater.inflate(R.layout.time_card_item, null);
                        holder.textView = (TextView)convertView.findViewById(R.id.two);
                        holder.textView.setText(time);
                        break;
                    case TYPE_FAV_APPS:
                        favorite_apps info = new favorite_apps(MultipleItemList.this);
                        info.open();
                        String data = info.getData();
                        info.close();

                        convertView = mInflater.inflate(R.layout.favorite_apps_item,null);
                        holder.fav1 = (Button)convertView.findViewById(R.id.three);
                        holder.fav2 = (Button)convertView.findViewById(R.id.three_1);
                        holder.fav3 = (Button)convertView.findViewById(R.id.three_2);
                        holder.fav4 = (Button)convertView.findViewById(R.id.three_3);
                        holder.fav5 = (Button)convertView.findViewById(R.id.three_4);
                        holder.fav6 = (Button)convertView.findViewById(R.id.four);
                        holder.fav7 = (Button)convertView.findViewById(R.id.four_1);
                        holder.fav8 = (Button)convertView.findViewById(R.id.four_2);
                        holder.fav9 = (Button)convertView.findViewById(R.id.four_3);
                        holder.fav10 = (Button)convertView.findViewById(R.id.four_4);
                        try {
                            holder.fav1.setBackgroundDrawable(apps_icon.get(0));
                            holder.fav1.setOnClickListener(onClickListeners.get(0));

                            holder.fav2.setBackgroundDrawable(apps_icon.get(1));
                            holder.fav2.setOnClickListener(onClickListeners.get(1));

                            holder.fav3.setBackgroundDrawable(apps_icon.get(2));
                            holder.fav3.setOnClickListener(onClickListeners.get(2));

                            holder.fav4.setBackgroundDrawable(apps_icon.get(3));
                            holder.fav4.setOnClickListener(onClickListeners.get(3));

                            holder.fav5.setBackgroundDrawable(apps_icon.get(4));
                            holder.fav5.setOnClickListener(onClickListeners.get(4));

                            holder.fav6.setBackgroundDrawable(apps_icon.get(5));
                            holder.fav6.setOnClickListener(onClickListeners.get(5));

                            holder.fav7.setBackgroundDrawable(apps_icon.get(6));
                            holder.fav7.setOnClickListener(onClickListeners.get(6));

                            holder.fav8.setBackgroundDrawable(apps_icon.get(7));
                            holder.fav8.setOnClickListener(onClickListeners.get(7));

                            holder.fav9.setBackgroundDrawable(apps_icon.get(8));
                            holder.fav9.setOnClickListener(onClickListeners.get(8));

                            holder.fav10.setBackgroundDrawable(apps_icon.get(9));
                            holder.fav10.setOnClickListener(onClickListeners.get(9));


                        }catch (IndexOutOfBoundsException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Assist Me is still learning",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case TYPE_SETTINGS:
                        convertView = mInflater.inflate(R.layout.list_bottom_settings,null);
                        holder.fav1 = (Button)convertView.findViewById(R.id.settings);
                        holder.fav1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getApplicationContext(),weather_preferences.class));
                                finish();
                            }
                        });
                        break;
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            return convertView;
        }

    }

    public static class ViewHolder {
        public TextView textView;
        public ImageView image;
        public TextView txtTemp;
        public ImageView imageView;
        public TextView txthumidity;
        public TextView txtWind;
        public TextView txtCond;

        public Button fav1;
        public Button fav2;
        public Button fav3;
        public Button fav4;
        public Button fav5;
        public Button fav6;
        public Button fav7;
        public Button fav8;
        public Button fav9;
        public Button fav10;

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}