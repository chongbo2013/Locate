package com.example.locate.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class SettingsActivity extends ActionBarActivity
{
	
	@Override
	protected void onCreate(
			Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		// Navigating up with the app icon
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled( true );
		// Get the intent extra to start about fragment if required
		boolean b = getIntent().getBooleanExtra( "about" , false );
		Fragment fragment = b ? new AboutFragment() : new SettingsFragment();
		getFragmentManager().beginTransaction().replace( android.R.id.content , fragment ).commit();
	}
}
