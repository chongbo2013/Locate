package com.example.locate.model;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.example.locate.LocateApplication;
import com.example.locate.R;
import com.example.locate.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;


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
		List<SearchResultInfo> appInfo = new ArrayList<>();
		Resources resources = LocateApplication.getContext().getResources();
		String title = resources.getString(R.string.internet);
		Drawable icon = resources.getDrawable(R.mipmap.internet);
		Intent click = CommonUtil.getWebSearchIntent(str);
		appInfo.add( new SearchResultInfo( title , icon , click ) );
		return appInfo;
	}
}
