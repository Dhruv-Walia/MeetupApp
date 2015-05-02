package com.appconcur.meetupapp;

import java.util.ArrayList;

import com.appconcur.meetupapp.adapter.NavDrawerListAdapter;
import com.appconcur.meetupapp.fragment.CreateGroup;
import com.appconcur.meetupapp.fragment.CreateParty;
import com.appconcur.meetupapp.fragment.Deals;
import com.appconcur.meetupapp.fragment.EditProfile;
import com.appconcur.meetupapp.fragment.MainFragment;
import com.appconcur.meetupapp.fragment.ManageFriends;
import com.appconcur.meetupapp.fragment.Rewards;
import com.appconcur.meetupapp.utils.NavDrawerItem;
import com.appconcur.meetupapp.webservices.SignOutShepHz;
import com.facebook.CallbackManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

public class Logged extends FragmentActivity{
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CallbackManager callbackManager;
	
	private String Username,SessionId;
	private boolean sess;
	
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logged);
		Username = getIntent().getStringExtra("user");
		SessionId = getIntent().getStringExtra("session");
		sess = true;
		
		final Dialog dialog = new Dialog(Logged.this);
        dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.login_dialogbox);
        ImageButton google = (ImageButton)dialog.findViewById(R.id.g_sign);
        ImageButton facebook = (ImageButton)dialog.findViewById(R.id.f_sign);
        dialog.show();
        
        facebook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

		 
			}
		});
        
        google.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

		mTitle = mDrawerTitle = getTitle();
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Username
		navDrawerItems.add(new NavDrawerItem(Username,R.drawable.ic_photos));
		// album list
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// artist list
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Exit
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// Exit
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// Exit
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		// Exit
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		// Exit
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));		
	
		
		// Recycle the typed array
		navMenuIcons.recycle();

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
		mDrawerList.setAdapter(adapter);
		
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		
		Fragment fragment = null;
		
		switch (position) {
		case 0:
			fragment = new MainFragment();
			break;
		case 1:
			fragment = new EditProfile(this);
			break;
		case 2:
			fragment = new CreateParty(this);
			break;
		case 3:
			fragment = new ManageFriends(this);
			break;
		case 4:
			fragment = new Rewards(this);
			break;
		case 5:
			fragment = new CreateGroup(this);
			break;
		case 6:
			fragment = new Deals(this);
			break;
		case 7:
		    SignOutShepHz so = new SignOutShepHz(this, SessionId);
		    so.execute();
			break;

		default:
			break;
		}

		if (fragment != null) {
					
     		    FragmentManager fragmentManager = getFragmentManager();
			    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

			    // update selected item and title, then close the drawer
			    mDrawerList.setItemChecked(position, true);
			    mDrawerList.setSelection(position);
			    setTitle(navMenuTitles[position]);
			    mDrawerLayout.closeDrawer(mDrawerList);
			
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	
	
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Intent in=new Intent();
		getApplicationContext().stopService(in);
	}
	
	@Override
	public void onBackPressed() {
		if(sess == true){
			//Alert dialog for accepting/rejecting friend request 
			AlertDialog.Builder builder1 = new AlertDialog.Builder(Logged.this);
            builder1.setMessage("You are Logged in:" +
            		"Do you really want to exit session ");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	SignOutShepHz so = new SignOutShepHz(Logged.this, SessionId);
        		    so.execute();
                    dialog.cancel();
                }
            });
            builder1.setNegativeButton("No",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {  
                    dialog.cancel();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();	
		}else{
			super.onBackPressed();
		}
	}

}
