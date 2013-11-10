package com.hack.beacon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.location.Location;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class ParseHelper 
{
	private static final String TAG = "ParseHelper";
	
	/*
	 * startTime: 0 = 0 mins
	 * 			  1 = 5
	 * 			  2 = 10
	 *            3 = 15
	 *            4 = 20
	 *            5 = 30
	 *            6 = 45
	 *            7 = 1 hour
	 * 
	 *  endTime = (endTime + 1) hours long, so endTime = 0 would
	 *  be an event lasting 1 hour, endTime = 1 would be an event lasting
	 *  2 hours, and so forth.
	 *  
	 */
	public static void createEvent(String eventName, String hostName, int startTime,
			int endTime, String eventAddress, Location locationData)
	{
		ParseObject eventObject = new ParseObject("Event");
		eventObject.put("EventName", eventName);
		eventObject.put("HostName", hostName);
		eventObject.put("EventAddress", eventAddress);
		
		
		// Parse the dates and add them to the eventObject
		int startMins = 0;
		if(startTime < 5)
		{
			startMins = 5 * startTime;
		}
		if(startTime == 5)
		{
			startMins = 30;
		}
		if(startTime == 6)
		{
			startMins = 45;
		}
		if(startTime == 7)
		{
			startMins = 60;
		}
		
		Calendar c = Calendar.getInstance();
		Calendar s = c;
		s.add(Calendar.MINUTE, startMins);
		Date start = s.getTime();
		eventObject.put("StartTime", start);
		
		c.add(Calendar.HOUR, (endTime + 1));
		Date end = c.getTime();
		eventObject.put("EndTime", end);
		
		// Parse latitude and longitude, then add to eventObject 
		double lat = locationData.getLatitude();
		double lon = locationData.getLongitude();
		ParseGeoPoint point = new ParseGeoPoint(lat, lon);
		eventObject.put("LocationData", point);
		
		eventObject.saveInBackground(new SaveCallback(){

				@Override
				public void done(ParseException e) 
				{
					if(e != null)
					{
						Log.d(TAG, e.getLocalizedMessage());
					}
				}
				
			});
	}
	
	
	/*
	 * LatLng is retrieved using mapview.getMap().getProjection().frompixel(int x, int y)
	 * Using Mapview mv, to get the NE corner: x = mv.getWidth(), y = 0;
	 *                              SW corner: x = 0,             y = mv.getHeight();
	 
	public static List<ParseObject> getLocations(LatLng ne, LatLng sw)
	{
		final ArrayList<ParseObject> ret = new ArrayList<ParseObject>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
		ParseGeoPoint nePoint = new ParseGeoPoint(ne.latitude, ne.longitude);
		ParseGeoPoint swPoint = new ParseGeoPoint(sw.latitude, sw.longitude);
		
		query.whereWithinGeoBox("LocationData", swPoint, nePoint);
		query.findInBackground(new FindCallback<ParseObject>(){

			@Override
			public void done(List<ParseObject> locList, ParseException e) 
			{
				if(e != null)
				{
					Log.d(TAG, e.getLocalizedMessage());
				}
				else
				{
					for(int i = 0; i < locList.size(); i++)
					{
						ret.add(locList.get(i));
					}
				}
			}
		
		});
		return ret;
	}
	*/

}
