package com.jes.museumtab;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.UUID;

public class ExhibitsDbAdapter {
	
	public static final String KEY_EXHIBIT_UUID = "uuid";
	public static final String KEY_EXHIBIT_NAME = "name";
	public static final String KEY_EXHIBIT_DESCRIPTION = "description";
	public static final String KEY_EXHIBIT_ROWID = "_id";
	
	public static final String KEY_IMAGE_PATH = "imagePath";
	public static final String KEY_IMAGE_EXHIBIT_BACKREF = "exhibitBackRef";
	public static final String KEY_IMAGE_ROWID = "_id";
	
	public static final String KEY_MISC_KEY = "key";
	public static final String KEY_MISC_VALUE = "value";
	public static final String KEY_MISC_ROWID = "_id";
		
	private static final String LOG_TAG = "ExhibitsDbAdapter";
	
	private static final String EXHIBIT_TABLE = "exhibits";
	private static final String IMAGE_TABLE = "images";
	private static final String MISC_TABLE = "misc";
	
	public static final int DATABASE_VERSION = 12;
	public static final String DATABASE_NAME = "data";
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	// SQL statement to create our database
	private static final String EXHIBIT_TABLE_CREATE =
			"create table " + EXHIBIT_TABLE
			+ "(" + KEY_EXHIBIT_ROWID + " integer primary key autoincrement, "
			+ KEY_EXHIBIT_UUID + " text not null unique," 
			+ KEY_EXHIBIT_NAME + " text not null, "
			+ KEY_EXHIBIT_DESCRIPTION + " text not null);";
	
	private static final String IMAGE_TABLE_CREATE =
			"create table " + IMAGE_TABLE
			+ "(" + KEY_EXHIBIT_ROWID + " integer primary key autoincrement, "
			+ KEY_IMAGE_PATH + " text not null unique, "
			+ KEY_IMAGE_EXHIBIT_BACKREF + " text not null unique);";
	
	private static final String MISC_TABLE_CREATE =
			"create table " + MISC_TABLE
			+ "(" + KEY_MISC_ROWID + " integer primary key autoincrement, "
			+ KEY_MISC_KEY + " text not null unique, "
			+ KEY_MISC_VALUE + " text not null);";
	
