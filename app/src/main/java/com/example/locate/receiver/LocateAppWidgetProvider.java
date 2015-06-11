package com.example.locate.receiver;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.locate.R;
import com.example.locate.view.MainActivity;


/**
 * Implementation of App Widget functionality.
 */
public class LocateAppWidgetProvider extends AppWidgetProvider
{

    @Override
    public void onUpdate(
            Context context ,
            AppWidgetManager appWidgetManager ,
            int[] appWidgetIds )
    {
        final int N = appWidgetIds.length;
        // Perform this loop procedure for each App Widget that belongs to this provider
        for( int i = 0 ; i < N ; i++ )
        {
            int appWidgetId = appWidgetIds[i];
            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent( context , MainActivity.class );
            PendingIntent pendingIntent = PendingIntent.getActivity( context , 0 , intent , 0 );
            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews( context.getPackageName() , R.layout.locate_app_widget );
            views.setOnClickPendingIntent( R.id.appWidgetButton , pendingIntent );
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget( appWidgetId , views );
        }
        super.onUpdate( context , appWidgetManager , appWidgetIds );
    }

    /**
     * Receive broadcast to update the widget
     *
     * @param context
     * @param intent
     */
    public void onReceive(
            Context context ,
            Intent intent )
    {
        super.onReceive( context , intent );
    }

    @Override
    public void onEnabled(
            Context context )
    {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(
            Context context )
    {
        // Enter relevant functionality for when the last widget is disabled
    }
}
