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


public class Event{
    Date startTime,endTime;
    String name, desc;
    Coordinate location;

    public Event(Date startTime, Date endTime, String name, String desc, Coordinate location){
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.desc = desc;
        this.location = location;

    }
    
    public static Coordinate geocode(String search) throws MalformedURLException,IOException {
    		JSONParser parser = new JSONParser();
    		URL geoUrl = new URL("http://nominatim.openstreetmap.org/search?" + search+"&format=json");
    		
    		URLConnection geoUrlc = geoUrl.openConnection();
    		InputStream geoIs = geoUrl.openStream();
    		// BufferedReader geoRead = new BufferedReader(new InputStreamReader(geoIs,Charset.forName("UTF-8")));
    		Scanner geoScanner = new Scanner(geoIs).useDelimiter("\\A");
    		String geoString = geoScanner.hasNext() ? geoScanner.next():"";
    		
    		
    		return new Coordinate(null,null);
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

    public void setDesc(String desc) {
        this.desc = desc;
    }
}