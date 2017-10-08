import java.util.LinkedList;

public class Schedule extends LinkedList<Event>{
	@Override
	public boolean add(Event e) { 
		boolean added = false;
		if(this.isEmpty()) {
			super.add(e);
			added = true;
		} else {
			for(int i = 0;i<this.size();i++) {
				if(e.compareTo(this.get(i)) <= 0) {
					this.add(i, e);
					added = true;
				}
			}
			if(!added) {
				this.add(e);
				added = true;
			}
		}
		return added;
	}
}
