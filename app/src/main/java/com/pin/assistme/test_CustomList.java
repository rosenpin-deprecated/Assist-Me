package com.pin.assistme;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assist.me.R;

import java.util.List;

/**
 * Created by tomer on 17/07/14.
 */
public class test_CustomList  extends ArrayAdapter<String> {
    private LayoutInflater mInflater;



    private Activity context = null;
    private List<String> city = null;
    private List<Integer> image = null;
    private List<String> temp = null;
    private List<String> humidity = null;
    private List<String> windspeed = null;
    private List<String> condition = null;
    String time;
    int timecards;//i



    public test_CustomList(Activity context,
                      List<String> city,
                      List<Integer> image,
                      List<String> temp,
                      List<String>humidity,
                      List<String>windspeed,
                      List<String>condition,
                      int timecards
                      ) {
        super(context, R.layout.weather_item, city);
        this.context = context;
        this.city = city;
        this.temp = temp;
        this.humidity = humidity;
        this.image = image;
        this.windspeed = windspeed;
        this.condition = condition;
        this.timecards = timecards;
    }

    public test_CustomList(Activity context,
                           String time){
        super(context, R.layout.time_card_item);
        this.context = context;
        this.time = time;
    }



    private static final int DATA_TYPE_WEATHER = 1;
    private static final int DATA_TYPE_OTHER = 0;

    @Override
    public int getViewTypeCount() {
        // Return the total number of xml layouts you'll be using
        return 2;
    }
    @Override
    public int getItemViewType(int position) {
        // You have the list position, look up the data in your array and
        // return the proper number for the data type
        if (position == 1) {
            return DATA_TYPE_WEATHER;
        } else {
            return DATA_TYPE_OTHER;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // always check and re-use the convertView if it's not null!
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            if (position == 1) {
                convertView = inflater.inflate(R.layout.weather_item, null);
            } else {
                convertView = inflater.inflate(R.layout.time_card_item, null);
            }
        }
        if (position == 1) {
            TextView txtTitle = (TextView) convertView.findViewById(R.id.city);
            TextView txtTemp = (TextView) convertView.findViewById(R.id.info_one);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.info_image);
            TextView txthumidity = (TextView) convertView.findViewById(R.id.humidity);
            TextView txtWind = (TextView) convertView.findViewById(R.id.wind_speed);
            TextView txtCond = (TextView) convertView.findViewById(R.id.info_two);
            txtCond.setText(condition.get(position));
            txtWind.setText(windspeed.get(position));
            txthumidity.setText(humidity.get(position));
            txtTitle.setText(city.get(position));
            txtTemp.setText(String.valueOf(temp.get(position)) + "°");
            imageView.setImageResource(image.get(position));
        } else {
            TextView txtTitle1 = (TextView) convertView.findViewById(R.id.two);
            txtTitle1.setText(time);
        }
        return convertView;
    }
   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // always check and re-use the convertView if it's not null!
        ViewHolder holder = null;
        LayoutInflater inflater = context.getLayoutInflater();
        int type = getItemViewType(position);
        System.out.println("getView " + position + " " + convertView + " type = " + type);
        if (convertView == null) {
            holder = new ViewHolder();
            if(time==null) {
                convertView = inflater.inflate(R.layout.weather_item, null);
                TextView txtTitle = (TextView) convertView.findViewById(R.id.city);
                TextView txtTemp = (TextView) convertView.findViewById(R.id.info_one);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.info_image);
                TextView txthumidity = (TextView) convertView.findViewById(R.id.humidity);
                TextView txtWind = (TextView) convertView.findViewById(R.id.wind_speed);
                TextView txtCond = (TextView) convertView.findViewById(R.id.info_two);
                txtCond.setText(condition.get(position));
                txtWind.setText(windspeed.get(position));
                txthumidity.setText(humidity.get(position));
                txtTitle.setText(city.get(position));
                txtTemp.setText(String.valueOf(temp.get(position)) + "°");
                imageView.setImageResource(image.get(position));
            }
            else{
                 convertView = inflater.inflate(R.layout.time_card_item, null);
                TextView txtTitle1 = (TextView) convertView.findViewById(R.id.two);
                 txtTitle1.setText(time);
            }
                convertView.setTag(holder);

        }
        return convertView;
    }*/

    static class ViewHolder {
    }

}
