import java.util.Date;
import java.util.Scanner;
import com.sothawo.mapjfx.Coordinate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.*;
import java.io.*;

public class Event implements Comparable<Event> {

	// Note that Date is a Java given object with year to minutes
	private int id;
	private Date startTime, endTime;
	private String name, desc, address;
	private Coordinate location;

	// Initialize all variables associated with events
	public Event(Date startTime, Date endTime, String name, String desc, String address, Coordinate location) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.name = name;
		this.desc = desc;
		this.location = location;
		this.address = address;

	}

	public static String convertString(String original) {
		String replaced = original.replace(" ", "%20");
		replaced = replaced.replace(",", "%2C");
		return replaced;
	}

	public static Coordinate geocode(String search) throws MalformedURLException, IOException, ParseException {
		double lattitude = 0;
		double longitude = 0;
		String realString = convertString(search);
		URL geoUrl = new URL("http://nominatim.openstreetmap.org/search.php?q=" + realString + "&format=json");
		InputStream geoIs = geoUrl.openStream();

		Scanner geoScanner = new Scanner(geoIs);
		geoScanner.useDelimiter("\\A");
		String geoString = geoScanner.hasNext() ? geoScanner.next() : "";

		JSONParser geoParser = new JSONParser();
		JSONArray newArray = (JSONArray) geoParser.parse(geoString);
		try{
			String objLat = (String) ((JSONObject) newArray.get(0)).get("lat");
			String objLon = (String) ((JSONObject) newArray.get(0)).get("lon");
			
			lattitude = Double.parseDouble(objLat);
			longitude = Double.parseDouble(objLon);
		}catch(IndexOutOfBoundsException e) {
			System.out.println(e);
		}

		
		geoScanner.close();
		geoIs.close();
		
		return new Coordinate(lattitude, longitude);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int newId) {
		id = newId;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDesc() {
		return desc;
	}

	public Coordinate getLocation() {
		return location;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int compareTo(Event e) {
		if (startTime.compareTo(e.startTime) != 0) {
			return startTime.compareTo(e.startTime);
		} else {
			return endTime.compareTo(e.endTime);
		}
	}
}