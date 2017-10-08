import java.text.DateFormat;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EventSlot extends GridPane {
	private Label titleLabel,timeLabel,locationLabel;
	public EventSlot(Event event) {
		titleLabel = new Label(event.getName());
		titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		timeLabel = new Label(DateFormat.getDateInstance().format(event.getStartTime()) + " - " 
								+ DateFormat.getDateInstance().format(event.getEndTime()));
		locationLabel = new Label(event.getAddress());
		locationLabel.setWrapText(true);
		
		this.getColumnConstraints().add(new ColumnConstraints(200));
		this.setPadding(new Insets(5,5,5,5));
		this.add(titleLabel, 0, 0);
		this.add(timeLabel, 0, 1);
		this.add(locationLabel, 0, 2);
	}
	//DO NOT USE THIS LOL
	public EventSlot() {
		titleLabel = new Label("THIS IS A TEST");
		titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		timeLabel = new Label("PLEAERESUHIDFSDHJF");
		locationLabel = new Label("3850 Parrot dr ellicott city md 21042 wow this is gonna look like ASS");
		locationLabel.setWrapText(true);
	
		this.getColumnConstraints().add(new ColumnConstraints(200));
		this.setPadding(new Insets(7,7,7,7));
		this.add(titleLabel, 0, 0);
		this.add(timeLabel, 0, 1);
		this.add(locationLabel, 0, 2);
	}
}
