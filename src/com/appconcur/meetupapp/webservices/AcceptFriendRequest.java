package com.appconcur.meetupapp.webservices;

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

public class AcceptFriendRequest extends AsyncTask<string, Void, Boolean>
{
	Context context;
	private final ProgressDialog dialog; 
	private final String Username,Buddyname;
	private boolean val; 
	 
	public AcceptFriendRequest(Activity activity,String username,String buddyname) {
		this.context = activity;
		this.dialog = new ProgressDialog(activity);
		this.Username = username;
		this.Buddyname = buddyname;
	}

	protected void onPreExecute() {

    	this.dialog.setTitle("Accepting Friend Request");
        this.dialog.setMessage("Accepting...");
        this.dialog.show();
    }

	@Override
	protected Boolean doInBackground(string... params) {

		//Build User Service  
        BuddyService buddyService = App42API.buildBuddyService();
        try {
        	 buddyService.acceptFriendRequest(Username, Buddyname, new App42CallBack() {  
             	public void onSuccess(Object response)   
             	{  
             	    Buddy  buddy  = (Buddy )response;  
             	    System.out.println("userName is : " + buddy.getUserName());   
             	    System.out.println("buddyName is : " + buddy.getBuddyName());   
             	    System.out.println("sendedOn is : " + buddy.getSendedOn());  
             	    System.out.println("acceptedOn is : " + buddy.getAcceptedOn());  
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
       
	protected void onPostExecute(final Boolean success) {

		 if (this.dialog.isShowing()){
	            this.dialog.dismiss();
	     }
		 if(success){
			 Toast.makeText(context, "Accepting successful!", Toast.LENGTH_SHORT).show();
		 }
		 else{
			 Toast.makeText(context, "Accepting failed!", Toast.LENGTH_SHORT).show();
		 }
	        
	}


}
