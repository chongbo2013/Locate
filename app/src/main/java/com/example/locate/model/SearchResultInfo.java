package com.example.locate.model;


import android.content.Intent;
import android.graphics.drawable.Drawable;


/**
 * A single search result information
 */
public class SearchResultInfo
{
	
	private String title;
	private Drawable icon;
	private Intent click;
	private Intent longClick;
	
	public SearchResultInfo(
			String title ,
			Drawable icon ,
			Intent click )
	{
		this.title = title;
		this.icon = icon;
		this.click = click;
	}
	
	public SearchResultInfo(
			String title ,
			Drawable icon ,
			Intent click ,
			Intent longClick )
	{
		this( title , icon , click );
		this.longClick = longClick;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public Drawable getIcon()
	{
		return icon;
	}
	
	public Intent getClick()
	{
		return click;
	}
	
	public Intent getLongClick()
	{
		return longClick;
	}
}
