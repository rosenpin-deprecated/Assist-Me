package com.pin.assistme;

/**
 * Created by tomer on 27/05/14.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;

import com.assist.me.R;

import java.util.List;

public class instructions extends FragmentActivity {

    int sdk = Build.VERSION.SDK_INT;
    String animation_selected = "normal";

    //Navdrawer
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    int language;

    List<DrawerItem> dataList;
    private ViewPager viewPager;
    private adapter mAdapter;
    // Tab titles
    void animations() {
   /*     if (animation_selected.equals("normal")) {
            overridePendingTransition(R.anim.bottom_to_top, R.anim.top_to_bottom);
        } else if (animation_selected.equals("left_to_right")) {
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        } else if(animation_selected.equals("fade")){
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        }
        else if (animation_selected.equals("normal_fade")){
            overridePendingTransition(R.anim.left_to_rigt_fade_in, R.anim.right_to_left_fade_out);
        }
        else{
            overridePendingTransition(R.anim.bottom_to_top_fade_in, R.anim.top_to_bottom_fade_out);
        }
        */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(sdk < Build.VERSION_CODES.KITKAT){
            setTheme(android.R.style.Theme_Holo_Light);
            setContentView(R.layout.instructoins);
        }
        else{
            setContentView(R.layout.instructoins);
        }
        SharedPreferences prefEditor =getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if (prefEditor.contains("animation")) {
            animation_selected = prefEditor.getString("animation", "");
        }
        animations();
        SharedPreferences lang = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new adapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        getActionBar().setTitle("Basics");

                        break;
                    case 1:
                        getActionBar().setTitle("Gestures");

                        ImageView img_animation = (ImageView) findViewById(R.id.hand);

                        TranslateAnimation animation = new TranslateAnimation(-findViewById(R.id.home).getWidth()/2, findViewById(R.id.home).getWidth()/2,
                                0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                        animation.setDuration(2000);  // animation duration
                        animation.setRepeatCount(Animation.INFINITE);  // animation repeat count
                        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
//      animation.setFillAfter(true);

                        img_animation.startAnimation(animation);

                        ImageView img_animation1 = (ImageView) findViewById(R.id.hand2);

                        TranslateAnimation animation1 = new TranslateAnimation(0.0f, 0.0f,
                                0.0f, 200.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                        animation1.setDuration(1100);  // animation duration
                        animation1.setRepeatCount(Animation.INFINITE);

                        img_animation1.startAnimation(animation1);
                        break;
                    case 2:
                        getActionBar().setTitle("Customization");


                        break;
                    case 3:
                        getActionBar().setTitle("Advanced");

                        break;
                }


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                if (viewPager.getCurrentItem() == 1) {
                    ImageView img_animation = (ImageView) findViewById(R.id.hand);

                    TranslateAnimation animation = new TranslateAnimation(-findViewById(R.id.home).getWidth()/2, findViewById(R.id.home).getWidth()/2,
                            0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                    animation.setDuration(2000);  // animation duration
                    animation.setRepeatCount(Animation.INFINITE);  // animation repeat count
                    animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
//      animation.setFillAfter(true);

                    img_animation.startAnimation(animation);

                    ImageView img_animation1 = (ImageView) findViewById(R.id.hand2);

                    TranslateAnimation animation1 = new TranslateAnimation(0.0f, 0.0f,
                            0.0f, 200.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                    animation1.setDuration(1100);  // animation duration
                    animation1.setRepeatCount(Animation.INFINITE);

                    img_animation1.startAnimation(animation1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (viewPager.getCurrentItem() == 1) {
                    ImageView img_animation = (ImageView) findViewById(R.id.hand);

                    TranslateAnimation animation = new TranslateAnimation(-findViewById(R.id.home).getWidth()/2, findViewById(R.id.home).getWidth()/2,
                            0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                    animation.setDuration(2000);  // animation duration
                    animation.setRepeatCount(Animation.INFINITE);  // animation repeat count
                    animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
//      animation.setFillAfter(true);

                    img_animation.startAnimation(animation);

                    ImageView img_animation1 = (ImageView) findViewById(R.id.hand2);

                    TranslateAnimation animation1 = new TranslateAnimation(0.0f, 0.0f,
                            0.0f, 200.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                    animation1.setDuration(1100);  // animation duration
                    animation1.setRepeatCount(Animation.INFINITE);

                    img_animation1.startAnimation(animation1);
                }
            }
        });





    }
    public void MoveNext(View view) {
        //it doesn't matter if you're already in the last item
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    public void MovePrevious(View view) {
        //it doesn't matter if you're already in the first item
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tutorial, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)     {
        switch (item.getItemId()) {
            case R.id.next:
                MoveNext(viewPager);
                break;
            case R.id.back:
                MovePrevious(viewPager);
                break;
        }
        return false;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}