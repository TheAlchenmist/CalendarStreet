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

public class CalendarStreetGUI extends Application{

    MapView mapPane;
    List<Marker> markers;

    BorderPane mainPane;
    GridPane calPane;
    HBox togglePane;
    VBox eventsPane;
    Button addEventButton;
    Label calStrLabel, myEventsLabel, nearbyEvLabel;
    ScrollPane scrollPane;

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

        calPane = new GridPane(); //pane for calendar toggling and event displaying
        calPane.setAlignment(Pos.TOP_CENTER);
        calPane.setPrefSize(200, 500);
        mainPane.setLeft(calPane);

        calStrLabel = new Label("Calendar Street");
        calStrLabel.setFont(Font.font("Cambria", 24));
        myEventsLabel = new Label("ME");
        nearbyEvLabel = new Label("NEARBY");

        togglePane = new HBox();
        togglePane.getChildren().addAll(myEventsLabel, nearbyEvLabel);

        eventsPane = new VBox();
        scrollPane = new ScrollPane(eventsPane);

        addEventButton = new Button("+");

        calPane.add(calStrLabel, 0, 0);
        calPane.add(togglePane, 0, 1);
        calPane.add(scrollPane, 0, 2);
        calPane.add(addEventButton, 0, 3);

        Scene scene = new Scene(mainPane, sceneWidth, sceneHeight);
        primaryStage.setTitle("Interest Table Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}