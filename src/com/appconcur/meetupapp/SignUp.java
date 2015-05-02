package com.appconcur.meetupapp;

import com.appconcur.meetupapp.webservices.SignUpShepHz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity implements OnClickListener{
	
	private Button reg;
	private EditText username,email,password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		username = (EditText) findViewById(R.id.username_up);
		password = (EditText) findViewById(R.id.password_up);
		email = (EditText) findViewById(R.id.email_up);
		reg = (Button) findViewById(R.id.register);
		
		reg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:
			String Username = username.getText().toString();
			String Password = password.getText().toString();
			String Email = email.getText().toString();
		
		    if(username == null || password == null || email == null){
		    	Toast.makeText(this, "Enter all Fields", Toast.LENGTH_LONG).show();
		    }else if(username == null && password == null && email == null){
		    	Toast.makeText(this, "Enter all Fields", Toast.LENGTH_LONG).show();
		    }else{
		    	SignUpShepHz sz = new SignUpShepHz(this, Username,Email, Password);
			    sz.execute();
		    }
			break;

		default:
			break;
		}
		
	}
}
