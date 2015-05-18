package com.example.locate.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.IBinder;
import android.os.Process;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.locate.LocateApplication;
import com.example.locate.content.Application;
import com.example.locate.content.Contact;
import com.example.locate.listener.HomeListen;
import com.example.locate.listener.HomeListen.OnHomeBtnPressLitener;
import com.example.locate.ui.MainActivity;


public class SearchService extends Service
{
	
	public static final boolean DEBUG = false;
	private Context mContext;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		mContext = LocateApplication.getContext();
		if( DEBUG )
			Toast.makeText( mContext , "SearchService onCreate" , Toast.LENGTH_SHORT ).show();
		initHomeListen();
		// start a new thread to initialize the data in memory since it's time wasting
		new Thread( new Runnable() {
			
			@Override
			public void run()
			{
				Contact.getInstance();
				Application.getInstance();
			}
		} ).start();
		mContext.getContentResolver().registerContentObserver( ContactsContract.Contacts.CONTENT_URI , true , contentObserver );
	}
	
	@Override
	public int onStartCommand(
			Intent intent ,
			int flags ,
			int startId )
	{
		if( DEBUG )
			Toast.makeText( mContext , "SearchService onStartCommand" , Toast.LENGTH_SHORT ).show();
		mHomeListen.start();
		return super.onStartCommand( intent , flags , startId );
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if( DEBUG )
			Toast.makeText( mContext , "SearchService onDestroy" , Toast.LENGTH_SHORT ).show();
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
				if( DEBUG )
					Toast.makeText( mContext , "HomeBtn LongPressed!" , Toast.LENGTH_SHORT ).show();
				Intent intent = new Intent( mContext , MainActivity.class );
				intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
				startActivity( intent );
			}
		} );
	}
	
	private HomeListen mHomeListen = null;
	
	private class ContactContentObserver extends ContentObserver
	{
		
		public ContactContentObserver()
		{
			super( null );
		}
		
		@Override
		public void onChange(
				boolean selfChange )
		{
			super.onChange( selfChange );
			Log.d( "yangxiaoming" , "MyContextObserver onChange" );
			Process.killProcess( Process.myPid() );
		}
	}
	
	ContactContentObserver contentObserver = new ContactContentObserver();
}
