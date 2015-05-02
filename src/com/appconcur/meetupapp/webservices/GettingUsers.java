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
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.UserService;

public class GettingUsers extends AsyncTask<string, Void, Boolean>
{
	Context context;
	private final ProgressDialog dialog; 
	private final String Username;
	private ArrayList<String> data;
	private boolean val; 
	 
	public GettingUsers(Activity activity,String username) {
		this.context = activity;
		this.dialog = new ProgressDialog(activity);
		this.Username = username;
	}

	protected void onPreExecute() {

    	this.dialog.setTitle("Getting Users");
        this.dialog.setMessage("Getting...");
        this.dialog.show();
    }

	@Override
	protected Boolean doInBackground(string... params) {

		//Build User Service  
        UserService userService = App42API.buildUserService();  
        try {
        	userService.getAllUsers(new App42CallBack() {  
    			public void onSuccess(Object response)   
    			{  
    			    @SuppressWarnings("unchecked")
    				ArrayList<User> user = (ArrayList<User>)response;
    			    data = new ArrayList<String>();
    			    for(int i = 0; i < user.size(); i++)     
    			    {    
    			        System.out.println("userName is " + user.get(i).getUserName());    
    			        System.out.println("emailId is " + user.get(i).getEmail());
    			        if(user.get(i).getUserName() == Username){
    			             data.remove(i);
    			        }else{
    			        	data.add(user.get(i).getUserName());
    			        }
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
