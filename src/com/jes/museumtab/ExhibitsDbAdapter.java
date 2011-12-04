package com.jes.museumtab;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExhibitsDbAdapter {
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_IMAGE_PATH = "image_path";
	public static final String KEY_ROWID = "_id";
	
	private static final String LOG_TAG = "ExhibitsDbAdapter";
	
	// SQL statement to create our database
	private static final String DATABASE_CREATE =
			"create table exhibits (_id integer primary key autoincrement, "
			+ "name text not null, description text not null, image_path text);";
	
	private static final String DATABASE_NAME = "data";
	private static final String DATABASE_TABLE = "exhibits";
	private static final int DATABASE_VERSION = 1;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(LOG_TAG, "Upgrading database from version " + oldVersion 
					+ " to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS exhibits");
			onCreate(db);
		}
	}
	
	
	private int mId;
	private String mName;
	private String mDescription;
	private String mImagePath;
	
	// how do we pull an image in? from a file?
	public ExhibitsDbAdapter(int id, String name, String desc, String imagePath) {
		mId = id;
		mName = name;
		mDescription = desc;
		mImagePath = imagePath;
	}
	
	// TODO: decide how to abstract storage of images, some options
	// option 1). make Exhibit a base class with android app/server having their 
	// 			  own implementations
	// option 2). wrapper around image class, abstracting away app/server 
	//			  storage specifics

}
