package com.example.locate.content;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ResolveInfo;

import com.example.locate.service.SearchService;


/**
 * All the applications in your android device
 */
public class Application implements Searchable
{
	
	private static Application mApplication;
	private List<ResolveInfo> mResolveInfoList = new ArrayList<ResolveInfo>();
	
	private Application()
	{
		Intent i = new Intent( Intent.ACTION_MAIN );
		i.addCategory( Intent.CATEGORY_LAUNCHER );
		mResolveInfoList = SearchService.mContext.getPackageManager().queryIntentActivities( i , 0 );
	}
	
	public static Application getInstance()
	{
		if( mApplication == null )
			mApplication = new Application();
		return mApplication;
	}
	
	public List<ResolveInfo> getAllAppList()
	{
		return mResolveInfoList;
	}
	
	@Override
	public List<Object> search(
			String str )
	{
		List<ResolveInfo> allAppInfo = SearchService.allAppInfo;
		List<Object> appInfo = new ArrayList<Object>();
		for( int i = 0 ; i < allAppInfo.size() ; i++ )
		{
			if( SearchService.appPinyin.get( i ).toLowerCase().contains( str.toLowerCase() ) )
			{
				appInfo.add( allAppInfo.get( i ) );
			}
		}
		return appInfo;
	}
}
