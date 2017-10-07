import java.util.Date;
import com.sothawo.mapjfx.Coordinate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


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
    
    public static Coordinate geocode(String search) {
    		JSONParser parser = new JSONParser();
    		Object file = parser.parse(new FileReader("hi"));
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