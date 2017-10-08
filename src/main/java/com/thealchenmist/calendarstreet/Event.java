import java.util.Date;
import java.util.Scanner;
import com.sothawo.mapjfx.Coordinate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.*;
import java.nio.charset.Charset;
import java.io.*;


public class Event implements Comparable<Event>{
    private Date startTime,endTime;
    private String name, desc, address;
    private Coordinate location;

    public Event(Date startTime, Date endTime, String name, String desc, String address, Coordinate location){
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.desc = desc;
        this.location = location;
        this.address = address;

    }
   
    public String convertString(String original) {
    		String replaced = original.replace(" ", "%20");
    		replaced = replaced.replace(",","%2C");
    		return replaced;
    }
    
    public static Coordinate geocode(String search) throws MalformedURLException,IOException,ParseException {
    		JSONParser parser = new JSONParser();
    		//String realString = convertString(search);
    		URL geoUrl = new URL("http://nominatim.openstreetmap.org/search.php?q=anne+arundel+hall%2C+college+park%2C+maryland&polygon_geojson=1&viewbox=&format=json");
    		
    		URLConnection geoUrlc = geoUrl.openConnection();
    		InputStream geoIs = geoUrl.openStream();
    		//BufferedReader geoRead = new BufferedReader(new InputStreamReader(geoIs,Charset.forName("UTF-8")));
    		Scanner geoScanner = new Scanner(geoIs).useDelimiter("\\A");
    		String geoString = geoScanner.hasNext() ? geoScanner.next():"";
    		//String geoString = readAll(geoRead);
    		JSONParser geoParser = new JSONParser();
    		JSONArray newArray = (JSONArray) geoParser.parse(geoString);
    		String objLat = (String) ((JSONObject)newArray.get(0)).get("lat");
    		String objLon = (String) ((JSONObject)newArray.get(0)).get("lon");
    		System.out.println(geoParser.parse(geoString));
    		System.out.println(geoString);
    		
    		System.out.println(objLat+ " "+objLon);
    		JSONObject geoObject =  (JSONObject)geoParser.parse(geoString);
    		String sLatitude = (String) geoObject.get("lat");
    		String sLongitude = (String) geoObject.get("lon");
    		
    		double lattitude = Double.parseDouble(objLat);
    		double longitude = Double.parseDouble(objLon);
    		
    		return new Coordinate(lattitude,longitude);
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
		if(startTime.compareTo(e.startTime)!=0) {
			return startTime.compareTo(e.startTime);
		} else {
			return endTime.compareTo(e.endTime);
		}
	}
}