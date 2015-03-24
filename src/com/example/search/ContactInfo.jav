package com.example.search;


import android.graphics.Bitmap;


/**
 * Description information of a contact
 * 
 */
public class ContactInfo
{
	
	private long id;
	private String name;
	private String phoneNo;
	private Bitmap photo;
	
	public ContactInfo(
			long id ,
			String name ,
			String phoneNo ,
			Bitmap photo )
	{
		this.id = id;
		this.name = name;
		this.phoneNo = phoneNo;
		this.photo = photo;
	}
	
	public long getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPhoneNo()
	{
		return phoneNo;
	}
	
	public Bitmap getPhoto()
	{
		return photo;
	}
}
