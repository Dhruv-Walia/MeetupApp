package com.appconcur.meetupapp;

import com.appconcur.meetupapp.webservices.CreateFBGDatabase;
import com.appconcur.meetupapp.webservices.FindKeyValueDatabase;
import com.appconcur.meetupapp.webservices.SignInShepHz;
import com.appconcur.meetupapp.webservices.SignUpShepHz;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.shephertz.app42.paas.sdk.android.App42API;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {

	private Button signin,signup;
	private SignInButton google;
	private LoginButton facebook;
	private EditText username,password;
	
	// Facebook Variables
	private String FacebookProfileName,FacebookAccessToken ;
	
	//Google Variables
	private String GooglepersonName, GooglepersonPhotoUrl, GooglePlusProfile,Googleemail ;
	
	// Facebook object
	private CallbackManager callbackManager;
	// Google Plus Object
	private GoogleApiClient mGoogleApiClient;
	private boolean mIntentInProgress;
	private static final int RC_SIGN_IN = 0;	
	// tag for activity
	private String Tag = this.getClass().getName(); 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        
        setContentView(R.layout.main_activity);
        signin = (Button)findViewById(R.id.login);
        signup = (Button)findViewById(R.id.logup);
        facebook = (LoginButton)findViewById(R.id.f_sign);
        google = (SignInButton)findViewById(R.id.g_sign);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
         
        App42API.initialize(getApplicationContext(),"28d2cc0da1e7520a587a770bf7b513b782aa5f81965eb5c888a61f96fba0d281",
        		"9b5af9370c06102461fa779651735bed3810c405ebf06939694ee48ee3fdbd04");

        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
        facebook.setOnClickListener(this);
        google.setOnClickListener(this);
    }

    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			String Username = username.getText().toString();
			String Password = password.getText().toString();
		    if(username == null || password == null){
		    	Toast.makeText(this, "Enter all Fields", Toast.LENGTH_LONG).show();
		    }else if(username == null && password == null){
		    	Toast.makeText(this, "Enter all Fields", Toast.LENGTH_LONG).show();
		    }else{
		    	SignInShepHz sz = new SignInShepHz(this, Username, Password);
			    sz.execute();
		    }
			break;
		case R.id.logup:
			final Dialog dialog = new Dialog(this);
		    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		    dialog.setContentView(R.layout.register);
		    final Window window = dialog.getWindow();
		    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		    window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
		    final EditText username = (EditText)dialog.findViewById(R.id.username_up);
		    final EditText password = (EditText)dialog.findViewById(R.id.password_up);
		    final EditText email = (EditText)dialog.findViewById(R.id.email_up);
			Button reg = (Button)dialog.findViewById(R.id.register);
		    dialog.show();
		    reg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					String Username = username.getText().toString();
					String Password = password.getText().toString();
					String Email = email.getText().toString();
				
				    if(username == null || password == null || email == null){
				    	Toast.makeText(getApplicationContext(), "Enter all Fields", Toast.LENGTH_LONG).show();
				    }else{
				    	SignUpShepHz sz = new SignUpShepHz(MainActivity.this, Username,Email, Password);
					    sz.execute();
					    dialog.dismiss();
				    }
				}
			});
			break;

		case R.id.f_sign:
			facebook.setReadPermissions("user_friends"); 
			FacebookLogin();
			break;
		case R.id.g_sign:
			GoogleLogin();
			break;
		}
		
	}
    
    public void FacebookLogin(){

    	facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
    		@Override
    		public void onSuccess(LoginResult loginResult) {
            	Profile profile = Profile.getCurrentProfile();
               	AccessToken token = loginResult.getAccessToken();
               	FacebookProfileName = profile.getName();
               	FacebookAccessToken = token.getToken();
               	if ( profile != null) {
                    Toast.makeText(getApplicationContext(),
                    		profile.getName()+",you are logged with Access Token "+token.getToken(), Toast.LENGTH_LONG).show();
               	} 
               	
               	FindKeyValueDatabase fb = new FindKeyValueDatabase(MainActivity.this,"MEETUP","FacebookDatabase","user",FacebookProfileName);
        	    fb.execute();
        	    
        			if(fb.getStatus() == Status.FINISHED){
        				boolean res = fb.Result();
        			    Log.v(Tag, "Finding Database...."+res);
        			    if(res==true){
        			    	SignInShepHz si = new SignInShepHz(MainActivity.this, FacebookProfileName, "abc");
        		            si.execute();
        			    }
        			    else{
        			    	String json = "{\"user\":\""+FacebookProfileName+"\",\"email\":"+Googleemail+",\"password\":\"abc\",\"accesstoken\":"+FacebookAccessToken+"\"}"; 
        					new CreateFBGDatabase("MEETUP","GoogleDatabase", json);
        			    	
        		            SignUpShepHz su = new SignUpShepHz(MainActivity.this, FacebookProfileName, Googleemail, "abc");
        		            su.execute();
        		            SignInShepHz si = new SignInShepHz(MainActivity.this, FacebookProfileName, "abc");
        		            si.execute();
        			    }
        			}
               	
            }

            @Override
            public void onCancel() {
                 Toast.makeText(getApplicationContext(), "Facebook Cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                 Toast.makeText(getApplicationContext(), "Facebook Error:"+exception, Toast.LENGTH_LONG).show();   
            }
    		
    	});
        
	}
	
	public void GoogleLogin(){
		
		 mGoogleApiClient = new GoogleApiClient.Builder(this)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(Plus.API)
	        .addScope(Plus.SCOPE_PLUS_LOGIN)
	        .build();
		 
		mGoogleApiClient.connect();
	}
    
	@Override
    protected void onResume() {
        super.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onResume methods of the primary Activities that an app may be
        // launched into.
        AppEventsLogger.activateApp(this);
    }
	
	@Override
    public void onPause() {
        super.onPause();

        // Call the 'deactivateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onPause methods of the primary Activities that an app may be
        // launched into.
        AppEventsLogger.deactivateApp(this);
    }
	
	@Override
	public void onConnected(Bundle arg0) {
		// Get user's information
	    getProfileInformation();
	    FindKeyValueDatabase fb = new FindKeyValueDatabase(this,"MEETUP","GoogleDatabase","user",GooglepersonName);
	    fb.execute();
	    
			if(fb.getStatus() == Status.FINISHED){
				boolean res = fb.Result();
			    Log.v(Tag, "Finding Database...."+res);
			    if(res==true){
			    	SignInShepHz si = new SignInShepHz(this, GooglepersonName, "abc");
		            si.execute();
			    }
			    else{
			    	String json = "{\"user\":\""+GooglepersonName+"\",\"email\":"+Googleemail+",\"password\":\"abc\"}"; 
					new CreateFBGDatabase("MEETUP","GoogleDatabase", json);
			    	
		            SignUpShepHz su = new SignUpShepHz(this, GooglepersonName, Googleemail, "abc");
		            su.execute();
		            SignInShepHz si = new SignInShepHz(this, GooglepersonName, "abc");
		            si.execute();
			    }
			}
		
	    
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!mIntentInProgress && result.hasResolution()) {
		    try {
		      mIntentInProgress = true;
		      startIntentSenderForResult(result.getResolution().getIntentSender(),
		          RC_SIGN_IN, null, 0, 0, 0);
		    } catch (SendIntentException e) {
		      // The intent was canceled before it was sent.  Return to the default
		      // state and attempt to connect to get an updated ConnectionResult.
		      mIntentInProgress = false;
		      mGoogleApiClient.connect();
		    }
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}
	
	/*protected void onStop() {
	    super.onStop();
	    if (mGoogleApiClient.isConnected()) {
	      mGoogleApiClient.disconnect();
	    }
	}*/
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RC_SIGN_IN) {
		    mIntentInProgress = false;

		    if (!mGoogleApiClient.isConnecting()) {
		      mGoogleApiClient.connect();
		    }
		}
	}
	
	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				GooglepersonName = currentPerson.getDisplayName();
				GooglepersonPhotoUrl = currentPerson.getImage().getUrl();
			    GooglePlusProfile = currentPerson.getUrl();
				Googleemail = Plus.AccountApi.getAccountName(mGoogleApiClient);

				Log.e(Tag, "Name: " + GooglepersonName + ", plusProfile: "
						+ GooglePlusProfile + ", email: " + Googleemail
						+ ", Image: " + GooglepersonPhotoUrl);

				Toast.makeText(this, GooglepersonName+" is connected with email "+Googleemail, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
