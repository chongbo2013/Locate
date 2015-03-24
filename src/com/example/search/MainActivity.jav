package com.example.search;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;


public class MainActivity extends Activity
{
	
	private Context mContext;
	private GridView mGridView;
	private EditText mEditText;
	private ImageButton mImageButton;
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
			int visibility = s.toString().equals( "" ) ? View.GONE : View.VISIBLE;
			mImageButton.setVisibility( visibility );
			// Do the searching
			List<Object> list = SearchProxy.getInstance( mContext ).search( String.valueOf( s ) );
			mGridView.setAdapter( new ImageAdapter( mContext , list ) );
		}
	};
	
	@Override
	protected void onCreate(
			Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		Intent startIntent = new Intent( this , SearchService.class );
		startService( startIntent );
		mContext = this;
		mImageButton = (ImageButton)findViewById( R.id.imageButton2 );
		mGridView = (GridView)findViewById( R.id.gridView );
		mEditText = (EditText)findViewById( R.id.editText );
		mEditText.addTextChangedListener( mTextWatcher );
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService( Activity.INPUT_METHOD_SERVICE );
		inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken() , 0 );
	}
	
	/**
	 * When user click the cancel icon, we should clear the search content
	 *
	 * @param view
	 */
	public void clearSearchContent(
			View view )
	{
		// remove the text watcher before clear the search content
		mEditText.removeTextChangedListener( mTextWatcher );
		mImageButton.setVisibility( View.GONE );
		mEditText.setText( "" );
		// set the text watcher after clear the search content
		mEditText.addTextChangedListener( mTextWatcher );
	}
}
