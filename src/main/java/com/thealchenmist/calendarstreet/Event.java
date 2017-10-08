import java.util.Date;
import com.sothawo.mapjfx.Coordinate;

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