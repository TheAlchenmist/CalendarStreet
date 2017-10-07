import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;

public class CalendarStreetGUI extends Application{

	BorderPane mainPane;
	GridPane calPane, togglePane;
	VBox eventsPane;
	Button addEventButton;
	Label calStrLabel, myEventsLabel, nearbyEvLabel;
	ScrollPane scrollPane;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		int sceneWidth = 800, sceneHeight = 500;
		int verSpaceBetweenNodes = 8, horSpaceBetweenNodes = 8;
		
		mainPane = new BorderPane();
		calPane = new GridPane(); //pane for calendar toggling and event displaying
		calPane.setAlignment(Pos.TOP_CENTER);
		calPane.setPrefSize(200, 500);
		mainPane.setLeft(calPane);
		
		calStrLabel = new Label("Calendar Street");
		calStrLabel.setFont(Font.font("Cambria", 24));
		myEventsLabel = new Label("ME");
		nearbyEvLabel = new Label("NEARBY");
		myEventsLabel.setAlignment(Pos.CENTER);
		
		togglePane = new GridPane();
		togglePane.add(myEventsLabel, 0, 0);
		togglePane.add(nearbyEvLabel, 1, 0);
		togglePane.setHgap(20);
		togglePane.setPrefWidth(calPane.getPrefWidth());
		togglePane.setAlignment(Pos.CENTER);
		togglePane.setGridLinesVisible(true);
		
		eventsPane = new VBox();
		eventsPane.setPrefSize(calPane.getPrefWidth()-10, sceneHeight-100);
		scrollPane = new ScrollPane(eventsPane);
		
		addEventButton = new Button("+");
		addEventButton.setPrefWidth(calPane.getPrefWidth());
		
		calPane.add(calStrLabel, 0, 0);
		calPane.add(togglePane, 0, 1);
		calPane.add(scrollPane, 0, 2);
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
