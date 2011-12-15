package com.jes.museumtab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class BackendSyncer extends Activity {
	private String updateResult = "";
	private Handler mHandler;
	private ExhibitsDbAdapter mDbHelper;
	
	private static String UPDATE_URL = "http://museumtab.appspot.com/update";
//	private static String UPDATE_URL = "http://192.168.1.139:8888/update";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new Handler();
		mDbHelper = new ExhibitsDbAdapter(this);
		mDbHelper.open();
		checkForDatabaseUpdate.start();
	}

	private Thread checkForDatabaseUpdate = new Thread() {
		@Override
		public void run() {
			try {
				
				// TODO: perhaps in the future, when we start passing more parameters
				// to this get request, we can use this method of passing multiple parameters
				// to the POST request
//				String appUniqueId = mDbHelper.getMiscValue(MuseumTab.APP_UNIQUE_ID);
//				
//				List<NameValuePair> pairList = new ArrayList<NameValuePair>();
//				pairList.add(new BasicNameValuePair(UPDATE_KEY, appUniqueId));
//				UrlEncodedFormEntity url = 
//						new UrlEncodedFormEntity(pairList, HTTP.UTF_8);
				
				HttpClient client = new DefaultHttpClient();
				HttpPost updateRequest = new HttpPost();
				
				updateRequest.setURI(new URI(UPDATE_URL + "/?id=" + 
						mDbHelper.getMiscValue(MuseumTab.APP_UNIQUE_ID)));
				
				HttpResponse response = client.execute(updateRequest);
				
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));
	
				StringBuilder builder = new StringBuilder();
				String line;
				
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				
				reader.close();
				updateResult = builder.toString();
				
				// now, convert result from JSON string into our object
				Gson gson = new Gson();
				
				Type typeOfData = new TypeToken<List<ExhibitData>>(){}.getType();
				
				List<ExhibitData> exhibitList = 
						gson.fromJson(updateResult, typeOfData);
				
				mDbHelper.open();
				mDbHelper.clearExhibits();
				
				for (ExhibitData exhibit: exhibitList) {
					mDbHelper.createExhibit(
							exhibit.getUuid(),
							exhibit.getName(), 
							exhibit.getDescription());
				}
				
				mHandler.post(showUpdate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	private Runnable showUpdate = new Runnable() {
		@Override
		public void run() {
			Toast.makeText(BackendSyncer.this, "Update result: " + updateResult, Toast.LENGTH_SHORT).show();
		}
	};
	
}
