package com.jes.museumtab;
import android.app.Activity;
import android.os.Bundle;

public class MuseumTab extends Activity {
	
	private ExhibitsDbAdapter mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mDbHelper = new ExhibitsDbAdapter(this);
		mDbHelper.open();
	}
}
