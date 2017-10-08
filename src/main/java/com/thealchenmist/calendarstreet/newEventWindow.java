import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
public class newEventWindow extends Stage{
	private Scene scene;
	private GridPane pane;
	private Label titleLabel, startTimeLabel, endTimeLabel, dateLabel, addressLabel;
	private TextField title, startTime, endTime, day, year ,address;
	private Button done, cancel;
	private final ComboBox month;
	public newEventWindow() {
		int sceneWidth = 361, sceneHeight = 200;
		int verSpaceBetweenNodes = 5, horSpaceBetweenNodes = 5;
		int paneBorderTop = 5, paneBorderRight = 10;
		int paneBorderBottom = 5, paneBorderLeft = 7;
		
		/* Setting pane properties */
		pane = new GridPane();
		pane.setHgap(horSpaceBetweenNodes);
		pane.setVgap(verSpaceBetweenNodes);
		pane.setPadding(new Insets(paneBorderTop, paneBorderRight, 
					    paneBorderBottom, paneBorderLeft));
		pane.getColumnConstraints().add(new ColumnConstraints(100));
		//pane.getColumnConstraints().add(new ColumnConstraints(150));
		pane.setPrefSize(sceneWidth, sceneHeight);
		
		titleLabel = new Label("Event Name:");
		title = new TextField();
		pane.add(titleLabel, 0, 0);
		pane.add(title, 1, 0);
		
		startTimeLabel = new Label("Start time:");
		startTime = new TextField();
		pane.add(startTimeLabel, 0, 1);
		pane.add(startTime, 1, 1);
		endTimeLabel = new Label("End time: ");
		endTime = new TextField();
		pane.add(endTimeLabel, 0, 2);
		pane.add(endTime, 1, 2);
		
		dateLabel = new Label("Date (m/d/y): ");
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Jan",
			        "Feb",
			        "Mar",
			        "Apr",
			        "May",
			        "June",
			        "Jul",
			        "Aug",
			        "Sept",
			        "Oct",
			        "Nov",
			        "Dec"
			    );
		month = new ComboBox(options);
		month.setVisibleRowCount(5);
		day = new TextField();
		day.setPrefWidth(75);
		year = new TextField();
		year.setPrefWidth(75);
		FlowPane dateSelect = new FlowPane();
		dateSelect.setHgap(5);
		dateSelect.getChildren().addAll(month,day,year);
		pane.add(dateLabel, 0, 3);
		pane.add(dateSelect, 1, 3);
		
		addressLabel = new Label("Address: ");
		address = new TextField();
		pane.add(addressLabel, 0, 4);
		pane.add(address, 1, 4);
		
		done = new Button("Done");
		cancel = new Button("Cancel");
		pane.add(done, 1, 5);
		pane.add(cancel, 0, 5);
		GridPane.setHalignment(done, HPos.CENTER);
		GridPane.setHalignment(cancel, HPos.CENTER);
		
		scene = new Scene(pane,sceneWidth,sceneHeight);
		this.setTitle("New Event");
		this.setScene(scene);
		this.show();
	}
}
