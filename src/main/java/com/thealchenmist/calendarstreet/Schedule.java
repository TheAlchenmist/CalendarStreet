import java.util.LinkedList;

@SuppressWarnings(value = "serial")
public class Schedule extends LinkedList<Event> {
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
}
