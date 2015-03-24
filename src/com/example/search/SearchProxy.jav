package com.example.search;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;


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
		List<Object> resultList = new ArrayList<Object>();
		// search application
		resultList.addAll( SearchApp.getInstance( mContext ).search( String.valueOf( str ) ) );
		// search contact
		resultList.addAll( SearchContact.getInstance( mContext ).search( String.valueOf( str ) ) );
		return resultList;
	}
}
