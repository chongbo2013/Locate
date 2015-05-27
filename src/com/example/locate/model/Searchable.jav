package com.example.locate.model;


import java.util.List;


/**
 * Class implements this interface means it could be searched
 *
 * @author Oliver
 */
public interface Searchable
{
	
	List<SearchResultInfo> search(
			String str );
}
