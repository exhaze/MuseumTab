package com.jes.museumtab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ScanQrActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
	    intent.setPackage("com.google.zxing.client.android");
	    startActivityForResult(intent, 0);
	}
}
