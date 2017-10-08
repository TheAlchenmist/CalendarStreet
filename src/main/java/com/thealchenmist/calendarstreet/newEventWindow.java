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
import java.util.Calendar;

public class newEventWindow extends Stage{
	
	private Scene scene;
	private GridPane pane;
	private Label titleLabel, startTimeLabel, endTimeLabel, startDateLabel, endDateLabel, addressLabel;
	private TextField title, startTime, endTime, startDay, endDay, startYear, endYear, address;
	private Button done, cancel;
	private final ComboBox<String> startMonth, endMonth, amPmStart, amPmEnd;
	
	public newEventWindow() {
		int sceneWidth = 393, sceneHeight = 250;
		int verSpaceBetweenNodes = 5, horSpaceBetweenNodes = 5;
		int paneBorderTop = 5, paneBorderRight = 10;
		int paneBorderBottom = 5, paneBorderLeft = 7;
		Calendar now = Calendar.getInstance();
		
		/* Setting pane properties */
		pane = new GridPane();
		pane.setHgap(horSpaceBetweenNodes);
		pane.setVgap(verSpaceBetweenNodes);
		pane.setPadding(new Insets(paneBorderTop, paneBorderRight, 
					    paneBorderBottom, paneBorderLeft));
		pane.getColumnConstraints().add(new ColumnConstraints(130));
		//pane.getColumnConstraints().add(new ColumnConstraints(150));
		pane.setPrefSize(sceneWidth, sceneHeight);
		
		titleLabel = new Label("Event Name:");
		title = new TextField();
		pane.add(titleLabel, 0, 0);
		pane.add(title, 1, 0);
		
		ObservableList<String> opts = 
			    FXCollections.observableArrayList(
			        "AM",
			        "PM"
			    );
		amPmStart = new ComboBox<String>(opts);
		amPmStart.setVisibleRowCount(2);
		amPmStart.getSelectionModel().selectFirst();
		amPmEnd = new ComboBox<String>(opts);
		amPmEnd.setVisibleRowCount(2);
		amPmEnd.getSelectionModel().selectFirst();
		
		startTimeLabel = new Label("Start time:");
		FlowPane startPane = new FlowPane();
		startTime = new TextField();
		startTime.setPrefWidth(90);
		startPane.setHgap(5);
		startPane.getChildren().addAll(startTime, amPmStart);
		pane.add(startTimeLabel, 0, 1);
		pane.add(startPane, 1, 1);
		endTimeLabel = new Label("End time: ");
		FlowPane endPane = new FlowPane();
		endTime = new TextField();
		endTime.setPrefWidth(90);
		endPane.setHgap(5);
		endPane.getChildren().addAll(endTime, amPmEnd);
		pane.add(endTimeLabel, 0, 2);
		pane.add(endPane, 1, 2);
		
		startDateLabel = new Label("Start Date (m/d/y): ");
		endDateLabel = new Label("End Date (m/d/y): ");
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
		startMonth = new ComboBox<String>(options);
		startMonth.setVisibleRowCount(5);
		endMonth = new ComboBox<String>(options);
		endMonth.setVisibleRowCount(5);
		
		//get current month to set default month selection in combobox
		startMonth.getSelectionModel().select(now.get(Calendar.MONTH));
		endMonth.getSelectionModel().select(now.get(Calendar.MONTH));
		
		startDay = new TextField();
		startDay.setPrefWidth(75);
		
		endDay = new TextField();
		endDay.setPrefWidth(75);
		
		startYear = new TextField();
		startYear.setPrefWidth(75);
		
		endYear = new TextField();
		endYear.setPrefWidth(75);
		
		FlowPane startDateSelect = new FlowPane();
		startDateSelect.setHgap(5);
		startDateSelect.getChildren().addAll(startMonth,startDay,startYear);
		
		FlowPane endDateSelect = new FlowPane();
		endDateSelect.setHgap(5);
		endDateSelect.getChildren().addAll(endMonth,endDay,endYear);
		
		pane.add(startDateLabel, 0, 3);
		pane.add(startDateSelect, 1, 3);
		pane.add(endDateLabel, 0, 4);
		pane.add(endDateSelect, 1, 4);
		
		addressLabel = new Label("Address: ");
		address = new TextField();
		pane.add(addressLabel, 0, 5);
		pane.add(address, 1, 5);
		
		done = new Button("Done");
		cancel = new Button("Cancel");
		pane.add(done, 1, 6);
		pane.add(cancel, 0, 6);
		GridPane.setHalignment(done, HPos.CENTER);
		GridPane.setHalignment(cancel, HPos.CENTER);
		
		scene = new Scene(pane,sceneWidth,sceneHeight);
		this.setTitle("New Event");
		this.setScene(scene);
		this.show();
	}
}
