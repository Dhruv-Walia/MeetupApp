package com.appconcur.meetupapp.webservices;

import java.util.ArrayList;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

public class FindKeyValueDatabase extends AsyncTask<string, Void, Boolean> 
{ 
	Context context;
	private final ProgressDialog dialog; 
	private final String DbName,CollectionName,Key,Value;
	boolean val;
	
	public FindKeyValueDatabase(Activity activity,String dbName,String collectionName,String key,String value) {
		this.context = activity;
		this.dialog = new ProgressDialog(activity);
		this.DbName = dbName;
		this.CollectionName = collectionName;
		this.Key = key;
		this.Value = value;
	}
	
	protected void onPreExecute() {

    	this.dialog.setTitle("Finding Database");
        this.dialog.setMessage("Finding...");
        this.dialog.show();
    }

	@Override
	protected Boolean doInBackground(string... params) {
		//Build User Service  
        StorageService storageService = App42API.buildStorageService();
        storageService.findDocumentByKeyValue(DbName, CollectionName, Key, Value, new App42CallBack() {
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
    		        GetResult(true);
    		    }
    		}  
    		public void onException(Exception ex)  
    		{  
    		    System.out.println("Exception Message"+ex.getMessage());
		        GetResult(false);
     		}  
    		});
		return true;
	}
	
	public void GetResult(boolean id){
		this.val = id;
		Result();
	}
	
	public Boolean Result() {
		return val;
	}

	protected void onPostExecute(final Boolean success) {

		 if (this.dialog.isShowing()){
	            this.dialog.dismiss();
	     }  
		 if(success){
			 Result();
		 }
	}
}
