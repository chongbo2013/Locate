package com.example.search;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;


public class MainActivity extends Activity
{

    private Context mContext;
    private GridView mGridView;
    private EditText mEditText;
    private TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(
                Editable s )
        {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(
                CharSequence s ,
                int arg1 ,
                int arg2 ,
                int arg3 )
        {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(
                CharSequence s ,
                int arg1 ,
                int arg2 ,
                int arg3 )
        {
            // Set the visibility of cancel icon according to whether empty
            ImageButton cancel = (ImageButton)findViewById( R.id.imageButton2 );
            int visibility = s.toString().equals( "" ) ? View.GONE : View.VISIBLE;
            cancel.setVisibility( visibility );
            // Do the searching
            SearchProxy proxy = SearchProxy.getInstance( mContext );
            List<ResolveInfo> list = proxy.search( String.valueOf( s ) );
            mGridView.setAdapter( new ImageAdapter( mContext , list ) );
        }
    };

    @Override
    protected void onCreate(
            Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Intent startIntent = new Intent( this , SearchService.class );
        startService( startIntent );
        mContext = this;
        mGridView = (GridView)findViewById( R.id.gridView );
        mEditText = (EditText)findViewById( R.id.editText );
        mEditText.addTextChangedListener( mTextWatcher );
    }

	@Override
	protected void onPause()
	{
		super.onPause();
		// hide the keyboard when the activity is paused, other the keyboard will appear when opening other app
		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService( Activity.INPUT_METHOD_SERVICE );
		inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken() , 0 );
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		// Clear the search content when the activity is paused
		mEditText.setText( "" );
	}

    /**
     * When user click the cancel icon, we should clear the search content
     *
     * @param view
     */
    public void clearSearchContent(
            View view )
    {
        mEditText.setText( "" );
    }
}
