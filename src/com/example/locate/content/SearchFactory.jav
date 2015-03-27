package com.example.locate.content;


import com.example.locate.Locate;
import com.example.locate.Locate.Range;


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
				s = SearchApp.getInstance( Locate.mContext );
				break;
			case CONTACT:
				s = SearchContact.getInstance( Locate.mContext );
				break;
		}
		return s;
	}
}
