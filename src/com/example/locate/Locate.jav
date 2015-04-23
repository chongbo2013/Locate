package com.example.locate;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
	
	// whether develop in debug mode or not
	public static final boolean DEBUG = true;
	// debug tag for LogCat
	public static final String TAG = "Locate";
	
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
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( mContext );
		boolean showApp = sharedPref.getBoolean( "pref_app" , true );
		boolean showContact = sharedPref.getBoolean( "pref_contact" , true );
		List<Range> list = new ArrayList<Range>();
		if( showApp )
			list.add( Range.APP );
		if( showContact )
			list.add( Range.CONTACT );
		// always show web search option
		list.add( Range.WEB );
		return list;
	}
}
