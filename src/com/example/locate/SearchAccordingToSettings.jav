package com.example.locate;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;


/**
 * Search according to the settings
 * 
 * It's up to user to decide search range, applications or contact or...
 * search order application first or contact first and so on
 */
public class SearchAccordingToSettings
{
	
	public enum Range
	{
		APP , CONTACT
	}
	
	private static SearchAccordingToSettings mSearchProxy;
	public static Context mContext;
	
	public static SearchAccordingToSettings getInstance(
			Context c )
	{
		if( mSearchProxy == null )
		{
			mSearchProxy = new SearchAccordingToSettings();
			mContext = c;
		}
		return mSearchProxy;
	}
	
	public List<Object> search(
			String str )
	{
		List<Object> resultList = new ArrayList<Object>();
		List<Range> range = range();
		for( Range i : range )
			resultList.addAll( SearchFactory.searchFactoryMethod( i ).search( String.valueOf( str ) ) );
		return resultList;
	}
	
	/**
	 * Read user settings to decide search range
	 * Default show all result
	 * 
	 * @return
	 */
	private List<Range> range()
	{
		List<Range> list = new ArrayList<Range>();
		for( Range r : Range.values() )
			list.add( r );
		return list;
	}
}
