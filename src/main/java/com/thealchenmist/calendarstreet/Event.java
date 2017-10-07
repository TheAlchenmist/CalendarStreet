import java.util.Date;

public class Event{
    Date startTime,endTime;
    String name, desc;

    public Event(Date startTime, Date endTime, String name, String desc){
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.desc = desc;


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