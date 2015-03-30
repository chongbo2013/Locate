package com.example.locate.ui;


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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.locate.Locate;
import com.example.locate.R;
import com.example.locate.adapter.ImageAdapter;
import com.example.locate.service.SearchService;


public class MainActivity extends Activity
{
	
	public static final String TAG = "MainActivity";
	private Context mContext;
	private GridView mGridView;
	private EditText mEditText;
	private ImageButton mImageButton;
	private TextView mTextView;
	private LinearLayout searchResult;
	private List<Object> resultList;
	private TextWatcher mTextWatcher = new TextWatcher() {
		
		long seconds_be , seconds_af;
		
		@Override
		public void afterTextChanged(
				Editable s )
		{
			// TODO Auto-generated method stub
			seconds_af = System.currentTimeMillis();
			int resultNum = resultList.size();
			mTextView.setText( "总计：" + resultNum + " 个。耗时：" + String.valueOf( seconds_af - seconds_be ) + " 毫秒。" );
		}
		
		@Override
		public void beforeTextChanged(
				CharSequence s ,
				int arg1 ,
				int arg2 ,
				int arg3 )
		{
			// We are searching, hide the background
			if( searchResult.getBackground() != null )
				searchResult.setBackground( null );
			// TODO Auto-generated method stub
			seconds_be = System.currentTimeMillis();
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
			resultList = Locate.getInstance( mContext ).search( String.valueOf( s ) );
			mGridView.setAdapter( new ImageAdapter( mContext , resultList ) );
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
		mTextView = (TextView)findViewById( R.id.textView_statistics );
		mGridView = (GridView)findViewById( R.id.gridView );
		searchResult = (LinearLayout)findViewById( R.id.searchResult );
		searchResult.setBackgroundResource( R.mipmap.contact );
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
		mTextView.setText( "" );
		mGridView.setAdapter( null );
		searchResult.setBackgroundResource( R.mipmap.contact );
		// set the text watcher after clear the search content
		mEditText.addTextChangedListener( mTextWatcher );
	}
}
