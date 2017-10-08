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
	
	private TextField title, startTime, endTime, startDay, endDay, startYear, endYear, address;
	
	public newEventWindow() {
		int sceneWidth = 393, sceneHeight = 250;
		int verSpaceBetweenNodes = 5, horSpaceBetweenNodes = 5;
		int paneBorderTop = 5, paneBorderRight = 10;
		int paneBorderBottom = 5, paneBorderLeft = 7;
		Calendar now = Calendar.getInstance();
		
		/* Setting pane properties */
		GridPane pane = new GridPane();
		pane.setHgap(horSpaceBetweenNodes);
		pane.setVgap(verSpaceBetweenNodes);
		pane.setPadding(new Insets(paneBorderTop, paneBorderRight, 
					    paneBorderBottom, paneBorderLeft));
		pane.getColumnConstraints().add(new ColumnConstraints(130));
		//pane.getColumnConstraints().add(new ColumnConstraints(150));
		pane.setPrefSize(sceneWidth, sceneHeight);
		
		Label titleLabel = new Label("Event Name:");
		title = new TextField();
		
		ObservableList<String> opts = 
			    FXCollections.observableArrayList(
			        "AM",
			        "PM"
			    );
		final ComboBox<String> amPmStart = new ComboBox<String>(opts);
		amPmStart.setVisibleRowCount(2);
		amPmStart.getSelectionModel().selectFirst();
		final ComboBox<String> amPmEnd = new ComboBox<String>(opts);
		amPmEnd.setVisibleRowCount(2);
		amPmEnd.getSelectionModel().selectFirst();
		
		Label startTimeLabel = new Label("Start time:");
		FlowPane startPane = new FlowPane();
		startTime = new TextField();
		startTime.setPrefWidth(90);
		startPane.setHgap(5);
		startPane.getChildren().addAll(startTime, amPmStart);
		
		Label endTimeLabel = new Label("End time: ");
		FlowPane endPane = new FlowPane();
		endTime = new TextField();
		endTime.setPrefWidth(90);
		endPane.setHgap(5);
		endPane.getChildren().addAll(endTime, amPmEnd);
		
		Label startDateLabel = new Label("Start Date (m/d/y): ");
		Label endDateLabel = new Label("End Date (m/d/y): ");
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
		final ComboBox<String> startMonth = new ComboBox<String>(options);
		startMonth.setVisibleRowCount(5);
		final ComboBox<String> endMonth = new ComboBox<String>(options);
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
		
		Label addressLabel = new Label("Address: ");
		address = new TextField();
		
		Button done = new Button("Done");
		done.setOnAction(e -> {
			
		});
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> {
			
		});
		
		pane.add(titleLabel, 0, 0);
		pane.add(title, 1, 0);
		pane.add(startTimeLabel, 0, 1);
		pane.add(startPane, 1, 1);
		pane.add(endTimeLabel, 0, 2);
		pane.add(endPane, 1, 2);
		pane.add(startDateLabel, 0, 3);
		pane.add(startDateSelect, 1, 3);
		pane.add(endDateLabel, 0, 4);
		pane.add(endDateSelect, 1, 4);
		pane.add(addressLabel, 0, 5);
		pane.add(address, 1, 5);
		pane.add(done, 1, 6);
		pane.add(cancel, 0, 6);
		
		GridPane.setHalignment(done, HPos.CENTER);
		GridPane.setHalignment(cancel, HPos.CENTER);
		
		Scene scene = new Scene(pane,sceneWidth,sceneHeight);
		this.setTitle("New Event");
		this.setScene(scene);
		this.show();
	}
	
	
}
