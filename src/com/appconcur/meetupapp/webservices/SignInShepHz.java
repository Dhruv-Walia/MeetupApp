package com.appconcur.meetupapp.webservices;

import java.util.HashMap;

import com.appconcur.meetupapp.Logged;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.UserService;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class SignInShepHz extends AsyncTask<string, Void, Boolean>
{
	Context context;
	private final ProgressDialog dialog; 
	private final String Username,Password;
	private boolean val; 
	 
	public SignInShepHz(Activity activity,String username,String password) {
		this.context = activity;
		this.dialog = new ProgressDialog(activity);
		this.Username = username;
		this.Password = password;
	}

	protected void onPreExecute() {

    	this.dialog.setTitle("Signing to Meetup");
        this.dialog.setMessage("Signing...");
        this.dialog.show();
    }

	@Override
	protected Boolean doInBackground(string... params) {

		//Build User Service  
        UserService userService = App42API.buildUserService();  
        try {
        	HashMap<String, String> otherMetaHeaders = new HashMap<String, String>();  
    	    otherMetaHeaders.put("userProfile", "true");   
    	    userService.setOtherMetaHeaders(otherMetaHeaders);   
    	    userService.authenticate(Username , Password, new App42CallBack() {  
    	    public void onSuccess(Object response)  
    	    {  
    	        User user = (User)response;  
    	        System.out.println("userName is " + user.getUserName());    
    	        System.out.println("sessionId is " + user.getSessionId());
    	        Intent in = new Intent(context, Logged.class);
    	        in.putExtra("user", user.getUserName());
    	        in.putExtra("session", user.getSessionId());
    	        context.startActivity(in);
    	        new Runnable() {
					public void run() {
						val = true;
					}
				};
    	        
    	    }  
    	    public void onException(Exception ex)   
    	    {  
    	        System.out.println("Exception Message : "+ex.getMessage());
    	        new Runnable() {
					public void run() {
						val = false;
					}
				};
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
			 Toast.makeText(context, "Sign In successful!", Toast.LENGTH_SHORT).show();
		 }
		 else{
			 Toast.makeText(context, "Sign In failed!", Toast.LENGTH_SHORT).show();
		 }
	        
	}

}
