package com.example.locate.content;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ResolveInfo;

import com.example.locate.service.SearchService;
import com.example.locate.tools.Utils;


/**
 * All the applications in your android device
 */
public class Application implements Searchable
{
	
	private static Application mApplication;
	private List<ResolveInfo> mResolveInfoList = new ArrayList<ResolveInfo>();
	private List<String> searchableStr = new ArrayList<String>();
	
	private Application()
	{
		Intent i = new Intent( Intent.ACTION_MAIN );
		i.addCategory( Intent.CATEGORY_LAUNCHER );
		mResolveInfoList = SearchService.mContext.getPackageManager().queryIntentActivities( i , 0 );
		for( ResolveInfo info : mResolveInfoList )
		{
			// Original name
			String original = String.valueOf( info.loadLabel( SearchService.mContext.getPackageManager() ) );
			// After convert the origin name to pinyin
			String pinyin = Utils.chinese2pinyin( original );
			String name = original + pinyin + Utils.getFirstLetter( pinyin );
			searchableStr.add( name );
		}
	}
	
	public static Application getInstance()
	{
		if( mApplication == null )
			mApplication = new Application();
		return mApplication;
	}
	
	@Override
	public List<Object> search(
			String str )
	{
		List<Object> appInfo = new ArrayList<Object>();
		for( String name : searchableStr )
		{
			if( name.toLowerCase().contains( str.toLowerCase() ) )
			{
				appInfo.add( mResolveInfoList.get( searchableStr.indexOf( name ) ) );
			}
		}
		return appInfo;
	}
}
