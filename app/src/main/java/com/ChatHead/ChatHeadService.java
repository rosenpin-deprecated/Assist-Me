package com.ChatHead;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.assist.me.R;

/**
 * Created by tomer on 20/08/14.
 */
public class ChatHeadService extends Service {

    private WindowManager windowManager;
    private ImageView chatHead;

    @Override public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);
        chatHead.setImageResource(R.drawable.ic_launcher);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                160,
                160,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        windowManager.addView(chatHead, params);

        chatHead.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            public boolean onTouch(View v, MotionEvent event) {
                Log.d("test", "ontouch");

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return false;
                    case MotionEvent.ACTION_UP:
                        if (params.x >= windowManager.getDefaultDisplay().getWidth()/2 ){
                            params.x = windowManager.getDefaultDisplay().getWidth()-params.width;
                        }
                        if (params.x <= windowManager.getDefaultDisplay().getWidth()/2 ){
                            params.x = 0;
                        }
                        windowManager.updateViewLayout(chatHead, params);

                        return false;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(chatHead, params);
                        return false;
                }
                return false;
            }
        });
        chatHead.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("test", "onclick");

                params.gravity = Gravity.TOP | Gravity.LEFT;
                params.x = 0;
                params.y = 0;
                windowManager.updateViewLayout(chatHead, params);

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null) windowManager.removeView(chatHead);
    }
}