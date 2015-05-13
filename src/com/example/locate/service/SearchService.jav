package com.example.locate.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.locate.Locate;
import com.example.locate.content.Application;
import com.example.locate.content.Contact;
import com.example.locate.listener.HomeListen;
import com.example.locate.listener.HomeListen.OnHomeBtnPressLitener;
import com.example.locate.ui.MainActivity;


public class SearchService extends Service
{

	public static Context mContext;

	@Override
	public void onCreate()
	{
		super.onCreate();
		mContext = this;
		initHomeListen();
		long seconds_be = System.currentTimeMillis();
		Contact.getInstance();
		Application.getInstance();
		long seconds_af = System.currentTimeMillis();
		if( Locate.DEBUG )
		{
			// calculate the time consuming with the indexing
			Log.d( Locate.TAG , "Index Time Consuming：" + String.valueOf( seconds_af - seconds_be ) + "ms" );
		}
		if(BootCompleteReceiver.DEBUG)
		{
				Toast.makeText(mContext, "Service started", Toast.LENGTH_SHORT).show();
    }
	}

	@Override
	public int onStartCommand(
			Intent intent ,
			int flags ,
			int startId )
	{
		mHomeListen.start();
		return super.onStartCommand( intent , flags , startId );
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public IBinder onBind(
			Intent intent )
	{
		return null;
	}

	private void initHomeListen()
	{
		mHomeListen = new HomeListen( this );
		mHomeListen.setOnHomeBtnPressListener( new OnHomeBtnPressLitener() {

			@Override
			public void onHomeBtnPress()
			{
				// TODO
			}

			@Override
			public void onHomeBtnLongPress()
			{
				Toast.makeText( mContext , "HomeBtn LongPressed!" , Toast.LENGTH_SHORT ).show();
				Intent intent = new Intent( mContext , MainActivity.class );
				intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
				startActivity( intent );
			}
		} );
	}
	
	private HomeListen mHomeListen = null;
}
