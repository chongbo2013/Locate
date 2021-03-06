package com.example.locate.network;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings.Secure;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.locate.LocateApplication;
import com.example.locate.R;
import com.example.locate.view.SettingsActivity;
import com.example.locate.view.UpdateDialog;


/**
 * Common request such as check for update, user statistics and so on
 */
public class CommonRequest
{

    private RequestQueue mRequestQueue;
    private Activity mActivity;

    public CommonRequest(
            Activity activity )
    {
        mActivity = activity;
        mRequestQueue = LocateRequestQueue.getInstance( mActivity.getApplicationContext() ).getRequestQueue();
    }

    /**
     * Check whether there is a new version through web
     */
    public void checkForUpdate()
    {
        String url = "http://movier.me:3000/upgrade";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest( url , null , new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(
                    JSONObject response )
            {
                try
                {
                    int latest_version = response.getInt( "version" );
                    PackageManager pm = LocateApplication.getContext().getPackageManager();
                    PackageInfo pi = pm.getPackageInfo( LocateApplication.getContext().getPackageName() , 0 );
                    int current_version = pi.versionCode;
                    if( latest_version > current_version )
                    {
                        UpdateDialog newFragment = new UpdateDialog();
                        newFragment.setDownloadUrl( response.getString( "url" ) );
                        newFragment.show( mActivity.getFragmentManager() , "dialog" );
                    }
                    else
                    {
                        // if current is latest version then toast the user only when he check for update through about
                        // don't toast when in main activity
                        if( mActivity instanceof SettingsActivity )
                            Toast.makeText( mActivity , mActivity.getResources().getString( R.string.latest_version ) , Toast.LENGTH_SHORT ).show();
                    }
                }
                catch( JSONException | NameNotFoundException e )
                {
                }
            }
        } , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(
                    VolleyError error )
            {
                // TODO Auto-generated method stub
            }
        } );
        // Access the RequestQueue through your singleton class.
        mRequestQueue.add( jsObjRequest );
    }

    /**
     * Use volley to upload some information about the user
     */
    public void uploadUserInfo()
    {
        // these parameters will be stored as user statistics
        final String android_id = Secure.getString( mActivity.getContentResolver() , Secure.ANDROID_ID );
        //		String build_id = Build.ID;
        final String build_brand = Build.BRAND;
        //		String build_manufacturer = Build.MANUFACTURER;
        //		String build_model = Build.MODEL;
        //		String build_serial = Build.SERIAL;
        // Instantiate the RequestQueue.
        String url = "http://movier.me:3000/new";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest( Request.Method.POST , url , new Response.Listener<String>() {

            @Override
            public void onResponse(
                    String response )
            {
                // Display the first 500 characters of the response string.
                // Get a handle to a SharedPreferences
                SharedPreferences sharedPref = mActivity.getPreferences( Context.MODE_PRIVATE );
                // Write to Shared Preferences
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString( "id" , response );
                editor.apply();
            }
        } , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(
                    VolleyError error )
            {
            }
        } ) {

            @Override
            protected Map<String , String> getParams()
            {
                // set post fields here
                Map<String , String> map = new HashMap<String , String>();
                map.put( "android_id" , android_id );
                map.put( "build_brand" , build_brand );
                return map;
            }
        };
        // Add the request to the RequestQueue.
        mRequestQueue.add( stringRequest );
    }

    /**
     * Use volley to upload some action about the user
     */
    public void uploadUserAction(
            final String action )
    {
        // Get a handle to a SharedPreferences
        SharedPreferences sharedPref = mActivity.getPreferences( Context.MODE_PRIVATE );
        // Read from Shared Preferences
        final String id = sharedPref.getString( "id" , "" );
        if( !id.isEmpty() )
        {
            String url = "http://movier.me:3000/statistics";
            StringRequest stringRequest = new StringRequest( Request.Method.POST , url , new Response.Listener<String>() {

                @Override
                public void onResponse(
                        String response )
                {
                    // TODO
                }
            } , new Response.ErrorListener() {

                @Override
                public void onErrorResponse(
                        VolleyError error )
                {
                    // TODO
                }
            } ) {

                @Override
                protected Map<String , String> getParams()
                {
                    // set post fields here
                    Map<String , String> map = new HashMap<String , String>();
                    map.put( "id" , id );
                    map.put( "action" , action );
                    return map;
                }
            };
            // Add the request to the RequestQueue.
            mRequestQueue.add( stringRequest );
        }
        else
        {
            uploadUserInfo();
        }
    }
}
