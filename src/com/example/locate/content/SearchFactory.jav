package com.example.locate.content;


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
				s = SearchApp.getInstance();
				break;
			case CONTACT:
				s = SearchContact.getInstance();
				break;
		}
		return s;
	}
}
