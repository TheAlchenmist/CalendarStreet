import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.Marker.Provided;

public class CalendarStreetGUI extends Application {
    
    MapView mapPane;
    List<Marker> markers = new ArrayList<Marker>();
    Schedule schedule = new Schedule();
    private VBox myEventsPane;

    public Marker addMarker(Coordinate position) {
        Marker newMarker = Marker.createProvided(Provided.RED)
                                 .setPosition(position)
                                 .setVisible(true);
        mapPane.addMarker(newMarker);
        markers.add(newMarker);
        resizeMap();

        return newMarker;
    }

    public void removeMarker(Marker marker) {
        mapPane.removeMarker(marker);
        markers.remove(marker);
        resizeMap();
    }

    // TODO: resizing map with zero markers will raise Illegal Argument
    private void resizeMap() {
        Coordinate coords[] = new Coordinate[markers.size()];
        for (int i = 0; i < coords.length; i++)
            coords[i] = markers.get(i).getPosition();

        if (coords.length > 1) {
        	    Extent wholeMap = Extent.forCoordinates(coords);
            mapPane.setExtent(wholeMap);
        } else {
        	    mapPane.setCenter(coords[0]);
        }

    }
    public void updateMyEvents() {
    		markers.clear();
    		myEventsPane.getChildren().clear();
		for(Event e: schedule) {
			myEventsPane.getChildren().add(new EventSlot(e));
			addMarker(e.getLocation());
		}
    }
   

	@Override
	public void start(Stage primaryStage) {
		int sceneWidth = 800, sceneHeight = 500;
		schedule = new Schedule();

		// Main pane that lies in primary stage
		BorderPane mainPane = new BorderPane();
		
		// Left-side map frame and details
        mapPane = new MapView();
        mapPane.setCenter(new Coordinate(38.98598265, -76.9483238568247));
        BorderPane.setAlignment(mapPane, Pos.CENTER);
        mainPane.setCenter(mapPane);
        
        mapPane.setZoom(18);
        mapPane.initialize();
        
        // Right-side calendar and events pane
		GridPane calPane = new GridPane();
		calPane.setAlignment(Pos.TOP_CENTER);
		calPane.setPrefSize(200, 500);
		mainPane.setLeft(calPane);

		// App name and events setting
		Label calStrLabel = new Label("Calendar Street");
		calStrLabel.setFont(Font.font("Cambria", 24));
		calStrLabel.setPrefWidth(calPane.getPrefWidth());
		calStrLabel.setAlignment(Pos.CENTER);
		Button myEventsButton = new Button("ME");
		Button nearbyEvButton = new Button("NEARBY");
		myEventsButton.setAlignment(Pos.CENTER);
		nearbyEvButton.setAlignment(Pos.CENTER);

		// Two events panes: my events and nearby events, for calendar pane
		myEventsPane = new VBox();
		myEventsPane.setPrefSize(calPane.getPrefWidth() - 10, sceneHeight - 86);
		ScrollPane myEvScrollPane = new ScrollPane(myEventsPane);
		VBox nearbyEventsPane = new VBox();
		nearbyEventsPane.getChildren().add(new Label("nearby events"));
		nearbyEventsPane.setPrefSize(calPane.getPrefWidth() - 10, sceneHeight - 86);
		ScrollPane nearbyEvScrollPane = new ScrollPane(nearbyEventsPane);

		// Toggles the two above event panes
		GridPane togglePane = new GridPane();
		togglePane.add(myEventsButton, 0, 0);
		togglePane.add(nearbyEvButton, 1, 0);
		myEventsButton.setPrefWidth(calPane.getPrefWidth()/2);
		myEventsButton.setOnAction(e -> {
            // Replaces nearby events pane with my events pane
			if(calPane.getChildren().contains(nearbyEvScrollPane)) {
				calPane.getChildren().remove(nearbyEvScrollPane);
				calPane.add(myEvScrollPane, 0, 2);
			}
		});
		nearbyEvButton.setPrefWidth(calPane.getPrefWidth()/2);
		nearbyEvButton.setOnAction(e -> {
            // Replaces my events pane with nearby events pane
			if(calPane.getChildren().contains(myEvScrollPane)) {
				calPane.getChildren().remove(myEvScrollPane);
				calPane.add(nearbyEvScrollPane, 0, 2);
			}
		});
		togglePane.setPrefWidth(calPane.getPrefWidth());
		togglePane.setAlignment(Pos.CENTER);
		
		// Add event button
		Button addEventButton = new Button("+");
		addEventButton.setPrefWidth(calPane.getPrefWidth());
		addEventButton.setAlignment(Pos.CENTER);
		addEventButton.setOnAction(e -> {
			new NewEventWindow(event -> {
				schedule.add(event);
				updateMyEvents();
			});
		});

		calPane.add(calStrLabel, 0, 0);
		calPane.add(togglePane, 0, 1);
		calPane.add(myEvScrollPane, 0, 2);
		calPane.add(addEventButton, 0, 3);

		Scene scene = new Scene(mainPane, sceneWidth, sceneHeight);
		primaryStage.setTitle("Calendar Street");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}