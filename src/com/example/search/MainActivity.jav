package com.example.search;


import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.GridView;


public class MainActivity extends Activity
{
	
	@Override
	protected void onCreate(
			Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		GridView gridView = (GridView)findViewById( R.id.gridView );
		PackageManager packageManager = getPackageManager();
		List<ApplicationInfo> list = packageManager.getInstalledApplications( PackageManager.GET_META_DATA );
		gridView.setAdapter( new ImageAdapter( this , list ) );
	}
}
