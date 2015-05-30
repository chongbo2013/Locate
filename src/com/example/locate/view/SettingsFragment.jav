package com.example.locate.view;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

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
				Intent intent = new Intent( getActivity() , SettingsActivity.class );
				// Ask setting activity to use about fragment
				intent.putExtra( "about" , true );
				startActivity( intent );
				return true;
			}
		} );
	}
}