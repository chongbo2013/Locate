package com.example.locate.ui;


import android.app.Fragment;
import android.os.Bundle;
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
		// CheckBoxPreference languagePref = (CheckBoxPreference)findPreference( "pref_app" );
		// languagePref.setSummary( languagePref.getEntry() );
	}
}
