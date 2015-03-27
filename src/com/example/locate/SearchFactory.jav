package com.example.locate;


import com.example.locate.SearchAccordingToSettings.Range;


/**
 * I want to use factory method pattern
 * 
 * @author Oliver
 */
public class SearchFactory
{
	
	/**
	 * We always give you what you want
	 * 
	 * @param r
	 * @return
	 */
	public static Searchable searchFactoryMethod(
			Range r )
	{
		Searchable s = null;
		switch( r )
		{
			case APP:
				s = SearchApp.getInstance( SearchAccordingToSettings.mContext );
				break;
			case CONTACT:
				s = SearchContact.getInstance( SearchAccordingToSettings.mContext );
				break;
		}
		return s;
	}
}
