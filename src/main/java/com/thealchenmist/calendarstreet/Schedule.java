import java.util.LinkedList;
import java.util.List;

@SuppressWarnings(value = "serial")
public class Schedule extends LinkedList<Event> {
    public Schedule() {
        super();
        List<Event> events = Database.getEvents();
        for (Event event : events) {
            this.add(event);
        }
    }

    @Override
    public boolean add(Event e) {
        boolean added = false;
        if (this.isEmpty() || e.compareTo(this.getLast()) > 0) {
            super.add(e);
            added = true;
        } else {
            for (int i = 0; i < this.size() && !added; i++) {
                if (e.compareTo(this.get(i)) <= 0) {
                    super.add(i, e);
                    added = true;
                }
            }
        }
        return added;
    }
    
    @Override
    public Event remove(int eventId) {
        Database.deleteEvent(eventId);
        for (int i = 0; i < this.size(); i++) {
            if (get(i).getId() == eventId) {
                return super.remove(i);
            }
        }
        return null;
    }

    public void update(Event e) {
        for (int i = 0; i < this.size(); i++) {
            if (e.getId() == this.get(i).getId()) {
                this.set(i, e);
                Database.updateEvent(e.getId(),
                                     e.getStartTime(),
                                     e.getEndTime(),
                                     e.getName(),
                                     e.getDesc(),
                                     e.getAddress(),
                                     e.getLocation());
            }
        }
    }
}
