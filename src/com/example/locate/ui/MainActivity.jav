package com.example.locate.ui;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.locate.Locate;
import com.example.locate.R;
import com.example.locate.adapter.ImageAdapter;
import com.example.locate.content.SearchResultInfo;
import com.example.locate.network.CheckUpdateTask;
import com.example.locate.network.LocateRequestQueue;
import com.example.locate.service.SearchService;


public class MainActivity extends Activity
{
	
	public static final String TAG = "MainActivity";
	public String url = "";
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
			SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( mContext );
			boolean debugMode = sharedPref.getBoolean( "pref_debug" , false );
			int visibility = debugMode ? View.VISIBLE : View.GONE;
			mTextView.setVisibility( visibility );
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
		mTextView.setVisibility( View.GONE );
		mGridView = (GridView)findViewById( R.id.gridView );
		searchResultBg = (ImageView)findViewById( R.id.search_result_bg );
		mEditText = (EditText)findViewById( R.id.editText );
		mEditText.addTextChangedListener( mTextWatcher );
		mEditText.setOnEditorActionListener( mOnEditorActionListener );
		checkUpdate();
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
		mTextView.setVisibility( View.GONE );
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
	
	/**
	 * Check whether there is new version released
	 */
	private void checkUpdate()
	{
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if( networkInfo != null && networkInfo.isConnected() )
		{
			new CheckUpdateTask( this ).execute( "http://movier.me:3000/" );
		}
		else
		{
			Toast.makeText( mContext , "Internet not accessible!" , Toast.LENGTH_SHORT ).show();
		}
	}
	
	/**
	 * We found the new version so tell the user to update
	 */
	public void showUpdateDialog()
	{
		DialogFragment newFragment = new UpdateDialog();
		newFragment.show( getFragmentManager() , "dialog" );
	}
	
	/**
	 * User choose to do the update
	 */
	public void doPositiveClick()
	{
		Uri uri = Uri.parse( url );
		Intent browserIntent = new Intent( Intent.ACTION_VIEW , uri );
		startActivity( browserIntent );
	}
	
	public void showSettingActivity(
			View view )
	{
		Intent intent = new Intent( this , SettingsActivity.class );
		startActivity( intent );
	}
	
	/**
	 * Use Volley to check update
	 */
	private void checkForUpdate()
	{
		RequestQueue queue = Volley.newRequestQueue( this );
		String url = "http://movier.me:3000/";
		JsonObjectRequest jsObjRequest = new JsonObjectRequest( Request.Method.GET , url , null , new Response.Listener<JSONObject>() {
			
			@Override
			public void onResponse(
					JSONObject response )
			{
				mEditText.setText( "Response: " + response.toString() );
			}
		} , new Response.ErrorListener() {
			
			@Override
			public void onErrorResponse(
					VolleyError error )
			{
				// TODO Auto-generated method stub
				mEditText.setText( "Response: " + error.toString() );
			}
		} );
		// Access the RequestQueue through your singleton class.
		queue.add( jsObjRequest );
	}
	
	/**
	 * Use volley to upload some information about the user
	 */
	private void uploadUserInfo()
	{
		// these parameters will be stored as user statistics
		final String android_id = Secure.getString( getContentResolver() , Secure.ANDROID_ID );
		String build_id = Build.ID;
		final String build_brand = Build.BRAND;
		String build_manufacturer = Build.MANUFACTURER;
		String build_model = Build.MODEL;
		String build_serial = Build.SERIAL;
		// Instantiate the RequestQueue.
		RequestQueue queue = LocateRequestQueue.getInstance( this.getApplicationContext() ).getRequestQueue();
		String url = "http://movier.me:3000/add";
		// Request a string response from the provided URL.
		StringRequest stringRequest = new StringRequest( Request.Method.POST , url , new Response.Listener<String>() {
			
			@Override
			public void onResponse(
					String response )
			{
				// Display the first 500 characters of the response string.
				mEditText.setText( "Response is: " + response );
				// Get a handle to a SharedPreferences
				SharedPreferences sharedPref = getPreferences( Context.MODE_PRIVATE );
				// Write to Shared Preferences
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString( "id" , response );
				editor.apply();
			}
		} , new Response.ErrorListener() {
			
			@Override
			public void onErrorResponse(
					VolleyError error )
			{
				mEditText.setText( "That didn't work!" );
			}
		} ) {
			
			@Override
			protected Map<String , String> getParams()
			{
				// set post fields here
				Map<String , String> map = new HashMap<String , String>();
				map.put( "android_id" , android_id );
				map.put( "build_brand" , build_brand );
				return map;
			}
		};
		// Add the request to the RequestQueue.
		queue.add( stringRequest );
	}
}
