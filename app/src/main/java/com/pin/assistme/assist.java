package com.pin.assistme;

/**
 * Created by tomer on 05/07/14.
 */
/*
public class assist extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    String cityName;
    SharedPreferences sharedPrefs;
    ImageView time_status;
    Button but,but2,but3,but4,but5,but6,but7,but8,but9,but10;
    RelativeLayout inner_weather;
    String forecastDaysNum = "3";
    LocationListener[] locationListener = {null};
    TextView weather_tv;
    String WeatherLocation;
    LocationManager locationMangaer=null;
    int weather_check = 0;
    ImageView imgView;
    Button button5,button4,button3,button2,button6;
    Button button;
    RelativeLayout button1;
    ImageView progressDialog;
    boolean WeatherForYourLocation = true;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ViewGroup group,group2,group3,group4;
    Timer myTimer;
    TextView temp,cond,city,humidity,wind_speed;
    String weatherfor = "location";
    boolean looking_for_weather = false;
    String weatherforcity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        onCreate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        oncreate_without_content_view();
    }

    void onCreate(){
        setContentView(R.layout.assist);
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        init();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        HandlePreferences();
        init_time(button2);
       // init_weather();
        remove_unused_cards();
        // create(button1,group);
        create(button2, group);
        create(button3,group2);
        create(button4,group);
        create(button5, group);
        create(but,group3);
        create(but2,group3);
        create(but3,group3);
        create(but4,group3);
        create(but5,group3);
        create(but6,group4);
        create(but7,group4);
        create(but8,group4);
        create(but9,group4);
        create(but10,group4);
        createTimer();
        try {
            init_short_apps();
        }
        catch (RuntimeException e){
            e.printStackTrace();
        }
    }
    void init_gps(){
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
    void oncreate_without_content_view(){
        onCreate();
        mSwipeRefreshLayout.setRefreshing(true);
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        finally {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    void init_time(Button time) {
        Calendar c = Calendar.getInstance();
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(c.get(Calendar.MINUTE));
        if (hour.length() < 2) {
            hour = "0" + hour;
        }
        if (minute.length() < 2) {
            minute = "0" + minute;
        }
        if (int_group(time.getId())) {
            time.setText(hour + ":" + minute);
        }
        if (Integer.valueOf(hour) > 5 && Integer.valueOf(hour) < 10) {
            time_status.setBackgroundResource(R.drawable.context_header_bg_dusk);
        } else if (Integer.valueOf(hour) >=10 && Integer.valueOf(hour) < 19) {
            time_status.setBackgroundResource(R.drawable.context_header_bg_daylight);
        }
        else{
            time_status.setBackgroundResource(R.drawable.context_header_bg_twilight);
        }
    }
    void remove_unused_cards(){
        if(button6.getText().toString().equals("")){
            group.removeView(button6);
        }
        if(!WeatherForYourLocation){
            group.removeView(button1);
        }
        try{
            if(button1.getChildAt(0) == null){
                group.removeView(button1);
            }
            if(group3.getChildAt(0)==null){
                group2.removeView(group3);
            }
            if(group2.getChildAt(0)==null){
                group.removeView(group2);
            }
            if(group4.getChildAt(0)==null){
                group2.removeView(group4);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            if (button2.getText().equals("")) {
                group.removeView(button2);
            }
            if (button3.getText().equals("")) {
                group.removeView(button3);
            }
            if (button4.getText().equals("")) {
                group.removeView(button4);
            }
            if (button5.getText().equals("")) {
                group.removeView(button5);
            }
            if(group2.getChildAt(1).getVisibility() != View.VISIBLE){
                group.removeView(group2);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    boolean int_group(int id){
        boolean toreturn = false;
        for(int i = 0;i<group.getChildCount();i++){
            if(group.getChildAt(i).getId()==id){
                toreturn = true;
            }
        }
        return toreturn;
    }
    void create(final View dismissableButton,final ViewGroup group){
        dismissableButton.setOnTouchListener(new SwipeDismissTouchListener(
                dismissableButton,
                null,
                new SwipeDismissTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(Object token) {
                        return true;
                    }
                    @Override
                    public void onDismiss(View view, Object token) {
                        group.removeView(dismissableButton);
                    }
                }));
    }
    void createRel(final ViewGroup dismissableButton, final ViewGroup group) {
        dismissableButton.setOnTouchListener(new SwipeDismissLayoutTouchListener(
                dismissableButton,
                null,
                new SwipeDismissLayoutTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(Object token) {
                        return true;
                    }

                    @Override
                    public void onDismiss(View view, Object token) {
                        group.removeView(dismissableButton);
                    }
                }
        ));
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    void init_weather(){
        if(WeatherForYourLocation) {
            if (!looking_for_weather) {
                try {
                    ContentResolver contentResolver = getBaseContext()
                            .getContentResolver();
                    boolean gpsStatus = Settings.Secure
                            .isLocationProviderEnabled(contentResolver,
                                    LocationManager.GPS_PROVIDER);

                    if (gpsStatus) {
                        if (isNetworkAvailable()) {
                            init_gps();
                        } else {
                            boolean isthere = false;
                            for (int i = 0; i < group.getChildCount(); i++) {
                                if (group.getChildAt(i).getId() == R.id.home) {
                                    isthere = true;
                                }
                            }
                            if (!isthere) {
                                group.removeView(button1);
                                button = new Button(getApplicationContext());
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder network_alert = new AlertDialog.Builder(assist.this);
                                        network_alert.setPositiveButton("Turn on wifi", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                                wifiManager.setWifiEnabled(true);
                                                oncreate_without_content_view();
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
                                                    oncreate_without_content_view();
                                                } catch (NullPointerException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(getApplicationContext(), "FAILED, you have to do it manually", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                        network_alert.show();
                                    }
                                });
                                button.setId(R.id.home);
                                button.setText("Get internet based cards");
                                button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
                                button.setBackgroundResource(R.drawable.now_cards);
                                button.setGravity(Gravity.CENTER);
                                button.setTextColor(Color.parseColor("#707070"));
                                group.addView(button, group.getChildCount() - 1);
                            }
                        }

                    } else {
                        if (!isNetworkAvailable()) {
                            boolean isthere = false;
                            for (int i = 0; i < group.getChildCount(); i++) {
                                if (group.getChildAt(i).getId() == R.id.home) {
                                    isthere = true;
                                }
                            }
                            if (!isthere) {
                                group.removeView(button1);
                                button = new Button(getApplicationContext());
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder network_alert = new AlertDialog.Builder(assist.this);
                                        network_alert.setPositiveButton("Turn on wifi", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                                wifiManager.setWifiEnabled(true);
                                                oncreate_without_content_view();
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
                                button.setId(R.id.home);
                                button.setText("Get internet based cards");
                                button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
                                button.setBackgroundResource(R.drawable.now_cards);
                                button.setGravity(Gravity.CENTER);
                                button.setTextColor(Color.parseColor("#707070"));
                                group.addView(button, group.getChildCount() - 1);
                            }

                        }
                        boolean isthere = false;
                        for (int i = 0; i < group.getChildCount(); i++) {
                            if (group.getChildAt(i).getId() == R.id.work) {
                                isthere = true;
                            }
                        }
                        if (!isthere) {
                            group.removeView(button1);
                            button = new Button(getApplicationContext());
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent gps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//GPS settings
                                    startActivity(gps);
                                }
                            });
                            button.setId(R.id.work);
                            button.setText("Get location based cards");
                            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
                            button.setBackgroundResource(R.drawable.now_cards);
                            button.setGravity(Gravity.CENTER);
                            button.setTextColor(Color.parseColor("#707070"));
                            group.addView(button, group.getChildCount() - 1);

                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Log.d("Error", "Cannot load weather for location");
                }
            }
        }
    }
    void init(){

        weather_tv = (TextView)findViewById(R.id.weather_for_multiple_locations);
        Button settings = (Button)findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),cards_preferences.class));
            }
        });
        but = (Button)findViewById(R.id.three);
        group3 = (ViewGroup)findViewById(R.id.group3);
        group4 = (ViewGroup)findViewById(R.id.group4);
        inner_weather = (RelativeLayout)findViewById(R.id.weather_inner);
        but6 = (Button)findViewById(R.id.four);
        but7 = (Button)findViewById(R.id.four_1);
        but8 = (Button)findViewById(R.id.four_2);
        but9 = (Button)findViewById(R.id.four_3);
        but10 = (Button)findViewById(R.id.four_4);
        but4 = (Button)findViewById(R.id.three_3);
        but2 = (Button)findViewById(R.id.three_1);
        but3 = (Button)findViewById(R.id.three_2);
        group2 = (ViewGroup)findViewById(R.id.apps_short_back);
        group = (ViewGroup) findViewById(R.id.backcard);
        button1 = (RelativeLayout) findViewById(R.id.one);
        button2 = (Button)findViewById(R.id.two);
        button3 = (Button)findViewById(R.id.three);
        button4 = (Button)findViewById(R.id.four);
        button5 = (Button)findViewById(R.id.five);
        button6 = (Button)findViewById(R.id.six);
        imgView = (ImageView)findViewById(R.id.info_image);
        temp = (TextView)findViewById(R.id.info_one);
        time_status = (ImageView)findViewById(R.id.time_status);
        cond = (TextView)findViewById(R.id.info_two);
        humidity = (TextView)findViewById(R.id.humidity);
        city = (TextView)findViewById(R.id.city);
        wind_speed = (TextView)findViewById(R.id.wind_speed);
        progressDialog = new ImageView(getApplicationContext());
        progressDialog.setBackgroundResource(R.drawable.pb);
        AnimationDrawable frameAnimation = (AnimationDrawable) progressDialog.getBackground();
        frameAnimation.start();
        progressDialog.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //progressDialog.setLayoutParams(new ViewGroup.MarginLayoutParams(100,100));
        button1.setGravity(Gravity.CENTER_HORIZONTAL);
        button1.addView(progressDialog);
        inner_weather.setVisibility(View.INVISIBLE);
        but5 = (Button)findViewById(R.id.three_4);
        Button exit_1 = (Button)findViewById(R.id.exit1);
        exit_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.startAnimation(AnimationUtils.loadAnimation(assist.this,R.anim.right_to_left_fade_out));
                android.os.SystemClock.sleep(700);
                group.removeView(button1);
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(R.color.lime, R.color.red, R.color.app_def, R.color.yellow);
        create(weather_tv,group);
        create(button1,group);
    }
    void init_short_apps(){
        favorite_apps info = new favorite_apps(assist.this);
        info.open();
        String data = info.getData();
        info.close();
        set_app_to_button(but,1);

        set_app_to_button(but2,2);

        set_app_to_button(but3,3);

        set_app_to_button(but4,4);

        set_app_to_button(but5,5);

        set_app_to_button(but6,6);

        set_app_to_button(but7,7);

        set_app_to_button(but8,8);

        set_app_to_button(but9,9);

        set_app_to_button(but10,10);


   /*     app_3 = lines[lines.length-3];
        app_3 = app_3.substring(app_3.lastIndexOf("|| ")+3);
        if(app_3 == null){
            but3.setText("Still learning");
        }
        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                but3.setBackgroundDrawable(getPackageManager().getApplicationIcon(app_3));
            } else {
                but3.setBackground(getPackageManager().getApplicationIcon(app_3));
            }
            but3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(app_3);
                    startActivity( LaunchIntent );
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
*/
/*
    }
    void set_app_to_button(Button button,int place_in_list){
        try {
            favorite_apps info = new favorite_apps(assist.this);
            info.open();
            String data = info.getData();
            info.close();
            String[] lines = data.split(System.getProperty("line.separator"));
            String appName_temp = lines[lines.length - place_in_list];
            final String appName = appName_temp.substring(appName_temp.lastIndexOf("|| ") + 3);
            if (appName == null) {
                button.setText("Still learning");
            }
            try {
                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    button.setBackgroundDrawable(getPackageManager().getApplicationIcon(appName));
                } else {
                    button.setBackground(getPackageManager().getApplicationIcon(appName));
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(appName);
                        try {
                            startActivity(LaunchIntent);
                        }
                        catch (NullPointerException e){
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
    void HandlePreferences(){
        if(sharedPrefs.contains("prefWeatherForLocation")){
            WeatherForYourLocation = sharedPrefs.getBoolean("prefWeatherForLocation",true);
        }
        if(sharedPrefs.contains("prefWeatherFor")){
            if(sharedPrefs.getString("prefWeatherFor","").equals("")||sharedPrefs.getString("prefWeatherFor","").equals(null)){
                group.removeView(findViewById(R.id.weather_for_multiple_locations));
                group.removeView(weather_tv);
                weather_tv.setVisibility(View.INVISIBLE);
            }
            else{
                weatherfor = "custom";
                String city = WeatherLocation;
                String lang = "en";
                String[] lines = sharedPrefs.getString("prefWeatherFor", "").split(System.getProperty("line.separator"));
                for (weather_check = 0; weather_check < lines.length; weather_check++) {
                WeatherLocation = lines[lines.length -weather_check-1];
                JSONWeatherTask_cards task = new JSONWeatherTask_cards();
                task.execute(new String[]{WeatherLocation, lang});
                JSONForecastWeatherTask_cards task1 = new JSONForecastWeatherTask_cards();
                task1.execute(new String[]{WeatherLocation, lang, forecastDaysNum});
                }
            }

        }
    }
    //timer
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

        }, 0, 10000);
    }
    void todo_on_tick(){
        init_time(button2);
       // init_weather();
        remove_unused_cards();
        init_short_apps();
        init_weather();

        String[] lines2 = weather_tv.getText().toString().split(System.getProperty("line.separator"));
        String[] lines = sharedPrefs.getString("prefWeatherFor", "").split(System.getProperty("line.separator"));
        Log.d("LINES",lines.length+","+lines2.length);
        if(lines2.length-1 >= lines.length){
            weatherfor = "location";
            looking_for_weather = false;
            if(weather_tv.getText().toString().equals("")||weather_tv.getText().toString().equals(null)){
                group.removeView(weather_tv);
                weather_tv.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cards_layout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                mSwipeRefreshLayout.setRefreshing(true);
                onCreate();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
        oncreate_without_content_view();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 9000);
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            if (!looking_for_weather) {
                looking_for_weather = true;
                button1.setVisibility(View.VISIBLE);

                String longitude = "Longitude: " + loc.getLongitude();
                String latitude = "Latitude: " + loc.getLatitude();

        /*------- To get city name from coordinates -------- */
