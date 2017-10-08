import java.util.LinkedList;

@SuppressWarnings(value = "serial")
public class Schedule extends LinkedList<Event> {
    public Schedule() {
        super(Database.getEvents());
    }

    @Override
    public boolean add(Event e) {
        int eventId = Database.insertEvent(e.getStartTime(),
                                           e.getEndTime(),
                                           e.getName(),
                                           e.getDesc(),
                                           e.getAddress(),
                                           e.getLocation());
        e.setId(eventId);

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
            if (get(i).getId() == this.get(i).getId()) {
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
