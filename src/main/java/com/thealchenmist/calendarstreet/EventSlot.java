import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class EventSlot extends GridPane {
	private Label titleLabel,timeLabel,locationLabel;
	public EventSlot(Event event) {
		titleLabel = new Label(event.getName());
		timeLabel = new Label(event.getStartTime() + " - " + event.getEndTime());
		locationLabel = new Label(event.getAddress());
		
		this.add(titleLabel, 0, 0);
		this.add(timeLabel, 0, 1);
		this.add(locationLabel, 0, 2);
	}
}
