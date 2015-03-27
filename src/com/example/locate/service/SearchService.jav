package com.example.locate.service;


import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;

import com.example.locate.content.Application;
import com.example.locate.content.ContactInfo;
import com.example.locate.content.Contact;
import com.example.locate.listener.HomeListen;
import com.example.locate.listener.HomeListen.OnHomeBtnPressLitener;
import com.example.locate.tools.Utils;
import com.example.locate.ui.MainActivity;


public class SearchService extends Service
{
	
	public static Context mContext;
	public static List<ContactInfo> mContactList = new ArrayList<ContactInfo>();
	public static List<ResolveInfo> allAppInfo = new ArrayList<ResolveInfo>();
	public static List<String> appPinyin = new ArrayList<String>();
	public static PackageManager pm;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		mContext = this;
		pm = mContext.getPackageManager();
		initHomeListen();
		mContactList = Contact.getInstance().getContactList();
		allAppInfo = Application.getInstance().getAllAppList();
		initAppPinyin();
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
	
	private void initAppPinyin()
	{
		for( ResolveInfo info : allAppInfo )
		{
			// Original name
			String original = String.valueOf( info.loadLabel( SearchService.pm ) );
			// After convert the origin name to pinyin
			String pinyin = Utils.chinese2pinyin( original );
			String name = original + pinyin + Utils.getFirstLetter( pinyin );
			appPinyin.add( name );
		}
	}
}
