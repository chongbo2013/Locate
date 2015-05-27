package com.example.locate.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.locate.service.SearchService;


/**
 * Start service after device is booted
 */
public class BootCompleteReceiver extends BroadcastReceiver
{
	
	public static final boolean DEBUG = false;
	
	@Override
	public void onReceive(
			Context context ,
			Intent intent )
	{
		if( DEBUG )
		{
			String action = intent.getAction();
			Toast.makeText( context , "BootCompleteReceiver onReceive action====" + action , Toast.LENGTH_SHORT ).show();
		}
		context.startService( new Intent( context , SearchService.class ) );
	}
}
