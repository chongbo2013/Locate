package com.example.locate.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

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
		Contact.getInstance();
		Application.getInstance();
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
