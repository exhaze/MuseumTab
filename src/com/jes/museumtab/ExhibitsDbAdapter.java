package com.jes.museumtab;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExhibitsDbAdapter {
	public static final String KEY_UNIQUEID = "uniqueId";
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_IMAGE_STRING = "image_string";
	public static final String KEY_ROWID = "_id";
	
	private static final String LOG_TAG = "ExhibitsDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	// SQL statement to create our database
	private static final String DATABASE_CREATE =
			"create table exhibits (_id integer primary key autoincrement, "
			+ "uniqueId text not null" 
			+ "name text not null, description text not null, image_path text);";
	
	private static final String DATABASE_NAME = "data";
	private static final String DATABASE_TABLE = "exhibits";
	private static final int DATABASE_VERSION = 1;
	
	private final Context mCtx;
	
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
		
	public ExhibitsDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}
	
    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
	public ExhibitsDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		mDbHelper.close();
	}
	
	public Cursor fetchAllExhibits () {
		return mDb.query(DATABASE_TABLE, new String[] {
				KEY_UNIQUEID, KEY_NAME, KEY_IMAGE_STRING}, 
				null, null, null, null, null );
	}
	
	public Cursor fetchExhibit(long rowId) throws SQLException {
		Cursor cursor =	
			mDb.query(true, DATABASE_TABLE, new String[] {
					KEY_UNIQUEID, KEY_NAME, KEY_IMAGE_STRING}, 
					KEY_ROWID + "=" + rowId, null, null, null, null, null);
		
		if (cursor != null) {
			cursor.moveToFirst();
		}
		
		return cursor;
	}
	
	// todo: add retrieval of exhibit based on QR code
	
	// TODO: decide how to abstract storage of images, some options
	// option 1). make Exhibit a base class with android app/server having their 
	// 			  own implementations
	// option 2). wrapper around image class, abstracting away app/server 
	//			  storage specifics

}
