package com.pin.assistme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.assist.me.R;


/**
 * Created by tomers-pc on 3/13/14.
 */
public class about extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        TextView about = (TextView)findViewById(R.id.abouttt);
        about.setText("Assist-Me is your intelligent virtual assistant, take it anywhere you go and let here assist you.\n" +
                "Assist-Me, not like other voice assistant will learn your usage of your phone and will assist you better based on that information.\n" +
                "Assist-Me packs a lot of exclusives features you will only find in it, such as smart cards, customizable colors all over, ' Bitch mode ', Siri look alike and many more!\n" +
                "With a beautiful minimalist super customizable design it's safe to say Assist-Me is one of the best voice assistant you could find for android!\n" +
                "With Assist-Me you can:\n" +
                "\n" +
                "->> Make phone calls\n" +
                "->> Get directions to places\n" +
                "->> Open applications by voice\n" +
                "->> Set timers\n" +
                "->> Toggle WIFI Bluetooth and more using your voice\n" +
                "->> Send messages\n" +
                "->> Play music\n" +
                "->> Get battery information just by asking for it\n" +
                "->> Ask her questions such as \"What's the weather?\"\n" +
                "->> Ask for general knowledge using WolframAlpha API, it knows everything!\n" +
                "->> Search the web, youtube , google , images etc.\n" +
                "->> Exclusive cool questions such as: \"What's my favorite app?\"\n" +
                "->> And much more!\n" +
                "\n" +
                "Assist Me is only starting it's way and it has a long way to go.\n" +
                "Stay tuned for future updates and upcoming many new features!\n" +
                "\n" +
                "By installing Assist-Me you accept it's user agreement\n" +
                "\n" +
                "Languages support:\n" +
                "Assist-Me currently support\n" +
                "--> English - STABLE\n" +
                "--> Hebrew - BETA\n" +
                "--> Russian - UNDER DEVELOPMENT");
    }
}
