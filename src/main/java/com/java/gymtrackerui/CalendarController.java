package com.java.gymtrackerui;

import com.java.gymtrackerui.api.WorkoutApiClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    @FXML
    private Label monthLabel;

    @FXML
    private GridPane calendarGrid;

    private final WorkoutApiClient workoutApiClient = new WorkoutApiClient();

    private YearMonth currentMonth;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentMonth = YearMonth.now();
        renderCalendar();
    }

    @FXML
    private void handlePrevMonth() {
        currentMonth = currentMonth.minusMonths(1);
        renderCalendar();
    }

    @FXML
    private void handleNextMonth() {
        currentMonth = currentMonth.plusMonths(1);
        renderCalendar();
    }

    private void renderCalendar() {

        calendarGrid.getChildren().clear();
        monthLabel.setText(currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                + " " + currentMonth.getYear());

        List<String> workoutDates;
        try {
            workoutDates = workoutApiClient.getWorkoutDatesForMonth(
                    currentMonth.getYear(), currentMonth.getMonthValue()
            );
        } catch (Exception e) {
            workoutDates = List.of(); // empty - just show a blank calendar rather than crash
        }

        // Day-of-week headers
        String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int i = 0; i < 7; i++) {
            Label header = new Label(dayNames[i]);
            header.setStyle("-fx-font-weight: bold;");
            calendarGrid.add(header, i, 0);
        }

        LocalDate firstOfMonth = currentMonth.atDay(1);
        int startColumn = firstOfMonth.getDayOfWeek().getValue() - 1; // Monday = 0

        int daysInMonth = currentMonth.lengthOfMonth();
        int row = 1;
        int column = startColumn;

        for (int day = 1; day <= daysInMonth; day++) {

            LocalDate thisDate = currentMonth.atDay(day);
            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setPrefSize(35, 35);
            dayLabel.setStyle("-fx-alignment: center; -fx-border-color: #6a89a7; -fx-text-fill: #384959;");

            if (workoutDates.contains(thisDate.toString())) {
                dayLabel.setStyle(dayLabel.getStyle() +
                        " -fx-background-color: #384959; -fx-text-fill: white; -fx-font-weight: bold;");
            }

            calendarGrid.add(dayLabel, column, row);

            column++;
            if (column > 6) {
                column = 0;
                row++;
            }
        }
    }

    @FXML
    private void goBack() throws Exception {
        App.switchScene("dashboard.fxml");
    }
}