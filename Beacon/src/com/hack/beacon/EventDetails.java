package com.hack.beacon;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class EventDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		
		ParseObject po = getDetails("Q8gIxMwQSP");
		TextView name = (TextView) findViewById(R.id.eventName);
		name.setText((CharSequence) po.get("EventName"));
		
		EditText start = (EditText) findViewById(R.id.start);
		Date st = (Date) po.get("StartTime");
		SimpleDateFormat ft = new SimpleDateFormat ("hh:mm");
		start.setText((CharSequence) ft.format(st));
		
		EditText end = (EditText) findViewById(R.id.end);
		Date en = (Date) po.get("EndTime");
		end.setText((CharSequence) ft.format(en));
		
		TextView loc = (TextView) findViewById(R.id.location);
		loc.setText((CharSequence) po.get("EventAddress"));
		
		TextView host = (TextView) findViewById(R.id.host);
		host.setText((CharSequence) po.get("HostName"));
		
		TextView category = (TextView) findViewById(R.id.category);
		category.setText((CharSequence) po.get("Category"));
		
		TextView desc = (TextView) findViewById(R.id.description);
		desc.setText((CharSequence) po.get("Description"));
		
		final Button directions = (Button) findViewById(R.id.directionsBtn);
        directions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	ParseObject po = getDetails("Q8gIxMwQSP");
            	ParseGeoPoint gp = (ParseGeoPoint) po.get("LocationData");
            	Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
            		    Uri.parse("http://maps.google.com/maps?daddr="+gp.getLatitude()+","+gp.getLongitude()));
            	EventDetails.this.startActivity(intent);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_details, menu);
		return true;
	}
	
	public ParseObject getDetails(String id){
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
		ParseObject po = null;
		try {
			 po = query.get("Q8gIxMwQSP");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return po;
	}

}
