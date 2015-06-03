package com.example.locate.model;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.example.locate.Locate;
import com.example.locate.LocateApplication;
import com.example.locate.util.PinyinUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * All the applications in your android device
 */
public class Application implements Searchable
{

    private static Application mApplication;
    private List<SearchResultInfo> mSearchResultInfoList = new ArrayList<SearchResultInfo>();
    private List<String> searchableStr = new ArrayList<String>();

    private Application()
    {
        if( Locate.DEBUG )
            Log.d( Locate.TAG , "Application initialize" );
        PackageManager pm = LocateApplication.getContext().getPackageManager();
        Intent i = new Intent( Intent.ACTION_MAIN );
        i.addCategory( Intent.CATEGORY_LAUNCHER );
        for( ResolveInfo info : pm.queryIntentActivities( i , 0 ) )
        {
            // Original name
            String original = String.valueOf( info.loadLabel( pm ) );
            // After convert the origin name to pinyin
            String pinyin = PinyinUtil.chinese2pinyin(original);
            String name = original + pinyin + PinyinUtil.getFirstLetter(pinyin);
            searchableStr.add( name );
            Drawable icon = info.loadIcon( pm );
            // start the application when being clicked
            Intent click = pm.getLaunchIntentForPackage( info.activityInfo.applicationInfo.packageName );
            // delete the application when being long clicked
            Intent longClick = new Intent( Intent.ACTION_DELETE );
            longClick.setData( Uri.fromParts( "package" , info.activityInfo.packageName , null ) );
            mSearchResultInfoList.add( new SearchResultInfo( original , icon , click , longClick ) );
        }
    }

    public static Application getInstance()
    {
        if( mApplication == null )
            mApplication = new Application();
        return mApplication;
    }

    @Override
    public List<SearchResultInfo> search(
            String str )
    {
        List<SearchResultInfo> appInfo = new ArrayList<SearchResultInfo>();
        for( String name : searchableStr )
        {
            if( name.toLowerCase().contains( str.toLowerCase() ) )
            {
                appInfo.add( mSearchResultInfoList.get( searchableStr.indexOf( name ) ) );
            }
        }
        return appInfo;
    }
}
