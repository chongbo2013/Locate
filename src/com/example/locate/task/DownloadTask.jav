package com.example.locate.task;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;

import com.example.locate.service.SearchService;
import com.example.locate.ui.MainActivity;


public class CheckUpdateTask extends AsyncTask<String , Void , String>
{
	
	private Activity activity;
	
	public CheckUpdateTask(
			Activity activity )
	{
		this.activity = activity;
	}
	
	@Override
	protected String doInBackground(
			String ... urls )
	{
		try
		{
			String result = downloadUrl( urls[0] );
			JSONObject jObject = new JSONObject( result );
			int version = jObject.getInt( "version" );
			PackageManager pm = SearchService.mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo( SearchService.mContext.getPackageName() , 0 );
			int versioncode = pi.versionCode;
			String url = "";
			if( version > versioncode )
			{
				url = jObject.getString( "url" );
			}
			return url;
		}
		catch( IOException | JSONException | NameNotFoundException e )
		{
			return "";
		}
	}
	
	// onPostExecute displays the results of the AsyncTask.
	@Override
	protected void onPostExecute(
			String result )
	{
		// Result is not empty indicates that we got a new version
		if( !result.isEmpty() )
		{
			MainActivity main = (MainActivity)activity;
			main.url = result;
			main.showUpdateDialog();
		}
	}
	
	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	private String downloadUrl(
			String myurl ) throws IOException
	{
		InputStream is = null;
		// Only display the first 500 characters of the retrieved
		// web page content.
		int len = 500;
		try
		{
			URL url = new URL( myurl );
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setReadTimeout( 10000 /* milliseconds */);
			conn.setConnectTimeout( 15000 /* milliseconds */);
			conn.setRequestMethod( "GET" );
			conn.setDoInput( true );
			// Starts the query
			conn.connect();
			is = conn.getInputStream();
			// Convert the InputStream into a string
			String contentAsString = readIt( is , len );
			return contentAsString;
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		}
		finally
		{
			if( is != null )
			{
				is.close();
			}
		}
	}
	
	// Reads an InputStream and converts it to a String.
	public String readIt(
			InputStream stream ,
			int len ) throws IOException , UnsupportedEncodingException
	{
		Reader reader = null;
		reader = new InputStreamReader( stream , "UTF-8" );
		char[] buffer = new char[len];
		reader.read( buffer );
		return new String( buffer );
	}
}