/*
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
                        JSONWeatherTask_cards task = new JSONWeatherTask_cards();
                        task.execute(new String[]{cityName, "en"});

                        JSONForecastWeatherTask_cards task1 = new JSONForecastWeatherTask_cards();
                        task1.execute(new String[]{cityName, "en", forecastDaysNum});
                        city.setText(cityName);

                    } else {
                        Toast.makeText(getApplicationContext(), "Error finding location", 100).show();
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
            if (weatherfor.equals("location")) {
                try {
                    progressDialog.setVisibility(View.INVISIBLE);
                    if (int_group(button.getId())) {
//                group.addView(button1);
                        for (int i = 0; i < group.getChildCount(); i++) {
                            if (group.getChildAt(i).getId() == button.getId()) {
                                try {
                                    group.removeViewAt(i);
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                    Log.d("Error","Cannot load weather for location");
                                }
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Log.d("Error","Cannot load weather for location");
                }
                progressDialog.setVisibility(View.INVISIBLE);
                button1.setPadding(0, 0, 0, 0);
                inner_weather.setVisibility(View.VISIBLE);
                temp.setTextColor((Color.parseColor("#707070")));
                looking_for_weather = false;
                String tmp = ("" + Math.round((weather.temperature.getTemp() - 275.15)));
                String condition = (weather.currentCondition.getCondition() + " (" + weather.currentCondition.getDescr() + ")");
                String windSpeed = ("" + weather.wind.getSpeed() + " mps");
                String Humidity = ("" + weather.currentCondition.getHumidity() + "%");
                humidity.setText(Humidity);
                wind_speed.setText(windSpeed);
                if (condition.equals(null) || tmp.equals(-275)) {
                    cond.setText("Can't find location");
                } else {
                    cond.setText(condition);
                    if (!sharedPrefs.getString("prefUnit", "").equals("Fahrenheit")) {
                        temp.setText((tmp) + "°");
                    } else {
                        temp.setText(String.valueOf(Math.round(Integer.valueOf(tmp) * 1.8 + 32)) + "°");
                    }
                    if (condition.contains("rain")) {
                        imgView.setImageResource(R.drawable.rain);
                    } else if (condition.contains("light")) {
                        imgView.setImageResource(R.drawable.lightning);
                    } else if (condition.contains("cloud")) {
                        imgView.setImageResource(R.drawable.cloudy);
                    } else if (condition.contains("wind")) {
                        imgView.setImageResource(R.drawable.wind);
                    } else {
                        imgView.setImageResource(R.drawable.sun);
                    }


			/*

			temp.setText("" + Math.round((weather.temperature.getTemp() - 275.15)) + "�C");
			hum.setText("" + weather.currentCondition.getHumidity() + "%");
			press.setText("" + weather.currentCondition.getPressure() + " hPa");
			windSpeed.setText("" + weather.wind.getSpeed() + " mps");
			windDeg.setText("" + weather.wind.getDeg() + "�");
			*/
