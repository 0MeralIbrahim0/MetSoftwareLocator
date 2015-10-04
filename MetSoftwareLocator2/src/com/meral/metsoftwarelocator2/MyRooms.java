package com.meral.metsoftwarelocator2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyRooms extends Activity implements OnClickListener {

	Button btnGuide, btnSettings;
	Button btnMakeList, btnShowList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myrooms);
		initComponent();
	}

	/**
	 * Component we initialize in onCreate method.
	 */
	private void initComponent() {
		btnGuide = (Button) findViewById(R.id.buttonGuide_MyRooms);
		btnSettings = (Button) findViewById(R.id.buttonSettings_MyRooms);
		btnMakeList = (Button) findViewById(R.id.buttonMakeList_MyRooms);
		btnShowList = (Button) findViewById(R.id.buttonShowList_MyRooms);
		btnGuide.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
		btnMakeList.setOnClickListener(this);
		btnShowList.setOnClickListener(this);
	}

	/**
	 * Method with switch statement for the buttons. It navigates the user to
	 * another activity according the button pressed.
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonGuide_MyRooms:
			startActivity(new Intent(MyRooms.this, GuideActivity.class));
			break;
		case R.id.buttonSettings_MyRooms:
			startActivity(new Intent(MyRooms.this, Settings.class));
			break;
		case R.id.buttonMakeList_MyRooms:
			startActivity(new Intent(MyRooms.this, MakeList.class));
			break;
		case R.id.buttonShowList_MyRooms:
			startActivity(new Intent(MyRooms.this, ShowList.class));
			break;
		default:
			break;
		}
	}
}
