package com.example.locate;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.locate.content.SearchFactory;
import com.example.locate.content.SearchResultInfo;


/**
 * Search according to the settings
 *
 * It's up to user to decide search range, applications or contact or...
 * search order application first or contact first and so on
 */
public class Locate
{
	
	public enum Range
	{
		APP , CONTACT , WEB
	}
	
	private static Locate mLocate;
	public static Context mContext;
	
	public static Locate getInstance(
			Context c )
	{
		if( mLocate == null )
		{
			mLocate = new Locate();
			mContext = c;
		}
		return mLocate;
	}
	
	/**
	 * Main function for the entire application
	 *
	 * @param str
	 * @return
	 */
	public List<SearchResultInfo> search(
			String str )
	{
		List<SearchResultInfo> resultList = new ArrayList<SearchResultInfo>();
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
