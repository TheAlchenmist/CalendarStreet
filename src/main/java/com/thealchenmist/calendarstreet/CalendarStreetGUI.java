import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.Marker.Provided;

public class CalendarStreetGUI extends Application {
    
    MapView mapPane;
    List<Marker> markers = new ArrayList<Marker>();
    Schedule schedule;
    private VBox myEventsPane;

    public Marker addMarker(Coordinate position, String title) {
        Marker newMarker = Marker.createProvided(Provided.RED)
                                 .setPosition(position)
                                 .setVisible(true);
        
        MapLabel label = new MapLabel(title, 10, -10);
        label.setVisible(true);
        newMarker.attachLabel(label); //this works in my head but not irl idk 
        markers.add(newMarker);
        mapPane.addMarker(newMarker);
        
        return newMarker;
    }
    Marker tempMarker = Marker.createProvided(Provided.BLUE);
    public void displayTempMarker(Coordinate position) {
        tempMarker.setPosition(position)
                  .setVisible(true);
    }
    public void hideTempMarker() {
        tempMarker.setVisible(false);
    }

    public void detachMarker(Marker marker) {
        marker.detachLabel();
        mapPane.removeMarker(marker);
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

        if (mapPane.getZoom() < 14) mapPane.setZoom(14);

    }
    public void updateMyEvents() {
        System.out.println("Updating");
        // First delete all of the events on the map right now
        Iterator<Marker> markerIter = markers.iterator();
        Marker currMarker;
        while (markerIter.hasNext()) {
            currMarker = markerIter.next();
            detachMarker(currMarker);
            markerIter.remove();
        }
        markers.clear();
        for (int i = 0; i < myEventsPane.getChildren().size(); i++)
            myEventsPane.getChildren().remove(i);
        myEventsPane.getChildren().clear();

        // Then re-populate the events and add markers too.
		for (int i = 0; i < schedule.size(); i++) {
			EventSlot eventSlot = new EventSlot(schedule.get(i));
			eventSlot.setOnMouseClicked(e -> {
			    Event oldEvent = ((EventSlot)e.getSource()).event;
				new EventDetail(schedule, oldEvent).setOnHiding(f -> updateMyEvents());
			});
            eventSlot.setOnMouseEntered(e -> {
                displayTempMarker(eventSlot.event.getLocation());
            });
            eventSlot.setOnMouseExited(e -> {
                hideTempMarker();
			});
			myEventsPane.getChildren().add(eventSlot);
			addMarker(schedule.get(i).getLocation(),schedule.get(i).getName());
		}
        resizeMap();
    }

	@Override
	public void start(Stage primaryStage) {
		Database.connectToDatabase();
		Database.createNewTable();
		schedule = new Schedule();

		int sceneWidth = 800, sceneHeight = 500;

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
		Image logo = new Image("MapUrDayLogo.png");
		ImageView iv = new ImageView();
		iv.setFitHeight(71);
		iv.setFitWidth(200);
		iv.setImage(logo);
		/*Label calStrLabel = new Label("Calendar Street");
		calStrLabel.setFont(Font.font("Cambria", 24));
		calStrLabel.setPrefWidth(calPane.getPrefWidth());
		calStrLabel.setAlignment(Pos.CENTER);*/
		Button myEventsButton = new Button("ME");
		Button nearbyEvButton = new Button("NEARBY");
		myEventsButton.setAlignment(Pos.CENTER);
		nearbyEvButton.setAlignment(Pos.CENTER);

		// Two events panes: my events and nearby events, for calendar pane
		myEventsPane = new VBox();
		myEventsPane.setPrefSize(calPane.getPrefWidth() - 10, sceneHeight - 128);
		ScrollPane myEvScrollPane = new ScrollPane(myEventsPane);
		VBox nearbyEventsPane = new VBox();
		nearbyEventsPane.getChildren().add(new Label("nearby events"));
		nearbyEventsPane.setPrefSize(calPane.getPrefWidth() - 10, sceneHeight - 128);
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
			new EventDetail(schedule).setOnHiding(f -> updateMyEvents());
		});

		calPane.add(iv, 0, 0);
		calPane.add(togglePane, 0, 1);
		calPane.add(myEvScrollPane, 0, 2);
		calPane.add(addEventButton, 0, 3);
		
		mapPane.initializedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    mapPane.addMarker(tempMarker);
                    updateMyEvents();
                }
            }
		});

		Scene scene = new Scene(mainPane, sceneWidth, sceneHeight);
		primaryStage.setTitle("MapUrDay");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}