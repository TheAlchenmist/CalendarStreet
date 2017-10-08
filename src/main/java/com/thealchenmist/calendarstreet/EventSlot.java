import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EventSlot extends GridPane {
	private Label titleLabel,timeLabel,dateLabel,locationLabel;
	public EventSlot(Event event) {
		titleLabel = new Label(event.getName());
		titleLabel.setWrapText(true);
		titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		
		/*get times*/
		SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
		timeLabel = new Label(localDateFormat.format(event.getStartTime()) + " — " + 
						localDateFormat.format(event.getEndTime()));
		/*get date(s)*/
		String startDate = DateFormat.getDateInstance().format(event.getStartTime());
		String endDate = DateFormat.getDateInstance().format(event.getEndTime());
		if(startDate.equals(endDate)) {
			dateLabel = new Label(startDate);
		} else {
			dateLabel = new Label(startDate + " — " + endDate);
		}
		
		locationLabel = new Label(event.getAddress());
		locationLabel.setWrapText(true);
		
		Stage stage = new Stage();
		stage.setTitle("Description");
		this.setOnMouseEntered(e -> {
		    // Fill with event description
			TextArea text = new TextArea(event.getDesc());
			text.setWrapText(true);

			// Create scene and add mouse exit code
			Scene scene = new Scene(text, 200, 100);
			scene.setOnMouseExited(f -> stage.close());
			stage.setScene(scene);
			
			// Center above and to the right of EventSlot
			Bounds location = this.localToScreen(this.getBoundsInLocal());
			stage.setX(location.getMaxX() / 2 + this.getWidth() * 1.2);
			stage.setY(location.getMaxY() / 2);

			stage.show();
		});
		this.setOnMouseExited(e -> stage.close());
		
		this.getColumnConstraints().add(new ColumnConstraints(185));
		this.setPadding(new Insets(5,5,5,5));
		this.add(titleLabel, 0, 0);
		this.add(timeLabel, 0, 1);
		this.add(dateLabel, 0, 2);
		this.add(locationLabel, 0, 3);
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
