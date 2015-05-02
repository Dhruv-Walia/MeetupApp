package com.appconcur.meetupapp;

import com.appconcur.meetupapp.webservices.SignInShepHz;
import com.shephertz.app42.paas.sdk.android.App42API;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button signin,signup;
	private EditText username,password;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup = (Button)findViewById(R.id.signup);
        signin = (Button)findViewById(R.id.signin);
        username = (EditText)findViewById(R.id.username_in);
        password = (EditText)findViewById(R.id.password_in);
        
        App42API.initialize(getApplicationContext(),"28d2cc0da1e7520a587a770bf7b513b782aa5f81965eb5c888a61f96fba0d281",
        		"9b5af9370c06102461fa779651735bed3810c405ebf06939694ee48ee3fdbd04");
        
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signin:
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

		case R.id.signup:
			Intent in = new Intent(getApplicationContext(), SignUp.class);
			startActivity(in);
			break;
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
