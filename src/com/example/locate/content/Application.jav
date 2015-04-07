package com.example.locate.content;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.example.locate.service.SearchService;
import com.example.locate.tools.Utils;


/**
 * All the applications in your android device
 */
public class Application implements Searchable
{
	
	private static Application mApplication;
	private List<SearchResultInfo> mSearchResultInfoList = new ArrayList<SearchResultInfo>();
	private List<String> searchableStr = new ArrayList<String>();
	
	private Application()
	{
		PackageManager pm = SearchService.mContext.getPackageManager();
		Intent i = new Intent( Intent.ACTION_MAIN );
		i.addCategory( Intent.CATEGORY_LAUNCHER );
		for( ResolveInfo info : pm.queryIntentActivities( i , 0 ) )
		{
			// Original name
			String original = String.valueOf( info.loadLabel( pm ) );
			// After convert the origin name to pinyin
			String pinyin = Utils.chinese2pinyin( original );
			String name = original + pinyin + Utils.getFirstLetter( pinyin );
			searchableStr.add( name );
			Drawable icon = info.loadIcon( pm );
			// start the application when being clicked
			Intent click = pm.getLaunchIntentForPackage( info.activityInfo.applicationInfo.packageName );
			// delete the application when being long clicked
			Intent longClick = new Intent( Intent.ACTION_DELETE );
			longClick.setData( Uri.fromParts( "package" , info.activityInfo.packageName , null ) );
			mSearchResultInfoList.add( new SearchResultInfo( original , icon , click , longClick ) );
		}
	}
	
	public static Application getInstance()
	{
		if( mApplication == null )
			mApplication = new Application();
		return mApplication;
	}
	
	@Override
	public List<SearchResultInfo> search(
			String str )
	{
		List<SearchResultInfo> appInfo = new ArrayList<SearchResultInfo>();
		for( String name : searchableStr )
		{
			if( name.toLowerCase().contains( str.toLowerCase() ) )
			{
				appInfo.add( mSearchResultInfoList.get( searchableStr.indexOf( name ) ) );
			}
		}
		return appInfo;
	}
}
