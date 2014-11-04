package com.pin.assistme;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.assist.me.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomer on 29/05/14.
 */
public class music extends Activity {
    private String[] mAudioPath;
    private MediaPlayer mMediaPlayer;
    String com = "22";
    private String[] mMusicList;
    int i = 0;
    int MusicAmount;
    String songchecked;
    Boolean play;
    ImageView album_pic;
    String song_to_play;
    Button stop,previous;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            song_to_play = extras.getString("KEY");
        }
        init();
        play_command();
    }
    void play_command(){
        for(int i = 0; i < mMusicList.length; i++) {
            songchecked = mMusicList[i].toString().toLowerCase().replace("-", "").replace("(", "").replace(")", "");
            if (songchecked.toLowerCase().equals(song_to_play)) {
                try {

                    play = true;
                    playSong(mAudioPath[i]);
                    if(mMediaPlayer.isPlaying()){
                                stop.setBackgroundResource(R.drawable.stop);
                    }
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    byte[] rawArt;
                    Bitmap art;
                    BitmapFactory.Options bfo=new BitmapFactory.Options();

                    mmr.setDataSource(mAudioPath[i]);
                    rawArt = mmr.getEmbeddedPicture();
                    if (rawArt != null) {
                        art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
                        album_pic.setImageBitmap(art);
                    }
                    else{
                        album_pic.setImageResource(R.drawable.ic_launcher);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Couldn't find that song", 200).show();

                }
            }
            else{
                if(mMusicList.length-0==i){
                    finish();
                    Toast.makeText(getApplicationContext(), "Couldn't find that song", 200).show();
                }
            }
        }
        if(!mMediaPlayer.isPlaying()){
            Toast.makeText(getApplicationContext(),"Song not found",100).show();
            finish();
        }
    }
    void init(){
        album_pic = (ImageView)findViewById(R.id.song_pic);
        previous = (Button)findViewById(R.id.previous);
        mMediaPlayer = new MediaPlayer();
        ListView mListView = new ListView(this);
        stop = (Button)findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    play = false;
                    stop.setBackgroundResource(R.drawable.play_button);
                } else {
                    mMediaPlayer.start();
                    play = true;
                    stop.setBackgroundResource(R.drawable.stop);
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.stop();
                play_command();
            }
        });
        mMusicList = getAudioList();
        MusicAmount = mMusicList.length;
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mMusicList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                mMediaPlayer.stop();
            }
        });
    }
    private void playSong(String path) throws IllegalArgumentException,
            IllegalStateException, IOException {

        Log.d("ringtone", "playSong :: " + path);

        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(path);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.prepare();
        mMediaPlayer.start();

    }
    private String[] getAudioList() {
        final Cursor mCursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA}, null, null,
                "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

        int count = mCursor.getCount();

        String[] songs = new String[count];
        mAudioPath = new String[count];
        if (mCursor.moveToFirst()) {
            do {
                songs[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                mAudioPath[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                i++;
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return songs;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.pause();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        finish();
    }
}


