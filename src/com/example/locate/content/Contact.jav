package com.example.locate.content;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;

import com.example.locate.Locate;
import com.example.locate.service.SearchService;
import com.example.locate.tools.Utils;


/**
 * All the contacts in your android device
 */
public class Contact implements Searchable
{
	
	private static Contact mContact;
	private List<SearchResultInfo> mSearchResultInfoList = new ArrayList<SearchResultInfo>();
	private List<String> searchableStr = new ArrayList<String>();
	
	private Contact()
	{
		if( Locate.DEBUG )
			Log.d( Locate.TAG , "Contact initialize" );
		ContentResolver cr = SearchService.mContext.getContentResolver();
		Cursor cur = cr.query( ContactsContract.Contacts.CONTENT_URI , null , null , null , null );
		if( cur.getCount() > 0 )
		{
			while( cur.moveToNext() )
			{
				String id = cur.getString( cur.getColumnIndex( ContactsContract.Contacts._ID ) );
				String name = cur.getString( cur.getColumnIndex( ContactsContract.Contacts.DISPLAY_NAME ) );
				if( Integer.parseInt( cur.getString( cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER ) ) ) > 0 )
				{
					Cursor pCur = cr.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI , null , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?" , new String[]{ id } , null );
					while( pCur.moveToNext() )
					{
						InputStream is = openPhoto( Long.valueOf( id ) );
						Bitmap photo = BitmapFactory.decodeStream( is );
						// when clicked get into the detail information of the contact
						Intent intent = new Intent( Intent.ACTION_VIEW );
						Uri uri = Uri.withAppendedPath( ContactsContract.Contacts.CONTENT_URI , String.valueOf( Long.valueOf( id ) ) );
						intent.setData( uri );
						if( is != null )
							mSearchResultInfoList.add( new SearchResultInfo( name , new BitmapDrawable( SearchService.mContext.getResources() , photo ) , intent ) );
						else
							mSearchResultInfoList.add( new SearchResultInfo( name , null , intent ) );
					}
					pCur.close();
				}
			}
		}
		for( SearchResultInfo info : mSearchResultInfoList )
		{
			String name = info.getTitle();
			String pinyin = Utils.chinese2pinyin( name );
			String pinyin_short = Utils.getFirstLetter( pinyin );
			String searchable_name = name + pinyin + pinyin_short;
			searchableStr.add( searchable_name );
		}
	}
	
	public static Contact getInstance()
	{
		if( mContact == null )
			mContact = new Contact();
		return mContact;
	}
	
	@Override
	public List<SearchResultInfo> search(
			String str )
	{
		List<SearchResultInfo> resultList = new ArrayList<SearchResultInfo>();
		for( String name : searchableStr )
		{
			if( name.toLowerCase().contains( str.toLowerCase() ) )
			{
				resultList.add( mSearchResultInfoList.get( searchableStr.indexOf( name ) ) );
			}
		}
		return resultList;
	}
	
	/**
	 * Load the contact photo
	 *
	 * @param contactId
	 * @return
	 */
	private InputStream openPhoto(
			long contactId )
	{
		Uri contactUri = ContentUris.withAppendedId( Contacts.CONTENT_URI , contactId );
		Uri photoUri = Uri.withAppendedPath( contactUri , Contacts.Photo.CONTENT_DIRECTORY );
		Cursor cursor = SearchService.mContext.getContentResolver().query( photoUri , new String[]{ Contacts.Photo.PHOTO } , null , null , null );
		if( cursor == null )
		{
			return null;
		}
		try
		{
			if( cursor.moveToFirst() )
			{
				byte[] data = cursor.getBlob( 0 );
				if( data != null )
				{
					return new ByteArrayInputStream( data );
				}
			}
		}
		finally
		{
			cursor.close();
		}
		return null;
	}
}
