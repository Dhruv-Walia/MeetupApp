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


public class GettingFriends extends AsyncTask<string, Void, Boolean>
{
	Context context;
	private final ProgressDialog dialog; 
	private final String Username;
	private ArrayList<String> data;
	private boolean val; 
	 
	public GettingFriends(Activity activity,String username) {
		this.context = activity;
		this.dialog = new ProgressDialog(activity);
		this.Username = username;
	}

	protected void onPreExecute() {

    	this.dialog.setTitle("Getting Friends");
        this.dialog.setMessage("Getting...");
        this.dialog.show();
    }

	@Override
	protected Boolean doInBackground(string... params) {

		//Build User Service  
        BuddyService buddyService = App42API.buildBuddyService();
        try {
        	buddyService.getAllFriends(Username, new App42CallBack() {  
    	    	public void onSuccess(Object response) {  
    	    	    @SuppressWarnings("unchecked")
    				ArrayList<Buddy>  buddy  = (ArrayList<Buddy> )response;  
    	    	    data = new ArrayList<String>();
    	    	    for(int i=0;i<buddy.size();i++)  
    	    	    {  
    	    	    	data.add(buddy.get(i).getBuddyName());
    	    	        System.out.println("userName is : " + buddy.get(i).getUserName());   
    	    	        System.out.println("acceptedOn is : " + buddy.get(i).getAcceptedOn());   
    	    	        System.out.println("buddyName is : " + buddy.get(i).getBuddyName());   
    	    	        System.out.println("message is : " + buddy.get(i).getMessage());   
    	    	        System.out.println("sendedOn is : " + buddy.get(i).getSendedOn());  
    	    	    }  
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
	
	protected ArrayList<String> GetData(){
		return data;
	}
       
	protected void onPostExecute(final Boolean success) {

		 if (this.dialog.isShowing()){
	            this.dialog.dismiss();
	     }
		 if(success){
			 Toast.makeText(context, "Getting successful!", Toast.LENGTH_SHORT).show();
		 }
		 else{
			 Toast.makeText(context, "Getting failed!", Toast.LENGTH_SHORT).show();
		 }
	        
	}

}
