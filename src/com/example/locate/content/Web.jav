package com.example.locate.content;


import java.util.ArrayList;
import java.util.List;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.example.locate.LocateApplication;
import com.example.locate.R;


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
		Drawable icon = LocateApplication.getContext().getResources().getDrawable( R.mipmap.internet );
		Intent click = new Intent( Intent.ACTION_WEB_SEARCH );
		click.putExtra( SearchManager.QUERY , str );
		appInfo.add( new SearchResultInfo( title , icon , click ) );
		return appInfo;
	}
}
