package com.example.locate.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * We should receive the event when package change
 */
public class AppChangedReceiver extends BroadcastReceiver {

    public static final String TAG = "AppChangedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "AppChangedReceiver onReceiver");
        String action = intent.getAction();
        Log.d(TAG, "action======" + action);
        String data = intent.getDataString();
        Log.d(TAG, "data======" + data);
    }
}
