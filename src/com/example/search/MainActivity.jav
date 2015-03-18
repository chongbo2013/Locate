package com.example.search;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;


public class MainActivity extends Activity
{
	
	private Context mContext;
	private GridView mGridView;
	private TextWatcher mTextWatcher = new TextWatcher() {
		
		@Override
		public void afterTextChanged(
				Editable s )
		{
			// TODO Auto-generated method stub
		}
		
		@Override
		public void beforeTextChanged(
				CharSequence s ,
				int arg1 ,
				int arg2 ,
				int arg3 )
		{
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onTextChanged(
				CharSequence s ,
				int arg1 ,
				int arg2 ,
				int arg3 )
		{
			// Set the visibility of cancel icon according to whether empty
			ImageButton cancel = (ImageButton)findViewById( R.id.imageButton2 );
			int visibility = s.toString().equals( "" ) ? View.GONE : View.VISIBLE;
			cancel.setVisibility( visibility );
			// Do the searching
			SearchProxy proxy = SearchProxy.getInstance( mContext );
			List<ResolveInfo> list = proxy.search( String.valueOf( s ) );
			mGridView.setAdapter( new ImageAdapter( mContext , list ) );
		}
	};
	
	@Override
	protected void onCreate(
			Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		mContext = this;
		mGridView = (GridView)findViewById( R.id.gridView );
		EditText editText = (EditText)findViewById( R.id.editText );
		editText.addTextChangedListener( mTextWatcher );
	}
}
