package com.hack.beacon;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.parse.Parse;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Testing for Event Details
		//Intent intent = new Intent(this, EventDetails.class);
    	//this.startActivity(intent);
		
		String app_id = getString(R.string.parse_app_id);
		String client_key = getString(R.string.parse_client_key);
		Parse.initialize(this, app_id, client_key);
		
		SupportMapFragment fm = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
		GoogleMap mMap = fm.getMap(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
