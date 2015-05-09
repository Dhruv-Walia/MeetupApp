package com.appconcur.meetupapp.webservices;

import java.util.ArrayList;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;

public class CreateFBGDatabase
{
	
	public CreateFBGDatabase(String dbName,String collectionName,String json) {
		
		//Build User Service  
		StorageService storageService = App42API.buildStorageService();   
     	storageService.insertJSONDocument(dbName, collectionName, json,new App42CallBack() {  
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
	}

}
