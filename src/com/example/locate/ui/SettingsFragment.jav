package com.example.locate.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.example.locate.LocateApplication;
import com.example.locate.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment
{
	
	@Override
	public void onCreate(
			Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		// Load the preferences from an XML resource
		addPreferencesFromResource( R.xml.preferences );
		// Get about preference
		Preference myPref = (Preference)findPreference( "pref_about" );
		// Add click listener to the about preference
		myPref.setOnPreferenceClickListener( new OnPreferenceClickListener() {
			
			public boolean onPreferenceClick(
					Preference preference )
			{
				Toast.makeText( LocateApplication.getContext() , "hello about perference" , Toast.LENGTH_SHORT ).show();
				return true;
			}
		} );
	}
}
