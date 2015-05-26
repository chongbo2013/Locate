package com.example.locate.ui;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


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
	
	public void openProjectWebsite(
			View view )
	{
		Intent browserIntent = new Intent( Intent.ACTION_VIEW , Uri.parse( "http://locate.movier.me/" ) );
		startActivity( browserIntent );
	}
	
	public void openDeveloperWebsite(
			View view )
	{
		Intent browserIntent = new Intent( Intent.ACTION_VIEW , Uri.parse( "http://movier.me/" ) );
		startActivity( browserIntent );
	}
}
