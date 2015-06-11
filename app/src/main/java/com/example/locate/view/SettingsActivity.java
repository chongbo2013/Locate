package com.example.locate.view;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.example.locate.R;


public class SettingsActivity extends ActionBarActivity
{

    private boolean b;

    @Override
    protected void onCreate(
            Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        // Navigating up with the app icon
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        // Get the intent extra to start about fragment if required
        b = getIntent().getBooleanExtra( "about" , false );
        if( b )
            setTitle( getResources().getString( R.string.pref_about ) );
        Fragment fragment = b ? new AboutFragment() : new SettingsFragment();
        getFragmentManager().beginTransaction().replace( android.R.id.content , fragment ).commit();
    }

    /**
     * Override the navigation up intent
     */
    public Intent getSupportParentActivityIntent()
    {
        Intent i;
        if( b )
            i = new Intent( this , SettingsActivity.class );
        else
            i = new Intent( this , MainActivity.class );
        return i;
    }
}
