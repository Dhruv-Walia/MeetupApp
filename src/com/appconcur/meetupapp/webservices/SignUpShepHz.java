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
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.UserService;

public class SignUpShepHz extends AsyncTask<string, Void, Boolean>
{
	Context context;
	private final ProgressDialog dialog; 
	private final String Username,Password,Email;
	private boolean id;
	 
	public SignUpShepHz(Activity activity,String username,String email,String password) {
		this.context = activity;
		this.dialog = new ProgressDialog(activity);
		this.Username = username;
		this.Password = password;
		this.Email = email;
	}

	protected void onPreExecute() {

    	this.dialog.setTitle("Signup to Meetup");
        this.dialog.setMessage("Registering...");
        this.dialog.show();
    }

	@Override
	protected Boolean doInBackground(string... params) {

		//Build User Service  
        UserService userService = App42API.buildUserService(); 
        try {
        	userService.createUser(Username, Password, Email, new App42CallBack() {  
    	        public void onSuccess(Object response)   
    	        {  
    	            User user = (User)response; 
    	            System.out.println("userName is " + user.getUserName());  
    	            System.out.println("emailId is " + user.getEmail()); 
    	            Intent in = new Intent(context, MainActivity.class);
    		        context.startActivity(in);
    		        id = true;
    	        }  
    	        public void onException(Exception ex)   
    	        {  
    	            System.out.println("Exception Message"+ex.getMessage()); 
    	            id = false;
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
	            Toast.makeText(context, "Registeration successful!", Toast.LENGTH_SHORT).show();
	        }
	        else {
	            Toast.makeText(context, "Registration failed!", Toast.LENGTH_SHORT).show();
	        }
	      
	  }

}
