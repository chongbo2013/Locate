package com.example.locate.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;


/**
 * We should receive the event when package change
 */
public class AppChangedReceiver extends BroadcastReceiver
{

    public static final boolean DEBUG = true;
    public static final String TAG = "AppChangedReceiver";

    @Override
    public void onReceive(
            Context context ,
            Intent intent )
    {
        if( DEBUG )
        {
            Log.d( TAG , "AppChangedReceiver onReceiver" );
            String action = intent.getAction();
            Log.d( TAG , "action======" + action );
            String data = intent.getDataString();
            Log.d( TAG , "data======" + data );
        }
        // do nothing When receive data is about this package
        if( !intent.getDataString().split( ":" )[1].equals( context.getPackageName() ) )
        {
            // kill the current process so that the search service will restart
            // thus our data in memory is the latest
            Process.killProcess( Process.myPid() );
        }
    }
}
