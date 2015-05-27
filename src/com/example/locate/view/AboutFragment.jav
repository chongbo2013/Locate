package com.example.locate.view;


import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.locate.LocateApplication;
import com.example.locate.R;
import com.example.locate.network.CommonRequest;


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
		TextView check_for_update = (TextView)v.findViewById( R.id.check_for_update_text_view );
		check_for_update.setOnClickListener( this );
		TextView title = (TextView)v.findViewById( R.id.version_name_text_view );
		String app_name = getResources().getString( R.string.app_name );
		PackageManager pm = LocateApplication.getContext().getPackageManager();
		PackageInfo pi;
		String version_name = "";
		try
		{
			pi = pm.getPackageInfo( LocateApplication.getContext().getPackageName() , 0 );
			version_name = pi.versionName;
		}
		catch( NameNotFoundException e )
		{
			e.printStackTrace();
		}
		StringBuilder builder = new StringBuilder();
		builder.append( app_name ).append( " " ).append( version_name );
		title.setText( builder.toString() );
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
			case R.id.check_for_update_text_view:
				new CommonRequest( getActivity() ).checkForUpdate();
				break;
		}
	}
}
