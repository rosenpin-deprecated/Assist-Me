package com.pin.assistme;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.RippleButton.FloatingActionButton;
import com.RippleButton.ObservableScrollView;
import com.assist.me.R;

/**
 * Created by tomer on 18/07/14.
 */
public class features extends Activity {
    LinearLayout back;
    int animation = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.features);
        init();
        String titles[] ={"Make phone calls",
                "Get directions",
                "Open applications",
                "Set timers",
                "Fast toggles",
                "Send messages",
                "Play music",
                "Get battery information",
                "Get useful information",
                "Do google searches",
                "Set alarms",
                "Do youtube searches",
                "Ask for cool stuff",
                "Ask for fun stuff",
                "Go to homescreen",
              //  "Add events to calendar"
        };
        String descriptions[]={
                "'Call + Contact name' to make phone calls",
                "'Navigate to + Place' to start navigation to places",
                "'Open + name of the app' to open the wanted application",
                "'Set a time for + time for timer' to start timer",
                "'Turn wifi on'\n'Turn bluetooth on'\n'Brighter'\netc",
                "'send a message to + contact name' to send a message",
                "'play + name of song' to play songs from your library",
                "'Battery' to get information about your battery",
                "'What's the weather' to get weather information\n 'What time is it' to get the current time",
                "'Google search + what you want to search' to do google searches",
                "'Alarm' to set alarm",
                "'youtube search + video you would like to search' to search videos on youtube",
                "'What is my favorite app?' Assist me will tell you what app do you use the most",
                "'Tell me a joke' Assist me will tell you a terrible joke",
                "'Go home' to go back to your home screen",
                //"'Remind me to + what you want to be reminded + when you want to be reminded"
        };

        for(int i = 0;i<titles.length;i++){
            if(titles[i]!=null&&descriptions[i]!=null) {
                addFeature(titles[i], descriptions[i]);
            }
        }
    }
    void init(){
        back = (LinearLayout)findViewById(R.id.features_back);
        FloatingActionButton flt = new FloatingActionButton.Builder(features.this)
                .withDrawable(getResources().getDrawable(android.R.drawable.ic_menu_revert))
                .withButtonColor(R.color.materialBlue)
                .withGravity(Gravity.BOTTOM|Gravity.RIGHT)
                .withMargins(0,0,0,68)
                .create();
        ObservableScrollView scrollView = (ObservableScrollView)findViewById(R.id.scroller);
        flt.listenToScrollView(scrollView);
        flt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    void addFeature(String title,String body){
        RelativeLayout big = new RelativeLayout(getApplicationContext());
        LinearLayout.LayoutParams bigLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        big.setMinimumHeight(400);
        bigLP.setMargins(0,50,0,0);
        big.setLayoutParams(bigLP);
        big.setBackgroundResource(R.drawable.now_cards);

        LinearLayout lin1 = new LinearLayout(getApplicationContext());
        lin1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView txtTitle = new TextView(getApplicationContext());
        txtTitle.setBackgroundColor(Color.parseColor("#FFF6F6F6"));
        txtTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTitle.setText(title);
        txtTitle.setTextColor(Color.parseColor("#FF747474"));
        txtTitle.setTextSize(25);
        txtTitle.setSingleLine();
        txtTitle.setPadding(45, 45, 45, 45);
        lin1.addView(txtTitle);

        LinearLayout lin2 = new LinearLayout(getApplicationContext());
        RelativeLayout.LayoutParams lin2LP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lin2LP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lin2LP.addRule(RelativeLayout.BELOW, lin1.getId());
        lin2.setLayoutParams(lin2LP);

        TextView txtBody = new TextView(getApplicationContext());
        txtBody.setMaxLines(3);
        RelativeLayout.LayoutParams lpBody = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtBody.setLayoutParams(lpBody);
        txtBody.setText("Say: " + body);
        txtBody.setTextColor(Color.parseColor("#FF747474"));
        txtBody.setGravity(Gravity.LEFT);
        txtBody.setTextSize(18);
        txtBody.setPadding(40,0,80,20);
        lin2.addView(txtBody);


        big.addView(lin1);
        big.addView(lin2);
        if(animation == 0) {
            big.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up_right));
            animation = 1;
        }
        else {
            big.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up_left));
            animation = 0;
        }
        back.addView(big);
        create(txtBody,lin2);
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
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
