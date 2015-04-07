package com.example.locate.ui;


import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.locate.Locate;
import com.example.locate.R;
import com.example.locate.adapter.ImageAdapter;
import com.example.locate.content.SearchResultInfo;
import com.example.locate.service.SearchService;


public class MainActivity extends Activity
{
	
	public static final String TAG = "MainActivity";
	private Context mContext;
	private GridView mGridView;
	private EditText mEditText;
	private ImageButton mImageButton;
	private TextView mTextView;
	private ImageView searchResultBg;
	private List<SearchResultInfo> resultList;
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
			if( searchResultBg.getBackground() != null )
				searchResultBg.setBackground( null );
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
	private OnEditorActionListener mOnEditorActionListener = new OnEditorActionListener() {
		
		@Override
		public boolean onEditorAction(
				TextView v ,
				int actionId ,
				KeyEvent event )
		{
			boolean handled = false;
			if( actionId == EditorInfo.IME_ACTION_SEARCH )
			{
				webSearch();
				handled = true;
			}
			return handled;
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
		searchResultBg = (ImageView)findViewById( R.id.search_result_bg );
		mEditText = (EditText)findViewById( R.id.editText );
		mEditText.addTextChangedListener( mTextWatcher );
		mEditText.setOnEditorActionListener( mOnEditorActionListener );
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		// hide the keyboard before start a new activity
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
		searchResultBg.setBackgroundResource( R.mipmap.search_result_bg );
		// set the text watcher after clear the search content
		mEditText.addTextChangedListener( mTextWatcher );
	}
	
	/**
	 * Perform a web search
	 */
	private void webSearch()
	{
		Intent intent = new Intent( Intent.ACTION_WEB_SEARCH );
		intent.putExtra( SearchManager.QUERY , mEditText.getText().toString() );
		startActivity( intent );
	}
}
