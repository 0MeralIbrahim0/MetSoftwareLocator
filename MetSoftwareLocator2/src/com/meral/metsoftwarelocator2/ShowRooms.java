package com.meral.metsoftwarelocator2;

import rest.DBAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowRooms extends Activity implements OnClickListener {

	Button btnGuide, btnSettings;
	ListView softwareList;
	TextView tv;

	DBAdapter myDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showrooms);
		initComponent();
		openDB();
		// registerListClickCallback();
	}

	/**
	 * Component we initialize in onCreate method.
	 */
	private void initComponent() {
		btnGuide = (Button) findViewById(R.id.buttonGuide_ShowRooms);
		btnSettings = (Button) findViewById(R.id.buttonSettings_ShowRooms);
		btnGuide.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
		softwareList = (ListView) findViewById(R.id.listViewShowRooms_SoftwareArray);
		String[] software = this.getResources().getStringArray(
				R.array.softwareArray);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.selectrowelement, R.id.TextViewOneElementRow_element,
				software);
		softwareList.setAdapter(adapter);
	}

	/**
	 * Method with switch statement for the buttons. It navigates the user to
	 * another activity according the button pressed.
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonGuide_ShowRooms:
			startActivity(new Intent(ShowRooms.this, GuideActivity.class));
			break;
		case R.id.buttonSettings_ShowRooms:
			startActivity(new Intent(ShowRooms.this, Settings.class));
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

	View previouslySelectedItem = null;

	@SuppressWarnings("deprecation")
	public void getSelectedListElementText(View v) {

		tv = (TextView) v;
		String softwareName = (String) tv.getText();

		Cursor c = myDb.getRoomsForSoftware(softwareName);
		startManagingCursor(c);

		// /// getting from the tables and creating an array of fields
		String[] fromFieldNames = new String[] { DBAdapter.KEY_ROOM_NAME };

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
		ListView myList = (ListView) findViewById(R.id.listViewShowRooms_RoomsContains);
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
	
	public void seeAllSoftware(View v){
		
		
		
	}

}
