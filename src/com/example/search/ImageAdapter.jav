package com.example.search;


import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter
{
	
	private Context mContext;
	private PackageManager mPackageManager;
	private List<ResolveInfo> mAppsList;
	
	public ImageAdapter(
			Context c ,
			List<ResolveInfo> appsList )
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
				// start the application when being clicked
				Intent LaunchIntent = mPackageManager.getLaunchIntentForPackage( mAppsList.get( position ).activityInfo.applicationInfo.packageName );
				mContext.startActivity( LaunchIntent );
			}
		} );
		imageView.setOnLongClickListener( new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(
					View v )
			{
				// delete the application when being long clicked
				Intent intent = new Intent( Intent.ACTION_DELETE );
				intent.setData( Uri.fromParts( "package" , mAppsList.get( position ).activityInfo.packageName , null ) );
				mContext.startActivity( intent );
				return true;
			}
		} );
		return imageView;
	}
}
