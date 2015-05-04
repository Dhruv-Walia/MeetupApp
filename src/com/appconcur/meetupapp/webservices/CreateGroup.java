package com.appconcur.meetupapp.webservices;

import java.util.ArrayList;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.buddy.Buddy;
import com.shephertz.app42.paas.sdk.android.buddy.BuddyService;

public class CreateGroup extends AsyncTask<string, Void, Boolean>
{
	Context context;
	private final ProgressDialog dialog; 
	private final String Username,Groupname;
	private boolean val;
	private ArrayList<String> data;
	 
	public CreateGroup(Activity activity,String username,String groupname) {
		this.context = activity;
		this.dialog = new ProgressDialog(activity);
		this.Username = username;
		this.Groupname = groupname;
		
	}

	protected void onPreExecute() {

    	this.dialog.setTitle("Creating Group By User");
        this.dialog.setMessage("Creating...");
        this.dialog.show();
    }

	@Override
	protected Boolean doInBackground(string... params) {

		//Build User Service  
        BuddyService buddyService = App42API.buildBuddyService();
        try {
        	data = new ArrayList<String>();
        	buddyService.createGroupByUser(Username, Groupname, new App42CallBack() {
        		public void onSuccess(Object response)   
        		{  
        		    Buddy  buddy  = (Buddy)response;  
        		    System.out.println("userName is : " + buddy.getUserName());   
        		    System.out.println("groupName is : " + buddy.getGroupName());
        		    data.add(buddy.getGroupName());
        		}  
        		public void onException(Exception ex)   
        		{  
        		    System.out.println("Exception Message"+ex.getMessage());      
        		}  
        		});   
        	return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	protected ArrayList<String> GroupData(){
		return data;
	}
       
	protected void onPostExecute(final Boolean success) {

		 if (this.dialog.isShowing()){
	            this.dialog.dismiss();
	     }
		 if(success){
			 Toast.makeText(context, "Rejecting successful!", Toast.LENGTH_SHORT).show();
		 }
		 else{
			 Toast.makeText(context, "Rejecting failed!", Toast.LENGTH_SHORT).show();
		 }
	        
	}

}
