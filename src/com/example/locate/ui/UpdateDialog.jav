package com.example.locate.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


/**
 * After check for upgrade, if new version is found, the dialog will be shown
 */
public class UpdateDialog extends DialogFragment implements DialogInterface.OnClickListener
{
	
	// if we have new version there must be a download url
	private String downloadUrl;
	
	/**
	 * When we just create the dialog
	 */
	@Override
	public Dialog onCreateDialog(
			Bundle savedInstanceState )
	{
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
		builder.setMessage( "Found New Version" ).setPositiveButton( "Download" , this ).setNegativeButton( "Cancel" , this );
		// Create the AlertDialog object and return it
		return builder.create();
	}
	
	/**
	 * User click button in the dialog whether position or negative
	 */
	@Override
	public void onClick(
			DialogInterface dialog ,
			int which )
	{
		switch( which )
		{
		// When user choose to download the new version
			case -1:
				Uri uri = Uri.parse( downloadUrl );
				Intent browserIntent = new Intent( Intent.ACTION_VIEW , uri );
				startActivity( browserIntent );
				break;
		}
	}
	
	/**
	 * Called by director to set download url
	 * 
	 * @param url
	 */
	public void setDownloadUrl(
			String url )
	{
		this.downloadUrl = url;
	}
}
