package com.example.locate.ui;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.locate.R;


public class AboutFragment extends Fragment implements View.OnClickListener
{
	
	public View onCreateView(
			LayoutInflater inflater ,
			ViewGroup container ,
			Bundle savedInstanceState )
	{
		// Inflate the layout for this fragment
		View v = inflater.inflate( R.layout.about , container , false );
		TextView open_project_website = (TextView)v.findViewById( R.id.project_website_text_view );
		open_project_website.setOnClickListener( this );
		TextView open_developer_website = (TextView)v.findViewById( R.id.developer_website_text_view );
		open_developer_website.setOnClickListener( this );
		return v;
	}
	
	@Override
	public void onClick(
			View v )
	{
		Intent i;
		int id = v.getId();
		switch( id )
		{
			case R.id.project_website_text_view:
				i = new Intent( Intent.ACTION_VIEW , Uri.parse( "http://locate.movier.me/" ) );
				startActivity( i );
				break;
			case R.id.developer_website_text_view:
				i = new Intent( Intent.ACTION_VIEW , Uri.parse( "http://movier.me/" ) );
				startActivity( i );
				break;
		}
	}
}
