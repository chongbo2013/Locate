package com.example.locate.ui;


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
		getFragmentManager().beginTransaction().replace( android.R.id.content , new SettingsFragment() ).commit();
	}
}