	private final Context mCtx;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, 
					null, DATABASE_VERSION);
		}
		
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(EXHIBIT_TABLE_CREATE);
			db.execSQL(IMAGE_TABLE_CREATE);
			db.execSQL(MISC_TABLE_CREATE);
		}
		
		public void onUpgrade(SQLiteDatabase db, 
				int oldVersion, int newVersion) {
			Log.w(LOG_TAG, "Upgrading database from version " + oldVersion 
					+ " to " + newVersion + 
					", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + EXHIBIT_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + IMAGE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + MISC_TABLE);
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
	
	public void createDefaults() {
		Cursor cursor = fetchAllExhibits();
		if (cursor.getCount() == 0) {
			createExhibit("Mona Lisa", "A painting by Leonardo Da Vinci");
		}
	}
	
	public long createExhibit(String name, String description) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_EXHIBIT_NAME, name);
		initialValues.put(KEY_EXHIBIT_DESCRIPTION, description);
		initialValues.put(KEY_EXHIBIT_UUID, UUID.randomUUID().toString());
		
		return mDb.insert(EXHIBIT_TABLE, null, initialValues);
	}
	
	public boolean deleteExhibit(long rowId) {
		Cursor cursor = fetchExhibit(rowId);
		
		String uuid = cursor.getString(
				cursor.getColumnIndexOrThrow(KEY_EXHIBIT_UUID));
		
		return mDb.delete(
				EXHIBIT_TABLE, KEY_EXHIBIT_ROWID + "=" + rowId, null) > 0 &&
				mDb.delete(IMAGE_TABLE, 
						KEY_IMAGE_EXHIBIT_BACKREF + sanitizedUuid(uuid), null) > 0;
	}
	
	public Cursor fetchAllExhibits () {
		return mDb.query(EXHIBIT_TABLE, new String[] {
				KEY_EXHIBIT_ROWID, KEY_EXHIBIT_UUID,
				KEY_EXHIBIT_NAME, KEY_EXHIBIT_DESCRIPTION}, 
				null, null, null, null, null );
	}
	
	public Cursor fetchExhibit(long rowId) throws SQLException {
		Cursor cursor =	
			mDb.query(true, EXHIBIT_TABLE, new String[] {
					KEY_EXHIBIT_ROWID,
					KEY_EXHIBIT_UUID, 
					KEY_EXHIBIT_NAME, 
					KEY_EXHIBIT_DESCRIPTION}, 
					KEY_EXHIBIT_ROWID + "=" + rowId, 
					null, null, null, null, null);
		
		if (cursor != null) {
			cursor.moveToFirst();
		}
		
		return cursor;
	}
	
	public Cursor fetchExhibit(String uuid) throws SQLException {
		Cursor cursor =
				mDb.query(true, EXHIBIT_TABLE, null,
						KEY_EXHIBIT_UUID + "=" + sanitizedUuid(uuid),
						null, null, null, null, null);
		
		if (cursor != null) {
			cursor.moveToFirst();
		}
		
		return cursor;
	}
	
	public boolean updateExhibit(long rowId, String name, String description) {
        ContentValues args = new ContentValues();
        args.put(KEY_EXHIBIT_NAME, name);
        args.put(KEY_EXHIBIT_DESCRIPTION, description);

        return mDb.update(EXHIBIT_TABLE, args, 
        		KEY_EXHIBIT_ROWID + "=" + rowId, null) > 0;
		
	}
	
	public long createImage(String imagePath, String exhibitBackref) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_IMAGE_PATH, imagePath);
		initialValues.put(KEY_IMAGE_EXHIBIT_BACKREF, exhibitBackref);
		
		return mDb.insert(IMAGE_TABLE, null, initialValues);
	}
	
	public boolean deleteImage(long rowId) {
		return mDb.delete(IMAGE_TABLE, KEY_IMAGE_ROWID + "=" + rowId, null) > 0;
	}
	
	public Cursor fetchImagesForExhibit(String uuid) throws SQLException {
		Cursor mCursor =
				mDb.query(true, IMAGE_TABLE, new String [] {
						KEY_IMAGE_ROWID,
						KEY_IMAGE_PATH},
						KEY_IMAGE_EXHIBIT_BACKREF + "=" + sanitizedUuid(uuid), 
						null, null, null, null, null);
		
		if (mCursor != null) mCursor.moveToFirst();
		
		return mCursor;
	}
	
	public String sanitizedUuid(String uuid) {
		return "\"" + uuid + "\"";
	}
	
	public String getMiscValue(String key) {
		Cursor cursor = 
				mDb.query(true, MISC_TABLE, new String[] {
						KEY_MISC_ROWID,
						KEY_MISC_VALUE}, 
						KEY_MISC_KEY + "=" + sanitizedUuid(key), 
						null, null, null, null, null);
		
		String result = new String();
		
		if (cursor.moveToFirst()) {
			result = cursor.getString(
					cursor.getColumnIndexOrThrow(KEY_MISC_VALUE));
		}
		
		cursor.close();
		
		return result;
	}
	
	public void setMiscValue(String key, String value) {
		if (key.isEmpty() || value.isEmpty()) return;
		
		ContentValues values = new ContentValues();
		values.put(KEY_MISC_KEY, key);
		values.put(KEY_MISC_VALUE, value);
		
		mDb.insert(MISC_TABLE, null, values);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// todo: add retrieval of exhibit based on QR code
	
	// TODO: decide how to abstract storage of images, some options
	// option 1). make Exhibit a base class with android app/server having their 
	// 			  own implementations
	// option 2). wrapper around image class, abstracting away app/server 
	//			  storage specifics

}
