package com.example.search;


import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageAdapter extends BaseAdapter
{
	
	private Context mContext;
	private PackageManager mPackageManager;
	/** search result list may contain ResolveInfo or ContactInfo and so on */
	private List<Object> mResultList;
	
	public ImageAdapter(
			Context c ,
			List<Object> appsList )
	{
		mContext = c;
		mResultList = appsList;
		mPackageManager = mContext.getPackageManager();
	}
	
	@Override
	public int getCount()
	{
		int size = 0;
		if( mResultList != null )
		{
			size = mResultList.size();
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
		View myView = convertView;
		// the search result is from application
		if( mResultList.get( position ) instanceof ResolveInfo )
		{
			final ResolveInfo resolveInfo = (ResolveInfo)mResultList.get( position );
			if( convertView == null )
			{
				//Inflate the layout
				LayoutInflater inflater = (LayoutInflater)mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
				myView = inflater.inflate( R.layout.grid_item , null );
				// Add The Image!!!           
				ImageView iv = (ImageView)myView.findViewById( R.id.grid_item_image );
				iv.setImageDrawable( resolveInfo.loadIcon( mPackageManager ) );
				// Add The Text!!!
				TextView tv = (TextView)myView.findViewById( R.id.grid_item_text );
				tv.setText( resolveInfo.loadLabel( mPackageManager ) );
			}
			myView.setOnClickListener( new OnClickListener() {
				
				@Override
				public void onClick(
						View v )
				{
					// start the application when being clicked
					Intent LaunchIntent = mPackageManager.getLaunchIntentForPackage( resolveInfo.activityInfo.applicationInfo.packageName );
					mContext.startActivity( LaunchIntent );
				}
			} );
			myView.setOnLongClickListener( new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(
						View v )
				{
					// delete the application when being long clicked
					Intent intent = new Intent( Intent.ACTION_DELETE );
					intent.setData( Uri.fromParts( "package" , resolveInfo.activityInfo.packageName , null ) );
					mContext.startActivity( intent );
					return true;
				}
			} );
		}
		return myView;
	}
}
