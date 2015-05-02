package com.appconcur.meetupapp.webservices;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.appconcur.meetupapp.MainActivity;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Response;
import com.shephertz.app42.paas.sdk.android.user.UserService;

public class SignOutShepHz extends AsyncTask<string, Void, Boolean>
{
	Context context;
	private final ProgressDialog dialog; 
	private final String Session;
	private boolean id;
	 
	public SignOutShepHz(Activity activity,String session) {
		this.context = activity;
		this.dialog = new ProgressDialog(activity);
		this.Session = session;
	}

	protected void onPreExecute() {

    	this.dialog.setTitle("Logging Out to Meetup");
        this.dialog.setMessage("Logging...");
        this.dialog.show();
    }

	@Override
	protected Boolean doInBackground(string... params) {

		//Build User Service  
        UserService userService = App42API.buildUserService(); 
        try {
        	userService.logout(Session,new App42CallBack() {  
			    public void onSuccess(Object response)  
			    {  
			        App42Response app42response = (App42Response)response;        
			        System.out.println("response is " + app42response) ;
			        Intent in = new Intent(context, MainActivity.class);
			        context.startActivity(in);
			    }  
			    public void onException(Exception ex)  
			    {  
			        System.out.println("Exception Message " + ex.getMessage()); 
			        
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
	        if (success){
	            Toast.makeText(context, "Logout successful!", Toast.LENGTH_SHORT).show();
	        }
	        else {
	            Toast.makeText(context, "Logout failed!", Toast.LENGTH_SHORT).show();
	        }
	      
	  }

}
