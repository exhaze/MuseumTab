package com.jes.museumtab;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class MuseumTabActivity extends ListActivity {
	
	private ExhibitsDbAdapter mDbHelper;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibit_list);
        
        mDbHelper = new ExhibitsDbAdapter(this);
        mDbHelper.open();
        fillData();
        
        // example code showing how to open file in private subdir
//        try {
//        	this.getContentResolver();
//        	File imagesDir = getDir("images", MODE_PRIVATE);
//        	
//	        FileOutputStream fos = new FileOutputStream(
//	        		imagesDir.getAbsolutePath() + File.separatorChar + "blah.jpg");
//	        
//	        byte[] buffer = new byte[100];
//	        buffer[0] = 'h';
//	        
//	        fos.write(buffer);
//	        fos.close();
//	        
//	        FileInputStream fis = new FileInputStream(
//	        		imagesDir.getAbsolutePath() + File.separatorChar + "blah.jpg");
//	        
//	        byte[] buffer2 = new byte[200];
//	        
//	        fis.read(buffer2);
//	        
//	        System.out.println(buffer2);
//	        
//        } catch (FileNotFoundException e) {
//        	e.printStackTrace();
//        } catch (IOException e) {
//        	e.printStackTrace();
//        }
    }
    
    private void fillData() {
    	Cursor exhibitsCursor = mDbHelper.fetchAllExhibits();
    	startManagingCursor(exhibitsCursor);
		
    	String[] from = new String[]{ExhibitsDbAdapter.KEY_EXHIBIT_NAME};
    	
    	int[] to = new int[]{R.id.exhibit_name};
		
    	SimpleCursorAdapter exhibits =
    			new SimpleCursorAdapter(this, R.layout.exhibit_row, 
    					exhibitsCursor, from, to);
    	
    	setListAdapter(exhibits);
	}

	@SuppressWarnings("unused")
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	   if (requestCode == 0) {
    	      if (resultCode == RESULT_OK) {
    	         String contents = intent.getStringExtra("SCAN_RESULT");
    	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
    	         // Handle successful scan
    	      } else if (resultCode == RESULT_CANCELED) {
    	         // Handle cancel
    	      }
    	   }
    	}
}