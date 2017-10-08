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
