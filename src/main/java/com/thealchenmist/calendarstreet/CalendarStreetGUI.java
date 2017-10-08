import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

import java.util.List;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.Marker.Provided;

public class CalendarStreetGUI extends Application {
    
    MapView mapPane;
    List<Marker> markers;

	private BorderPane mainPane;
	private GridPane calPane, togglePane;
	private VBox myEventsPane, nearbyEventsPane;
	private Button addEventButton,myEventsButton,nearbyEvButton;
	private Label calStrLabel;
	ScrollPane myEvScrollPane, nearbyEvScrollPane;
	
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

    private void resizeMap() {
        Coordinate coords[] = new Coordinate[markers.size()];
        for (int i = 0; i < coords.length; i++)
            coords[i] = markers.get(i).getPosition();
        Extent wholeMap = Extent.forCoordinates(coords);

        mapPane.setExtent(wholeMap);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		int sceneWidth = 800, sceneHeight = 500;
		int verSpaceBetweenNodes = 8, horSpaceBetweenNodes = 8;

		mainPane = new BorderPane();
		
        mapPane = new MapView();
        mapPane.setCenter(new Coordinate(38.98598265, -76.9483238568247));
        BorderPane.setAlignment(mapPane, Pos.CENTER);
        mainPane.setCenter(mapPane);
        
        mapPane.setZoom(18);
        mapPane.initialize();
		
		calPane = new GridPane(); // pane for calendar toggling and event displaying
		calPane.setAlignment(Pos.TOP_CENTER);
		calPane.setPrefSize(200, 500);
		mainPane.setLeft(calPane);

		calStrLabel = new Label("Calendar Street");
		calStrLabel.setFont(Font.font("Cambria", 24));
		calStrLabel.setPrefWidth(calPane.getPrefWidth());
		calStrLabel.setAlignment(Pos.CENTER);
		myEventsButton = new Button("ME");
		nearbyEvButton = new Button("NEARBY");
		myEventsButton.setAlignment(Pos.CENTER);
		nearbyEvButton.setAlignment(Pos.CENTER);

		togglePane = new GridPane();
		togglePane.add(myEventsButton, 0, 0);
		togglePane.add(nearbyEvButton, 1, 0);
		myEventsButton.setPrefWidth(calPane.getPrefWidth()/2);
		myEventsButton.setOnAction(e -> { //replaces nearby events pane with my events pane
			if(calPane.getChildren().contains(nearbyEvScrollPane)) {
				calPane.getChildren().remove(nearbyEvScrollPane);
				calPane.add(myEvScrollPane, 0, 2);
			}
		});
		nearbyEvButton.setPrefWidth(calPane.getPrefWidth()/2);
		nearbyEvButton.setOnAction(e -> { //replaces my events pane with nearby events pane
			if(calPane.getChildren().contains(myEvScrollPane)) {
				calPane.getChildren().remove(myEvScrollPane);
				calPane.add(nearbyEvScrollPane, 0, 2);
			}
		});
		togglePane.setPrefWidth(calPane.getPrefWidth());
		togglePane.setAlignment(Pos.CENTER);
		
		myEventsPane = new VBox();
		myEventsPane.getChildren().add(new Label("my events"));
		myEventsPane.setPrefSize(calPane.getPrefWidth() - 10, sceneHeight - 86);
		myEvScrollPane = new ScrollPane(myEventsPane);
		nearbyEventsPane = new VBox();
		nearbyEventsPane.getChildren().add(new Label("nearby events"));
		nearbyEventsPane.setPrefSize(calPane.getPrefWidth() - 10, sceneHeight - 86);
		nearbyEvScrollPane = new ScrollPane(nearbyEventsPane);

		addEventButton = new Button("+");
		addEventButton.setPrefWidth(calPane.getPrefWidth());
		addEventButton.setAlignment(Pos.CENTER);

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