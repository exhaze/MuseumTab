package com.jes.museumtab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanQrCodeActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		IntentIntegrator.initiateScan(ScanQrCodeActivity.this);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, 
			int resultCode, Intent data) {

		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, data);
		
		   if (scanResult != null && scanResult.getContents() != null) {
			   String uuid = scanResult.getContents();
			   Intent intent = new Intent(this, ExhibitDisplay.class);
			   intent.putExtra(ExhibitsDbAdapter.KEY_EXHIBIT_UUID, uuid);
			   startActivity(intent);
		   }

		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}
	
}
