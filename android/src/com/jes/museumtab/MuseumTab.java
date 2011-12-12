package com.jes.museumtab;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TabHost;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MuseumTab extends TabActivity  {
	
	private ExhibitsDbAdapter mDbHelper;
	
	private static final String MUSEUM_IMAGE = "museumImage";
	
	private Button mScanBtn;
	private Button mExhibitListBtn;
	
	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//		
//		mDbHelper = new ExhibitsDbAdapter(this);
//		mDbHelper.open();
//		
//		String museumImgPath = mDbHelper.getMiscValue(MUSEUM_IMAGE);
//		
//		if (!museumImgPath.isEmpty()) {
//			Bitmap bMap = BitmapFactory.decodeFile(museumImgPath);
//			ImageView museumView = (ImageView) findViewById(R.id.museum_image);
//			museumView.setImageBitmap(bMap);
//		}
//		
//		mScanBtn = (Button) findViewById(R.id.scan_btn);
//		mExhibitListBtn = (Button) findViewById(R.id.exhibit_list_btn);
//		
//		mScanBtn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				IntentIntegrator.initiateScan(MuseumTab.this);
//			}
//		});
//		
//		mExhibitListBtn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(MuseumTab.this, ExhibitList.class);
//				startActivity(intent);
//			}
//		});
		
		
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.main);

		    Resources res = getResources(); // Resource object to get Drawables
		    TabHost tabHost = getTabHost();  // The activity TabHost
		    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
		    Intent intent;  // Reusable Intent for each tab

		    // Create an Intent to launch an Activity for the tab (to be reused)
		    intent = new Intent().setClass(this, ExhibitList.class);

		    // Initialize a TabSpec for each tab and add it to the TabHost
		    spec = tabHost.newTabSpec("artists").setIndicator(getString(R.string.scan_btn_text),
		                      res.getDrawable(R.drawable.icon))
		                  .setContent(intent);
		    tabHost.addTab(spec);

		    // Do the same for the other tabs
		    intent = new Intent().setClass(this, ScanQrCodeActivity.class);
		    spec = tabHost.newTabSpec("albums").setIndicator(getString(R.string.current_exhibit),
		                      res.getDrawable(R.drawable.icon))
		                  .setContent(intent);
		    tabHost.addTab(spec);

		    intent = new Intent().setClass(this, ExhibitDisplay.class);
		    spec = tabHost.newTabSpec("songs").setIndicator(getString(R.string.goto_exhibit_list_btn),
		                      res.getDrawable(R.drawable.icon))
		                  .setContent(intent);
		    tabHost.addTab(spec);

		    tabHost.setCurrentTab(2);
		}
		

//	@Override
//	protected void onActivityResult(int requestCode, 
//			int resultCode, Intent data) {
//
//		IntentResult scanResult = IntentIntegrator.parseActivityResult(
//				requestCode, resultCode, data);
//		
//		   if (scanResult != null && scanResult.getContents() != null) {
//			   String uuid = scanResult.getContents();
//			   Intent intent = new Intent(this, ExhibitDisplay.class);
//			   intent.putExtra(ExhibitsDbAdapter.KEY_EXHIBIT_UUID, uuid);
//			   startActivity(intent);
//		   }
//
//		super.onActivityResult(requestCode, resultCode, data);
//	}
}
