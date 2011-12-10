package com.jes.museumtab;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExhibitDisplay extends Activity {
	private ExhibitsDbAdapter mDbHelper;
	private Long mRowId;
	private TextView mExhibitName;
	private TextView mExhibitDescription;
	private ImageView mExhibitImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mDbHelper = new ExhibitsDbAdapter(this);
        mDbHelper.open();
        
        setContentView(R.layout.exhibit);
        
        mExhibitName = (TextView) findViewById(R.id.exhibit_name);
        mExhibitDescription = (TextView) findViewById(R.id.exhibit_desc);
        mExhibitImage = (ImageView) findViewById(R.id.exhibit_image);
        
        Button backButton = (Button) findViewById(R.id.back_to_exhibit_list);
        
        mRowId = (savedInstanceState == null) ? null :
        	(Long) savedInstanceState.getSerializable(
        			ExhibitsDbAdapter.KEY_EXHIBIT_ROWID);
       
        if (mRowId == null) {
        	Bundle extras = getIntent().getExtras();
        	mRowId = extras != null ? 
        			extras.getLong(ExhibitsDbAdapter.KEY_EXHIBIT_ROWID) : null;
        }
        
        populateFields();
        
        backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}

	private void populateFields() {
		if (mRowId != null) {
			Cursor exhibit = mDbHelper.fetchExhibit(mRowId);
			startManagingCursor(exhibit);
			
			int nameCol = exhibit.getColumnIndexOrThrow(
					ExhibitsDbAdapter.KEY_EXHIBIT_NAME);
			
			int descCol = exhibit.getColumnIndexOrThrow(
					ExhibitsDbAdapter.KEY_EXHIBIT_DESCRIPTION);
			
			mExhibitName.setText(exhibit.getString(nameCol));
			mExhibitDescription.setText(exhibit.getString(descCol));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(ExhibitsDbAdapter.KEY_EXHIBIT_ROWID, mRowId);
	}
}
