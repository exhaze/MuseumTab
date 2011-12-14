package com.jes.museumtab;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class BackendSyncer extends Activity {
	private String html = "";
	private Handler mHandler;
	private ExhibitsDbAdapter mDbHelper;
	
	private static String UPDATE_URL = "http://museumtab.appspot.com/update/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
				URL updateURL = new URL(UPDATE_URL + mDbHelper.getMiscValue(MuseumTab.APP_UNIQUE_ID));
				URLConnection conn = updateURL.openConnection();
				BufferedInputStream is = new BufferedInputStream(conn.getInputStream());
				ByteArrayBuffer baf = new ByteArrayBuffer(50);
				
				int current = 0;
				while ((current = is.read()) != -1) {
					baf.append((byte)current);
				}
				
				html = new String(baf.toByteArray());
				mHandler.post(showUpdate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	private Runnable showUpdate = new Runnable() {
		@Override
		public void run() {
			Toast.makeText(BackendSyncer.this, "HTML code: " + html, Toast.LENGTH_SHORT).show();
		}
	};
	
}
