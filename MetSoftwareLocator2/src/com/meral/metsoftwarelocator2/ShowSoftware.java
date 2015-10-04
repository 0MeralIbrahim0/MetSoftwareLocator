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
import android.widget.TextView;
import android.widget.Toast;

public class ShowSoftware extends Activity implements OnClickListener {

	Button btnGuide, btnSettings;
	DBAdapter myDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showsoftware);
		initComponent();
		openDB();
		listViewFromDB();
	}

	/**
	 * Component we initialize in onCreate method.
	 */
	private void initComponent() {
		btnGuide = (Button) findViewById(R.id.buttonGuide_ShowSoftware);
		btnSettings = (Button) findViewById(R.id.buttonSettings_ShowSoftware);
		btnGuide.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
	}

	/**
	 * Method with switch statement for the buttons. It navigates the user to
	 * another activity according the button pressed.
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonGuide_ShowSoftware:
			startActivity(new Intent(ShowSoftware.this, GuideActivity.class));
			break;
		case R.id.buttonSettings_ShowSoftware:
			startActivity(new Intent(ShowSoftware.this, Settings.class));
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

	@SuppressWarnings("deprecation")
	private void listViewFromDB() {
		Cursor cursor = myDb.getAllRowsFromRooms();

		// Allow activity to manage lifetime of the cursor.
		// DEPRECATED! Runs on the UI thread, OK for small/short queries.
		startManagingCursor(cursor);

		// Setup mapping from cursor to view fields:

		// /// getting from the tables and creating an array of fields
		String[] fromFieldNames = new String[] { DBAdapter.KEY_ROOM_NAME };

		// ///////// Create an array of views
		int[] toViewIDs = new int[] { R.id.TextViewOneElementRow_element };

		// // Create adapter to may columns of the DB onto elements in the UI.
		SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, // Context
				R.layout.selectrowelement, // Row layout template
				cursor, // cursor (set of DB records to map)
				fromFieldNames, // DB Column names
				toViewIDs // View IDs to put information in
		);

		// ///////// Set the adapter for the list view
		ListView myList = (ListView) findViewById(R.id.listViewShowRooms_DBRooms);
		myList.setAdapter(myCursorAdapter);
	}

	View previouslySelectedItem = null;
	TextView tv;

	@SuppressWarnings("deprecation")
	public void getSelectedListElementText(View v) {

		tv = (TextView) v;
		String roomName = (String) tv.getText();

		Cursor c = myDb.getSoftwareForRooms(roomName);
		startManagingCursor(c);

		// /// getting from the tables and creating an array of fields
		String[] fromFieldNames = new String[] { DBAdapter.KEY_SOFTWARE };

		// ///////// Create an array of views
		int[] toViewIDs = new int[] { R.id.TextViewOneElementRow_element };

		// // Create adapter to may columns of the DB onto elements in the UI.

		SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, // Context
				R.layout.rowelement, // Row layout template
				c, // cursor (set of DB records to map)
				fromFieldNames, // DB Column names
				toViewIDs // View IDs to put information in
		);

		// ///////// Set the adapter for the list view
		ListView myList = (ListView) findViewById(R.id.listViewShowRooms_SoftwareContains);
		myList.setAdapter(myCursorAdapter);

		// I HAVE RETURNED SQL QUERRY CURSOR.
		// I WILL USE THE METHOD THAT COMBINES CURSOR AND LIST VIEW

		if (previouslySelectedItem != null) {
			previouslySelectedItem.setBackgroundColor(getResources().getColor(
					R.color.transparent));
		}

		v.setBackgroundColor(getResources().getColor(R.color.blue));

		previouslySelectedItem = v;
	}

}
