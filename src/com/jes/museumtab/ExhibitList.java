package com.jes.museumtab;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ExhibitList extends ListActivity {
	
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_DISPLAY = 1;
	
	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;
	
	private ExhibitsDbAdapter mDbHelper;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibit_list);
        
        mDbHelper = new ExhibitsDbAdapter(this);
        mDbHelper.open();
        mDbHelper.createDefaults();
        fillData();
        
        registerForContextMenu(getListView());
        
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
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.menu_insert);
		return true;
	}

    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	}
    
    @Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
    	case DELETE_ID:
    		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	        mDbHelper.deleteExhibit(info.id);
	        fillData();
	        return true;
		}
		return super.onContextItemSelected(item);
	}
    
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, ExhibitDisplay.class);
		i.putExtra(ExhibitsDbAdapter.KEY_EXHIBIT_ROWID, id);
		startActivityForResult(i, ACTIVITY_DISPLAY);
	}

	@SuppressWarnings("unused")
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
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