/*
                }

            }
            else {
                try {
                    weatherforcity = ("" + Math.round((weather.temperature.getTemp() - 275.15)));
                    weather_tv.setText(weather_tv.getText().toString() + "Weather in " + " azazazaz " + weatherforcity + "\n");
                    String[] lines = sharedPrefs.getString("prefWeatherFor", "").split(System.getProperty("line.separator"));
                    String[] lines2 = weather_tv.getText().toString().split(System.getProperty("line.separator"));
                    for (int weather_check = 0; weather_check < lines2.length; weather_check++) {
                        if (lines2[weather_check].contains("azazazaz")) {
                            weather_tv.setText(weather_tv.getText().toString().replace("azazazaz", lines[lines.length - weather_check - 1]).toUpperCase());
                        }
                    }

                }catch (ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
                String[] lines = sharedPrefs.getString("prefWeatherFor", "").split(System.getProperty("line.separator"));
                String[] lines2 = weather_tv.getText().toString().split(System.getProperty("line.separator"));
                if(lines2.length >= lines.length){
                    weatherfor = "location";
                    looking_for_weather = false;
                    if(weather_tv.getText().toString().equals("")||weather_tv.getText().toString().equals(null)){
                        group.removeView(weather_tv);
                        weather_tv.setVisibility(View.INVISIBLE);
                    }
                }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(getApplicationContext(), Main1.class));
            finish();
        }
        return true;
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
*/