package com.pin.assistme;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by tomer on 16/05/14.
 */
public class DeviceAdministator extends DeviceAdminReceiver {

        void showToast(Context context, String msg) {
        }

        @Override
        public void onEnabled(Context context, Intent intent) {
            showToast(context, "YOLO");
        }

        @Override
        public CharSequence onDisableRequested(Context context, Intent intent) {
            return null;
        }

        @Override
        public void onDisabled(Context context, Intent intent) {
        }

        @Override
        public void onPasswordChanged(Context context, Intent intent) {
        }
    }
