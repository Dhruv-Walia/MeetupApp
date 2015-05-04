package com.appconcur.meetupapp.fragment;

import com.appconcur.meetupapp.R;
import com.appconcur.meetupapp.ui.FindFriend;
import com.appconcur.meetupapp.ui.FriendRequest;
import com.appconcur.meetupapp.ui.MyFriends;
import com.appconcur.meetupapp.utils.TabManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class ManageFriends  extends Fragment{
	
	Context mContext;
	TabHost tabHost;
	TabHost.TabSpec tab1,tab2,tab3; 
	TabManager mTabManager;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mContext = (Context)activity;
	}

    @SuppressLint("NewApi") @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabManager = new TabManager(getActivity(), getFragmentManager(),
                android.R.id.tabcontent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.manage_friends, container, false);
        TabHost host = mTabManager.handleCreateView(v);

        mTabManager.addTab(host.newTabSpec("friends").setIndicator("My Friends"),
                MyFriends.class, null);
        mTabManager.addTab(host.newTabSpec("request").setIndicator("Friend Requests"),
                FriendRequest.class, null);
        mTabManager.addTab(host.newTabSpec("find").setIndicator("Find Friends"),
                FindFriend.class, null);

        return v;
    }

    @SuppressLint("NewApi") @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mTabManager.handleViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabManager.handleDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mTabManager.handleSaveInstanceState(outState);
    }

}
