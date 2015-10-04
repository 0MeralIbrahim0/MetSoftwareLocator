package rest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	// Context of application who uses us.
	private final Context context;

	// For logging
	private static final String TAG = "DBAdapter";

	// //////////////
	// DATABASE INFO
	// //////////////

	// DB info: it's name, and the tables we are using (two tables).
	public static final String DATABASE_NAME = "MyDb";
	public static final String ROOMS_DATABASE_TABLE = "roomsTable";
	public static final String LISTS_DATABASE_TABLE = "listsTable";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 2;

	// //////////////
	// FIRST TABLE
	// //////////////

	// DB Fields
	public static final String KEY_ROOMSTABLE_ROWID = "_id";
	public static final int COL_ROOMSTABLE_ROWID = 0;

	// Setup the fields
	public static final String KEY_ROOM_NAME = "roomname";
	public static final String KEY_SOFTWARE = "software";
	public static final String KEY_DESCRIPTION = "description";

	// Setup the field numbers
	public static final int COL_ROOM_NAME = 1;
	public static final int COL_SOFTWARE = 2;
	public static final int COL_ROOM_DESCRIPTION = 3;

	// Array of all keys
	public static final String[] ALL_ROOMSTABLE_KEYS = new String[] {
			KEY_ROOMSTABLE_ROWID, KEY_ROOM_NAME, KEY_SOFTWARE, KEY_DESCRIPTION };

	// Create Table
	private static final String ROOMS_DATABASE_CREATE_SQL = "create table "
			+ ROOMS_DATABASE_TABLE + " (" + KEY_ROOMSTABLE_ROWID
			+ " integer primary key autoincrement, " + KEY_ROOM_NAME
			+ " string not null, " + KEY_SOFTWARE + " text not null, "
			+ KEY_DESCRIPTION + " text not null" + ");";

	// //////////////
	// SECOND TABLE
	// //////////////

	// DB Fields
	public static final String KEY_LISTSTABLE_ROWID = "_id";
	public static final int COL_LISTSTABLE_ROWID = 0;

	// Setup the fields
	public static final String KEY_LIST_NAME = "listname";
	public static final String KEY_ROOMS_LIST = "roomslist";

	// Setup the field numbers
	public static final int COL_LIST_NAME = 1;
	public static final int COL_ROOMS_LIST = 2;

	// Array of all keys
	public static final String[] ALL_LISTSTABLE_KEYS = new String[] {
			KEY_LISTSTABLE_ROWID, KEY_LIST_NAME, KEY_ROOMS_LIST };

	// Create Table
	public static final String LISTS_DATABASE_CREATE_SQL = "create table "
			+ LISTS_DATABASE_TABLE + " (" + KEY_LISTSTABLE_ROWID
			+ " integer primary key autoincrement, " + KEY_LIST_NAME
			+ " text not null, " + KEY_ROOMS_LIST + " text not null" + ");";

	// //////////////
	// PUBLIC METHODS
	// //////////////
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}

	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}

	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}

	// ///////////////////////
	// METHODS FOR ROOMS TABLE
	// ///////////////////////

	// Add a new set of values to the ROOMS TABLE.
	public long insertRowRoomsTable(String roomName, String software,
			String description) {
		// Create row's data:
		ContentValues roomsTableValues = new ContentValues();
		roomsTableValues.put(KEY_ROOM_NAME, roomName);
		roomsTableValues.put(KEY_SOFTWARE, software);
		roomsTableValues.put(KEY_DESCRIPTION, description);

		// Insert it into the database.
		return db.insert(ROOMS_DATABASE_TABLE, null, roomsTableValues);
	}

	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRowFromRooms(long rowId) {
		String where = KEY_ROOMSTABLE_ROWID + "=" + rowId;
		return db.delete(ROOMS_DATABASE_TABLE, where, null) != 0;
	}

	// Return all data in the database.
	public Cursor getAllRowsFromRooms() {
		String where = null;
		Cursor c = db.query(true, ROOMS_DATABASE_TABLE, ALL_ROOMSTABLE_KEYS,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRowFromRooms(long rowId) {
		String where = KEY_ROOMSTABLE_ROWID + "=" + rowId;
		Cursor c = db.query(true, ROOMS_DATABASE_TABLE, ALL_ROOMSTABLE_KEYS,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Change an existing row to be equal to new data.
	public boolean updateRowForRooms(long rowId, String roomName, int software,
			String description) {
		String where = KEY_ROOMSTABLE_ROWID + "=" + rowId;

		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_ROOM_NAME, roomName);
		newValues.put(KEY_SOFTWARE, software);
		newValues.put(KEY_DESCRIPTION, description);

		// Insert it into the database.
		return db.update(ROOMS_DATABASE_TABLE, newValues, where, null) != 0;
	}

	public Cursor getRoomsForSoftware(String softwareName) {
		String where = KEY_SOFTWARE + " LIKE '%" + softwareName + "%';";
		Cursor c = db.query(true, ROOMS_DATABASE_TABLE, ALL_ROOMSTABLE_KEYS,
				where, null, null, null, null, null);

		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getSoftwareForRooms(String roomName) {
		String where = KEY_ROOM_NAME + " LIKE '%" + roomName + "%';";
		Cursor c = db.query(true, ROOMS_DATABASE_TABLE, ALL_ROOMSTABLE_KEYS,
				where, null, null, null, null, null);

		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// ///////////////////////
	// METHODS FOR LISTS TABLE
	// ///////////////////////

	// Add a new set of values to the ROOMS TABLE.
	public long insertRowListsTable(String listName, String roomsList) {
		// Create row's data:
		ContentValues listsTableValues = new ContentValues();
		listsTableValues.put(KEY_LIST_NAME, listName);
		listsTableValues.put(KEY_ROOMS_LIST, roomsList);

		// Insert it into the database.
		return db.insert(LISTS_DATABASE_TABLE, null, listsTableValues);
	}

	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRowFromLists(long rowId) {
		String where = KEY_LISTSTABLE_ROWID + "=" + rowId;
		return db.delete(LISTS_DATABASE_TABLE, where, null) != 0;
	}

	// Return all data in the database.
	public Cursor getAllRowsFromLists() {
		String where = null;
		Cursor c = db.query(true, LISTS_DATABASE_TABLE, ALL_LISTSTABLE_KEYS,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRowFromLists(long rowId) {
		String where = KEY_LISTSTABLE_ROWID + "=" + rowId;
		Cursor c = db.query(true, LISTS_DATABASE_TABLE, ALL_LISTSTABLE_KEYS,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Change an existing row to be equal to new data.
	public boolean updateRowForLists(long rowId, String listName, int roomsList) {
		String where = KEY_LISTSTABLE_ROWID + "=" + rowId;

		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_LIST_NAME, listName);
		newValues.put(KEY_ROOMS_LIST, roomsList);

		// Insert it into the database.
		return db.update(LISTS_DATABASE_TABLE, newValues, where, null) != 0;
	}

	// /////////////////////
	// PRIVATE HELPER CLASS
	// /////////////////////
	/**
	 * Private class which handles database creation and upgrading. Used to
	 * handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(ROOMS_DATABASE_CREATE_SQL);
			_db.execSQL(LISTS_DATABASE_CREATE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version "
					+ oldVersion + " to " + newVersion
					+ ", which will destroy all old data!");

			// // Destroy old database:
			// _db.execSQL("DROP TABLE IF EXISTS " + ROOMS_DATABASE_CREATE_SQL);
			// _db.execSQL("DROP TABLE IF EXISTS " + LISTS_DATABASE_CREATE_SQL);
			// // Recreate new database:

			onCreate(_db);
		}
	}
}
