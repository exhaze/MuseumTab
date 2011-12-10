package com.jes.museumtab;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MuseumTab extends Activity {
	
	private ExhibitsDbAdapter mDbHelper;
	
	private static final String MUSEUM_IMAGE = "museumImage";
	
	private Button mScanBtn;
	private Button mExhibitListBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mDbHelper = new ExhibitsDbAdapter(this);
		mDbHelper.open();
		
		String museumImgPath = mDbHelper.getMiscValue(MUSEUM_IMAGE);
		
		if (!museumImgPath.isEmpty()) {
			Bitmap bMap = BitmapFactory.decodeFile(museumImgPath);
			ImageView museumView = (ImageView) findViewById(R.id.museum_image);
			museumView.setImageBitmap(bMap);
		}
		
		mScanBtn = (Button) findViewById(R.id.scan_btn);
		mExhibitListBtn = (Button) findViewById(R.id.exhibit_list_btn);
		
		mScanBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				IntentIntegrator.initiateScan(MuseumTab.this);
			}
		});
		
		mExhibitListBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MuseumTab.this, ExhibitList.class);
				startActivity(intent);
			}
		});
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
	}
}
