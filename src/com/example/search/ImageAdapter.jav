package com.example.search;


import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


public class ImageAdapter extends BaseAdapter
{
	
	private Context mContext;
	private PackageManager mPackageManager;
	private List<ApplicationInfo> mAppsList;
	
	public ImageAdapter(
			Context c ,
			List<ApplicationInfo> appsList )
	{
		mContext = c;
		mAppsList = appsList;
		mPackageManager = mContext.getPackageManager();
	}
	
	@Override
	public int getCount()
	{
		int size = 0;
		if( mAppsList != null )
		{
			size = mAppsList.size();
		}
		return size;
	}
	
	@Override
	public Object getItem(
			int position )
	{
		return null;
	}
	
	@Override
	public long getItemId(
			int position )
	{
		return 0;
	}
	
	@Override
	public View getView(
			final int position ,
			View convertView ,
			ViewGroup parent )
	{
		ImageView imageView;
		if( convertView == null )
		{ // if it's not recycled, initialize some attributes
			imageView = new ImageView( mContext );
			imageView.setLayoutParams( new GridView.LayoutParams( 144 , 144 ) );
			imageView.setScaleType( ImageView.ScaleType.CENTER_CROP );
			imageView.setPadding( 8 , 8 , 8 , 8 );
		}
		else
		{
			imageView = (ImageView)convertView;
		}
		imageView.setImageDrawable( mAppsList.get( position ).loadIcon( mPackageManager ) );
		imageView.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(
					View v )
			{
				Toast.makeText( mContext , "" + position , Toast.LENGTH_SHORT ).show();
			}
		} );
		return imageView;
	}
}
