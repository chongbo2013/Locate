package com.example.locate.content;


import java.util.ArrayList;
import java.util.List;

import android.app.SearchManager;
import android.content.Intent;


/**
 * Web search
 */
public class Web implements Searchable
{
	
	private static Web mWeb;
	
	private Web()
	{
	}
	
	public static Web getInstance()
	{
		if( mWeb == null )
			mWeb = new Web();
		return mWeb;
	}
	
	@Override
	public List<SearchResultInfo> search(
			String str )
	{
		List<SearchResultInfo> appInfo = new ArrayList<SearchResultInfo>();
		String title = "Internet";
		Intent click = new Intent( Intent.ACTION_WEB_SEARCH );
		click.putExtra( SearchManager.QUERY , str );
		appInfo.add( new SearchResultInfo( title , null , click ) );
		return appInfo;
	}
}
