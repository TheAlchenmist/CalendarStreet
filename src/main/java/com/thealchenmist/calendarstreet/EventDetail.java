import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventDetail extends Stage{

    private TextField title, startTime, endTime, startDay, endDay, startYear, endYear, address;
    private TextArea description;
    private Button done;
    private GridPane pane;
    private ObservableList<String> options1;
    private ObservableList<String> options2;
    private final ComboBox<String> startMonth;
    private final ComboBox<String> endMonth;
    private final ComboBox<String> amPmStart;
    private final ComboBox<String> amPmEnd;


    public EventDetail(List<Event> schedule, Event editing) {
        this(schedule);

        title.setText(editing.getName());

        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        startTime.setText(localDateFormat.format(editing.getStartTime()));
        endTime.setText(localDateFormat.format(editing.getEndTime()));

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(editing.getStartTime());
        startDay.setText("" + startCal.get(Calendar.DAY_OF_MONTH));
        startYear.setText("" + (startCal.get(Calendar.YEAR) - 2000));

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(editing.getEndTime());
        endDay.setText("" + endCal.get(Calendar.DAY_OF_MONTH));
        endYear.setText("" + (endCal.get(Calendar.YEAR) - 2000));

        address.setText(editing.getAddress());
        description.setText(editing.getDesc());

        Button delete = new Button("Delete");
        pane.getChildren().remove(done);
        HBox doneDelete = new HBox(65);
        doneDelete.setAlignment(Pos.CENTER);
        doneDelete.getChildren().addAll(done,delete);
        pane.add(doneDelete, 1, 7);

        delete.setOnAction(e -> {
            schedule.remove(editing.getId());
            this.close();
        });
        done.setOnAction(e -> {
            try {
                getEventData(editing);
                Database.updateEvent(editing.getId(), editing.getStartTime(), editing.getEndTime(),
                                     editing.getName(), editing.getDesc(), editing.getAddress(), editing.getLocation());
            } catch (IOException | org.json.simple.parser.ParseException | IllegalArgumentException e1) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Address not found.");
                alert.showAndWait();
                return;
            }

            this.close();
        });
    }

    public EventDetail(List<Event> schedule) {
        this.setResizable(false);

        int sceneWidth = 393, sceneHeight = 350;
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

        Label titleLabel = new Label("Event Name:");
        title = new TextField();

        Label descrLabel = new Label("Description:");
        description = new TextArea();
        description.setWrapText(true);

        ObservableList<String> opts1 =
                FXCollections.observableArrayList(
                    "AM",
                    "PM"
                );
        ObservableList<String> opts2 =
                FXCollections.observableArrayList(
                    "AM",
                    "PM"
                );
        amPmStart = new ComboBox<String>(opts1);
        amPmStart.setVisibleRowCount(2);
        amPmStart.getSelectionModel().selectFirst();
        amPmEnd = new ComboBox<String>(opts2);
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

        Label startDateLabel = new Label("Start Date (m/d/yy): ");
        Label endDateLabel = new Label("End Date (m/d/yy): ");
        options1 = FXCollections.observableArrayList(
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
        options2 = FXCollections.observableArrayList(
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
        startMonth = new ComboBox<String>(options1);
        startMonth.setVisibleRowCount(5);
        endMonth = new ComboBox<String>(options2);
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

        done = new Button("Done");
        done.setOnAction(e -> {
            try {
                Event event = new Event();
                getEventData(event);

                schedule.add(event);
                Database.insertEvent(event.getStartTime(), event.getEndTime(),
                                     event.getName(), event.getDesc(),
                                     event.getAddress(), event.getLocation());
            } catch (IOException | org.json.simple.parser.ParseException | IllegalArgumentException e1) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Address not found.");
                alert.showAndWait();
                return;
            }

            this.close();
        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            this.close();
        });

        pane.add(titleLabel, 0, 0);
        pane.add(title, 1, 0);
        pane.add(descrLabel, 0, 1);
        pane.add(description, 1, 1);
        pane.add(startTimeLabel, 0, 2);
        pane.add(startPane, 1, 2);
        pane.add(endTimeLabel, 0, 3);
        pane.add(endPane, 1, 3);
        pane.add(startDateLabel, 0, 4);
        pane.add(startDateSelect, 1, 4);
        pane.add(endDateLabel, 0, 5);
        pane.add(endDateSelect, 1, 5);
        pane.add(addressLabel, 0, 6);
        pane.add(address, 1, 6);
        pane.add(cancel, 0, 7);
        pane.add(done, 1, 7);

        GridPane.setHalignment(done, HPos.CENTER);
        GridPane.setHalignment(cancel, HPos.CENTER);

        Scene scene = new Scene(pane,sceneWidth,sceneHeight);
        this.setTitle("New Event");
        this.setScene(scene);
        this.show();
    }

    private void getEventData(Event modified) throws IOException, org.json.simple.parser.ParseException, IllegalArgumentException {
        Calendar cal = Calendar.getInstance();

        //startDate string
        String times[] = startTime.getText().split(":");
        cal.set(Integer.parseInt("20" + (Integer.parseInt(startYear.getText()) < 10 ? "0" + startYear.getText() : startYear.getText())),
                options1.indexOf(startMonth.getValue()),
                Integer.parseInt(startDay.getText()));
        int time = Integer.parseInt(times[0]);
        cal.set(Calendar.HOUR_OF_DAY, (amPmStart.getValue().equals("AM") ? time : time+12));
        cal.set(Calendar.MINUTE, (times.length > 1) ? Integer.parseInt(times[1]) : 0);

        Date startDate = cal.getTime();

        //endDate string
        times = endTime.getText().split(":");
        cal.set(Integer.parseInt("20" + (Integer.parseInt(endYear.getText()) < 10 ? "0" + endYear.getText() : endYear.getText())),
                options2.indexOf(endMonth.getValue()),
                Integer.parseInt(endDay.getText()));
        time = Integer.parseInt(times[0]);
        cal.set(Calendar.HOUR_OF_DAY, (amPmEnd.getValue().equals("AM") ? time : time+12));
        cal.set(Calendar.MINUTE, (times.length > 1) ? Integer.parseInt(times[1]) : 0);

        Date endDate = cal.getTime();

        modified.setStartTime(startDate);
        modified.setEndTime(endDate);
        modified.setName(title.getText());
        modified.setDesc(description.getText());
        modified.setAddress(address.getText());
        modified.setLocation(Event.geocode(address.getText()));
    }

}
