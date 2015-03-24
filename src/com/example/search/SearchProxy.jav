package com.example.search;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;


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
	
	public List<Object> search(
			String str )
	{
		List<Object> appInfo = new ArrayList<Object>();
		PackageManager pm = mContext.getPackageManager();
		Intent i = new Intent( Intent.ACTION_MAIN );
		i.addCategory( Intent.CATEGORY_LAUNCHER );
		List<ResolveInfo> allAppInfo = pm.queryIntentActivities( i , 0 );
		for( ResolveInfo info : allAppInfo )
		{
			// Original name
			String original = String.valueOf( info.loadLabel( pm ) );
			// After convert the origin name to pinyin
			String pinyin = Utils.chinese2pinyin( original );
			String name = original + pinyin + Utils.deleteSpace( pinyin ) + Utils.getFirstLetter( pinyin );
			if( name.toLowerCase().contains( str.toLowerCase() ) )
			{
				appInfo.add( info );
			}
		}
		return appInfo;
	}
}
