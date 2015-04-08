package com.example.locate.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class UpdateDialog extends DialogFragment
{
	
	@Override
	public Dialog onCreateDialog(
			Bundle savedInstanceState )
	{
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
		builder.setMessage( "Found New Version" ).setPositiveButton( "Download" , new DialogInterface.OnClickListener() {
			
			public void onClick(
					DialogInterface dialog ,
					int id )
			{
				( (MainActivity)getActivity() ).doPositiveClick();
			}
		} ).setNegativeButton( "Cancel" , new DialogInterface.OnClickListener() {
			
			public void onClick(
					DialogInterface dialog ,
					int id )
			{
				// User cancelled the dialog
			}
		} );
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
