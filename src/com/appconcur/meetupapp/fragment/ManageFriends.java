package com.appconcur.meetupapp.fragment;

import com.appconcur.meetupapp.R;
import com.appconcur.meetupapp.ui.FindFriend;
import com.appconcur.meetupapp.ui.FriendRequest;
import com.appconcur.meetupapp.ui.MyFriends;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class ManageFriends  extends Fragment{
	
	Context mcontext;
	TabHost tabHost;
	TabHost.TabSpec tab1,tab2,tab3; 
	
	public ManageFriends(Context context){
		this.mcontext = context;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.manage_friends, container, false);
        // create the TabHost that will contain the Tabs
        tabHost = (TabHost)rootView.findViewById(android.R.id.tabhost);
        tabHost.setup();
        tab1 = tabHost.newTabSpec("First Tab");
        tab2 = tabHost.newTabSpec("Second Tab");
        tab3 = tabHost.newTabSpec("Third tab");

       // Set the Tab name and Activity
       // that will be opened when particular Tab will be selected
        tab1.setIndicator("My Friends");
        tab1.setContent(new Intent(mcontext,MyFriends.class));
       
        tab2.setIndicator("Friend Requests");
        tab2.setContent(new Intent(mcontext,FriendRequest.class));

        tab3.setIndicator("Find Friends");
        tab3.setContent(new Intent(mcontext,FindFriend.class));
       
        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        
        return rootView;
    }

}
