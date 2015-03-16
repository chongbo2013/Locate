package com.example.search;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;


public class SearchProxy
{
	
	private static SearchProxy mSearchProxy;
	private static Context mContext;
	
	private SearchProxy()
	{
	}
	
	public static SearchProxy getInstance(
			Context c )
	{
		if( mSearchProxy == null )
		{
			mSearchProxy = new SearchProxy();
			mContext = c;
		}
		return mSearchProxy;
	}
	
	public List<ApplicationInfo> search(
			String str )
	{
		List<ApplicationInfo> appInfo = new ArrayList<ApplicationInfo>();
		PackageManager pm = mContext.getPackageManager();
		List<ApplicationInfo> allAppInfo = pm.getInstalledApplications( PackageManager.GET_META_DATA );
		for( ApplicationInfo info : allAppInfo )
		{
			String name = String.valueOf( info.loadLabel( pm ) );
			if( str.toLowerCase().equals( name.toLowerCase() ) )
			{
				appInfo.add( info );
			}
		}
		return appInfo;
	}
}
