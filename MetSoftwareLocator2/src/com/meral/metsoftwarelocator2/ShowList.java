package com.meral.metsoftwarelocator2;

import rest.DBAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowList extends Activity implements OnClickListener {

	Button btnGuide, btnSettings;
	DBAdapter myDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showlist);
		initComponent();
		openDB();
		listViewFromDB();
	}

	/**
	 * Component we initialize in onCreate method.
	 */
	private void initComponent() {
		btnGuide = (Button) findViewById(R.id.buttonGuide_MyR_ShowList);
		btnSettings = (Button) findViewById(R.id.buttonSettings_MyR_ShowList);
		btnGuide.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
	}

	/**
	 * Method with switch statement for the buttons. It navigates the user to
	 * another activity according the button pressed.
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonGuide_MyR_ShowList:
			startActivity(new Intent(ShowList.this, GuideActivity.class));
			break;
		case R.id.buttonSettings_MyR_ShowList:
			startActivity(new Intent(ShowList.this, Settings.class));
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}

	/**
	 * Method for initiating the DBAdapter object and calling its open() method
	 */
	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}

	/**
	 * Method calls close() method of DBAdapter class.
	 */
	private void closeDB() {
		myDb.close();
	}

	/**
	 * Method for deleting rows from database. This method gets the row id from
	 * list view as parameter, and calls the deleteRowFromLists() method of
	 * DBAdapter class for deleting the object with the same id. Also it get
	 * details of this object and creates a toast for user feedback.
	 */
	@SuppressWarnings("deprecation")
	private void listViewFromDB() {
		Cursor cursor = myDb.getAllRowsFromLists();

		// Allow activity to manage lifetime of the cursor.
		// DEPRECATED! Runs on the UI thread, OK for small/short queries.
		startManagingCursor(cursor);

		// Setup assigning from cursor to view fields:

		// Getting from the tables and creating an array of fields
		String[] fromFieldNames = new String[] { DBAdapter.KEY_LIST_NAME,
				DBAdapter.KEY_ROOMS_LIST };

		// Create an array of views from a layout file.
		int[] toViewIDs = new int[] { R.id.TextView_ListName,
				R.id.TextView_RoomsList };

		// Create adapter to get each element of fromFieldNames array list and
		// pass to the toViewIDs array list.
		SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, // Context
				R.layout.item_layout, // Row layout template
				cursor, // cursor (set of DB records to map)
				fromFieldNames, // DB Column names
				toViewIDs // View IDs to put information in
		);

		// Set the adapter for the list view
		ListView myList = (ListView) findViewById(R.id.listViewFromDB_ShowLists1);
		myList.setAdapter(myCursorAdapter);
	}

	/**
	 * Button method to navigate the user to delete activity
	 */
	public void toDeleteActivity(View v) {
		startActivity(new Intent(ShowList.this, DeleteLists.class));
	}
}
