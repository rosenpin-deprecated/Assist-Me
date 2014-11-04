package com.pin.assistme;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.assist.me.R;

        import org.w3c.dom.Text;

        import java.util.List;

public class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    private final List<String> city;
    private final List<Integer> image;
    private final List<String> temp;
    private final List<String> humidity;
    private final List<String> windspeed;
    private final List<String> condition;


    public CustomList(Activity context,
                      List<String> city,
                      List<Integer> image,
                      List<String> temp,
                      List<String>humidity,
                      List<String>windspeed,
                      List<String>condition) {
        super(context, R.layout.weather_item, city);
        this.context = context;
        this.city = city;
        this.temp = temp;
        this.humidity = humidity;
        this.image = image;
        this.windspeed = windspeed;
        this.condition = condition;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.weather_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.city);
        TextView txtTemp = (TextView) rowView.findViewById(R.id.info_one);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.info_image);
        TextView txthumidity = (TextView) rowView.findViewById(R.id.humidity);
        TextView txtWind = (TextView) rowView.findViewById(R.id.wind_speed);
        TextView txtCond = (TextView) rowView.findViewById(R.id.info_two);
        txtCond.setText(condition.get(position));
        txtWind.setText(windspeed.get(position));
        txthumidity.setText(humidity.get(position));
        txtTitle.setText(city.get(position));
        txtTemp.setText(String.valueOf(temp.get(position))+"°");
        imageView.setImageResource(image.get(position));
        return rowView;
    }
}