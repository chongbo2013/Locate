package com.example.locate.adapter;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locate.R;
import com.example.locate.model.SearchResultInfo;
import com.example.locate.network.CommonRequest;


public class ImageAdapter extends BaseAdapter
{
	
	private Context mContext;
	/** search result list may contain ResolveInfo or ContactInfo and so on */
	private List<SearchResultInfo> mResultList;
	
	public ImageAdapter(
			Context c ,
			List<SearchResultInfo> appsList )
	{
		mContext = c;
		mResultList = appsList;
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
		final SearchResultInfo contactInfo = mResultList.get( position );
		if( convertView == null )
		{
			//Inflate the layout
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			myView = inflater.inflate( R.layout.grid_item , null );
			// Add The Image!!!
			ImageView iv = (ImageView)myView.findViewById( R.id.grid_item_image );
			// Only set the photo when not empty otherwise use default contact photo
			Drawable photo = contactInfo.getIcon();
			if( photo != null )
				iv.setImageDrawable( photo );
			// Add The Text!!!
			TextView tv = (TextView)myView.findViewById( R.id.grid_item_text );
			tv.setText( contactInfo.getTitle() );
		}
		myView.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(
					View v )
			{
				// when clicked get into the detail information of the contact
				mContext.startActivity( contactInfo.getClick() );
				if( mContext instanceof Activity )
					new CommonRequest( (Activity)mContext ).uploadUserAction( contactInfo.getTitle() );
			}
		} );
		/**
		 * Current we don't need long click event, but it doesn't mean forever
		 */
		//		if( contactInfo.getLongClick() != null )
		//		{
		//			myView.setOnLongClickListener( new OnLongClickListener() {
		//				
		//				@Override
		//				public boolean onLongClick(
		//						View v )
		//				{
		//					mContext.startActivity( contactInfo.getLongClick() );
		//					return true;
		//				}
		//			} );
		//		}
		return myView;
	}
}
