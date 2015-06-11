package com.example.locate.network;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/**
 * Since our application makes constant use of the network, we should set up our own RequestQueue
 */
public class LocateRequestQueue
{

    private static LocateRequestQueue mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private LocateRequestQueue(
            Context context )
    {
        mCtx = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader( mRequestQueue , new ImageLoader.ImageCache() {

            private final LruCache<String , Bitmap> cache = new LruCache<String , Bitmap>( 20 );

            @Override
            public Bitmap getBitmap(
                    String url )
            {
                return cache.get( url );
            }

            @Override
            public void putBitmap(
                    String url ,
                    Bitmap bitmap )
            {
                cache.put( url , bitmap );
            }
        } );
    }

    public static synchronized LocateRequestQueue getInstance(
            Context context )
    {
        if( mInstance == null )
        {
            mInstance = new LocateRequestQueue( context );
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if( mRequestQueue == null )
        {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue( mCtx.getApplicationContext() );
        }
        return mRequestQueue;
    }

    public <T>void addToRequestQueue(
            Request<T> req )
    {
        getRequestQueue().add( req );
    }

    public ImageLoader getImageLoader()
    {
        return mImageLoader;
    }
}
