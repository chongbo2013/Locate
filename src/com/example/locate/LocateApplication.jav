package com.example.locate;


import android.app.Application;
import android.content.Context;


/**
 * Context could be easily accessible after use our own Application
 */
public class LocateApplication extends Application
{
	
	private static Context context;
	
	public void onCreate()
	{
		super.onCreate();
		context = getApplicationContext();
	}
	
	/**
	 * Simply use LocateApplication.getContext() to access the context
	 * 
	 * @return Context
	 */
	public static Context getContext()
	{
		return context;
	}
}
