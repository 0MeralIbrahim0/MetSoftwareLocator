package com.meral.metsoftwarelocator2;

import rest.DBAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MakeList extends Activity implements OnClickListener {

	DBAdapter myDb;
	Button btnGuide, btnSettings;
	private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10, cb11,
			cb12, cb13, cb14, cb15;
	TextView listNameTextView;
	String list_Name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.make_list);
		initCheckBoxes();
		openDB();
		intiComponents();
	}

	/**
	 * Method initializes the check boxes in getting them from UI.
	 */
	public void initCheckBoxes() {
		cb1 = (CheckBox) findViewById(R.id.checkBox1);
		cb2 = (CheckBox) findViewById(R.id.checkBox2);
		cb3 = (CheckBox) findViewById(R.id.checkBox3);
		cb4 = (CheckBox) findViewById(R.id.checkBox4);
		cb5 = (CheckBox) findViewById(R.id.checkBox5);
		cb6 = (CheckBox) findViewById(R.id.checkBox6);
		cb7 = (CheckBox) findViewById(R.id.checkBox7);
		cb8 = (CheckBox) findViewById(R.id.checkBox8);
		cb9 = (CheckBox) findViewById(R.id.checkBox9);
		cb10 = (CheckBox) findViewById(R.id.checkBox10);
		cb11 = (CheckBox) findViewById(R.id.checkBox11);
		cb12 = (CheckBox) findViewById(R.id.checkBox12);
		cb13 = (CheckBox) findViewById(R.id.checkBox13);
		cb14 = (CheckBox) findViewById(R.id.checkBox14);
		cb15 = (CheckBox) findViewById(R.id.checkBox15);
	}

	/**
	 * Component we initialize in onCreate method.
	 */
	public void intiComponents() {
		btnGuide = (Button) findViewById(R.id.buttonGuide_MakeList);
		btnSettings = (Button) findViewById(R.id.buttonSettings_MakeList);
		btnGuide.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
		listNameTextView = (TextView) findViewById(R.id.editTextListName_Main);
	}

	/**
	 * Method with switch statement for the buttons. It navigates the user to
	 * another activity according the button pressed.
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonGuide_MakeList:
			startActivity(new Intent(MakeList.this, GuideActivity.class));
			break;
		case R.id.buttonSettings_MakeList:
			startActivity(new Intent(MakeList.this, Settings.class));
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
	 * This method gets a list name and the checked boxes and saves a list to
	 * the database.
	 */
	public void submitToDB(View v) {
		String rooms = " ";
		if (cb1.isChecked())
			rooms += cb1.getText() + "\n ";
		if (cb2.isChecked())
			rooms += cb2.getText() + "\n ";
		if (cb3.isChecked())
			rooms += cb3.getText() + "\n ";
		if (cb4.isChecked())
			rooms += cb4.getText() + "\n ";
		if (cb5.isChecked())
			rooms += cb5.getText() + "\n ";
		if (cb6.isChecked())
			rooms += cb6.getText() + "\n ";
		if (cb7.isChecked())
			rooms += cb7.getText() + "\n ";
		if (cb8.isChecked())
			rooms += cb8.getText() + "\n ";
		if (cb9.isChecked())
			rooms += cb9.getText() + "\n ";
		if (cb10.isChecked())
			rooms += cb10.getText() + "\n ";
		if (cb11.isChecked())
			rooms += cb11.getText() + "\n ";
		if (cb12.isChecked())
			rooms += cb12.getText() + "\n ";
		if (cb13.isChecked())
			rooms += cb13.getText() + "\n ";
		if (cb14.isChecked())
			rooms += cb14.getText() + "\n ";
		if (cb15.isChecked())
			rooms += cb15.getText() + "\n ";

		String listName = "" + listNameTextView.getText();
		// If checked rooms and list name is empty, tell user
		// to provide these data.
		if (listName == "" && rooms == " ") {
			Toast.makeText(getApplicationContext(),
					"Enter a list name and check rooms!", Toast.LENGTH_LONG)
					.show();
			// If list name is empty tell user to provide list name
		} else if (listName == "") {
			Toast.makeText(getApplicationContext(), "Enter List Name!",
					Toast.LENGTH_LONG).show();
			// If checked rooms are empty tell user to check boxes.
		} else if (rooms == " ") {
			Toast.makeText(getApplicationContext(), "Check Rooms!",
					Toast.LENGTH_LONG).show();
		} else {
			// If both list name and check boxes are provided, insert a row to
			// the database with the list details.
			list_Name = listName;
			myDb.insertRowListsTable(listName, rooms);
			String tost = "List Saved \n" + listName + ": \n" + rooms;
			Toast.makeText(getApplicationContext(), tost, Toast.LENGTH_LONG)
					.show();
			startActivity(new Intent(MakeList.this, MainActivity.class));
		}
	}
}
