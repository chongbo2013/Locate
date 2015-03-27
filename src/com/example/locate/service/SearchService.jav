package com.example.locate.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.locate.listener.HomeListen;
import com.example.locate.listener.HomeListen.OnHomeBtnPressLitener;
import com.example.locate.ui.MainActivity;


public class SearchService extends Service
{
	
	private Context mContext;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		mContext = this;
		initHomeListen();
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
				Intent intent = new Intent( mContext , MainActivity.class );
				intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
				startActivity( intent );
			}
		} );
	}
	
	private HomeListen mHomeListen = null;
}
