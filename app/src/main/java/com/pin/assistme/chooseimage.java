/*package com.pin.assistme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.assist.me.R;


/**
 * Created by tomers-pc on 14/03/14.
 */
/*
public class chooseimage extends Activity {
    ImageView dumb;
    ImageView linux;
    ImageView hanah;
    ImageView flying;
    ImageView unicorn;
    ImageView guy;
    ImageView momi;

    ImageView peter;
    String selectedimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseimage);
        linux = (ImageView)findViewById(R.id.linux);
        hanah = (ImageView)findViewById(R.id.hanah);
        flying = (ImageView)findViewById(R.id.flying);
        unicorn = (ImageView)findViewById(R.id.unicorn);
        guy = (ImageView)findViewById(R.id.guy);
        momi = (ImageView)findViewById(R.id.momi);
        peter = (ImageView) findViewById(R.id.peter);
        dumb = (ImageView)findViewById(R.id.dumb);
        linux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimage = "linux";
                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                prefEditor.putString("selectedimage", selectedimage);
                prefEditor.commit();
                finish();
            }
        });
        hanah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimage = "hanah";
                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                prefEditor.putString("selectedimage", selectedimage);
                prefEditor.commit();
                finish();
            }
        });
        flying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimage = "flying";
                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                prefEditor.putString("selectedimage", selectedimage);
                prefEditor.commit();
                finish();
            }
        });
        unicorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimage = "unicorn";
                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                prefEditor.putString("selectedimage", selectedimage);
                prefEditor.commit();
                finish();
            }
        });
        guy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimage = "guy";
                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                prefEditor.putString("selectedimage", selectedimage);
                prefEditor.commit();
                finish();
            }
        });
        momi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimage = "momi";
                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                prefEditor.putString("selectedimage", selectedimage);
                prefEditor.commit();
                finish();
            }
        });
        dumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimage = "R.drawable.dumb";
                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                prefEditor.putString("selectedimage", selectedimage);
                prefEditor.commit();
                finish();
            }
        });
        peter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimage = "R.drawable.peter";
                SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
                prefEditor.putString("selectedimage", selectedimage);
                prefEditor.commit();
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pick_from_gallery, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pickimagefromgallery:
                Intent settings = new Intent(this, settings.class);
                startActivity(settings);
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}*/