package com.appconcur.meetupapp.facebook;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class FacebookLogActivity extends AsyncTask<string, Void, Boolean> {
	
	Context context;
	private final ProgressDialog dialog; 
	private CallbackManager callbackManager; 
	String TAG = this.getClass().getName();
	
	public FacebookLogActivity(Activity context) {
		this.context = context;
		this.dialog = new ProgressDialog(context);
	}
	
	protected void onPreExecute() {

    	this.dialog.setTitle("Signing to Facebook");
        this.dialog.setMessage("Signing...");
        this.dialog.show();
    }
	
	@Override
	protected Boolean doInBackground(string... params) {
		try {
			FacebookSdk.sdkInitialize(context);
	        
	        callbackManager = CallbackManager.Factory.create();

	        LoginManager.getInstance().registerCallback(callbackManager,
	                new FacebookCallback<LoginResult>() {
	                    @Override
	                    public void onSuccess(LoginResult loginResult) {
	                    	Log.v(TAG, "Access Token "+loginResult.getAccessToken());
		                   	Profile profile = Profile.getCurrentProfile();
		                   	if ( profile != null) {
	                            Toast.makeText(context,profile.getFirstName()+",you are logged", Toast.LENGTH_LONG).show();
	                        } else {
	                        	Toast.makeText(context,"Error in logging to Facebook Profile", Toast.LENGTH_LONG).show();
	                        }   
	                    }
	                    	

	                    @Override
	                    public void onCancel() {
	                    	Toast.makeText(context,"You are not logged", Toast.LENGTH_LONG).show();
	                    }

	                    @Override
	                    public void onError(FacebookException error) {
	                    	Log.v(TAG, "Facebook Login Error -"+error.toString());
	                    }
	                    
	                });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void sendRequestDialog() {
    	String appLinkUrl, previewImageUrl;

    	appLinkUrl = "https://www.mydomain.com/myapplink";
    	previewImageUrl = "https://www.mydomain.com/my_invite_image.jpg";

    	if (AppInviteDialog.canShow()) {
    	    AppInviteContent content = new AppInviteContent.Builder()
    	                .setApplinkUrl(appLinkUrl)
    	                .setPreviewImageUrl(previewImageUrl)
    	                .build();
    	    AppInviteDialog.show((Activity) context, content);
    	}
	}
 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
