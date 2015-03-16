package com.example.search;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;


public class MainActivity extends Activity
{
	
	private Context mContext;
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
			Toast.makeText( mContext , String.valueOf( s ) , Toast.LENGTH_SHORT ).show();
		}
	};
	
	@Override
	protected void onCreate(
			Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		mContext = this;
		GridView gridView = (GridView)findViewById( R.id.gridView );
		PackageManager packageManager = getPackageManager();
		List<ApplicationInfo> list = packageManager.getInstalledApplications( PackageManager.GET_META_DATA );
		gridView.setAdapter( new ImageAdapter( this , list ) );
		EditText editText = (EditText)findViewById( R.id.editText );
		editText.addTextChangedListener( mTextWatcher );
	}
}
