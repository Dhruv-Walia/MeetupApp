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
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

public class UpdateKeyValueDatabase extends AsyncTask<string, Void, Boolean>
{
	Context context;
	private final ProgressDialog dialog; 
	private final String dbName,collectionName,key,value,jsonDoc;
	private boolean val; 
	 
	public UpdateKeyValueDatabase(Activity activity,String dbname,String collectionname,String Key,String Value,String jsondoc ) {
		this.context = activity;
		this.dialog = new ProgressDialog(activity);
        this.dbName = dbname;
        this.collectionName = collectionname;
        this.key = Key;
        this.value = Value;
        this.jsonDoc = jsondoc;
	}

	protected void onPreExecute() {

    	this.dialog.setTitle("Updating DataBase");
        this.dialog.setMessage("Updating...");
        this.dialog.show();
    }

	@Override
	protected Boolean doInBackground(string... params) {

		//Build User Service  
        StorageService storageService = App42API.buildStorageService();
        try {
        	storageService.saveOrUpdateDocumentByKeyValue(dbName, collectionName, key, value, jsonDoc, new App42CallBack() {  
        		public void onSuccess(Object response)   
        		{  
        		    Storage  storage  = (Storage )response;  
        		    System.out.println("dbName is " + storage.getDbName());  
        		    System.out.println("collection Name is " + storage.getCollectionName());  
        		    ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();              
        		    for(int i=0;i<jsonDocList.size();i++)  
        		    {  
        		        System.out.println("objectId is " + jsonDocList.get(i).getDocId());    
        		        System.out.println("CreatedAt is " + jsonDocList.get(i).getCreatedAt());    
        		        System.out.println("UpdatedAtis " + jsonDocList.get(i).getUpdatedAt());    
        		        System.out.println("Jsondoc is " + jsonDocList.get(i).getJsonDoc());    
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
       
	protected void onPostExecute(final Boolean success) {

		 if (this.dialog.isShowing()){
	            this.dialog.dismiss();
	     }
		 if(success){
			 Toast.makeText(context, "Updating successful!", Toast.LENGTH_SHORT).show();
		 }
		 else{
			 Toast.makeText(context, "Updating failed!", Toast.LENGTH_SHORT).show();
		 }
	        
	}

}
