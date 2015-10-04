package com.meral.metsoftwarelocator2;

import java.io.File;
import rest.DBAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button btnMyRooms, btnShowRooms, btnShowSoftware;
	Button btnGuide, btnSettings;

	DBAdapter myDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initComponent();
	}

	/**
	 * Component we initialize in onCreate method.
	 */
	private void initComponent() {
		btnGuide = (Button) findViewById(R.id.buttonGuide_Main);
		btnSettings = (Button) findViewById(R.id.buttonSettings_Main);
		btnMyRooms = (Button) findViewById(R.id.buttonMyRooms_Main);
		btnShowRooms = (Button) findViewById(R.id.buttonShowRooms_Main);
		btnShowSoftware = (Button) findViewById(R.id.buttonShowSoftware_Main);
		btnGuide.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
		btnMyRooms.setOnClickListener(this);
		btnShowRooms.setOnClickListener(this);
		btnShowSoftware.setOnClickListener(this);
		createOrOpenDB();
	}

	/**
	 * Method with switch statement for the buttons. It navigates the user to
	 * another activity according the button pressed.
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonGuide_Main:
			startActivity(new Intent(MainActivity.this, GuideActivity.class));
			break;
		case R.id.buttonSettings_Main:
			startActivity(new Intent(MainActivity.this, Settings.class));
			break;
		case R.id.buttonMyRooms_Main:
			startActivity(new Intent(MainActivity.this, MyRooms.class));
			break;
		case R.id.buttonShowRooms_Main:
			startActivity(new Intent(MainActivity.this, ShowRooms.class));
			break;
		case R.id.buttonShowSoftware_Main:
			startActivity(new Intent(MainActivity.this, ShowSoftware.class));
			break;
		default:
			break;
		}
	}

	/**
	 * This method will be called every time user runs the application and it
	 * will check it there is an existing database. If there is, the method will
	 * only open the database. Else, if there is no database, which is the case
	 * that the user runs the application for the first time, it will open the
	 * database and start adding data in it.
	 */
	public void createOrOpenDB() {
		if (doesDatabaseExist(this, "MyDb")) {
			openDB();
		} else {
			// Open and add to database
			openDB();
			myDb.insertRowRoomsTable(
					"TMG-01",
					"Eclipse Orecle, Express Studio, Photoshop, Netbeans, Notpad++, Protege, Team Viewer",
					"20 PCs available");
			myDb.insertRowRoomsTable(
					"TMG-02",
					"Eclipse Orecle, Eclipse 4.1, Dreamviewer, Netbeans, Notpad++, Paint.net, Gantt Project",
					"50 PCs available");
			myDb.insertRowRoomsTable(
					"TMG-03",
					"Eclipse Orecle, Eclipse 4.1, Dreamviewer, Photoshop, Netbeans, Paint.net, Protege, Team Viewer, FileZilla",
					"10 computers available");
			myDb.insertRowRoomsTable(
					"TMG-04",
					"Eclipse Orecle, Eclipse 4.1, Dreamviewer, Express Studio, Gantt Project, FileZilla",
					"30 computers available");
			myDb.insertRowRoomsTable(
					"TMG-05",
					" Eclipse 4.1, Express Studio, Photoshop, Netbeans, Notpad++, Paint.net, Team Viewer",
					"40 computers available");
			myDb.insertRowRoomsTable(
					"T2-20",
					"Eclipse Orecle, Eclipse 4.1, Dreamviewer, Photoshop, Netbeans, Notpad++, Protege, Gantt Project",
					"15 computers available");
			myDb.insertRowRoomsTable(
					"T5-20",
					"Eclipse Orecle, Eclipse 4.1, Express Studio, Notpad++, Protege, Team Viewer, FileZilla",
					"25 computers available");
			myDb.insertRowRoomsTable(
					"T6-20",
					"Eclipse 4.1, Dreamviewer, Express Studio, Netbeans, Paint.net, Gantt Project, FileZilla",
					"20 computers available");
			myDb.insertRowRoomsTable(
					"T7-06",
					"Eclipse Orecle, Eclipse 4.1, Dreamviewer, Photoshop, Netbeans, Notpad++, Paint.net, Team Viewer",
					"Project Room, 20 computers available");
			myDb.insertRowRoomsTable(
					"T7-05",
					"Eclipse Orecle, Eclipse 4.1, Express Studio, Photoshop, Protege, Team Viewer, FileZilla",
					"23 computers available");
			myDb.insertRowRoomsTable(
					"T7-20",
					"Eclipse 4.1, Dreamviewer, Express Studio, Netbeans, Notpad++, Paint.net, Protege, Gantt Project",
					"17 computers available");
			myDb.insertRowRoomsTable(
					"T8-03",
					"Eclipse Orecle, Eclipse 4.1, Dreamviewer, Photoshop, Gantt Project, FileZilla",
					"17 computers available");
			myDb.insertRowRoomsTable(
					"T8-06",
					"Eclipse Orecle, Eclipse 4.1, Dreamviewer, Express Studio, Netbeans, Notpad++, Paint.net, Team Viewer",
					"17 computers available");
			myDb.insertRowRoomsTable(
					"T8-07",
					"Eclipse Orecle, Eclipse 4.1, Express Studio, Photoshop, Protege, Team Viewer, FileZilla",
					"17 computers available");
			myDb.insertRowRoomsTable(
					"Project Room",
					"Eclipse Orecle, Eclipse 4.1, Dreamviewer, Express Studio, Photoshop, Netbeans, Notpad++, Paint.net, Protege, Gantt Project, Team Viewer, FileZilla",
					"17 computers available");
		}

	}

	/**
	 * Method that check if database exists.
	 */
	private static boolean doesDatabaseExist(ContextWrapper context,
			String dbName) {
		File dbFile = context.getDatabasePath(dbName);
		return dbFile.exists();
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

}
