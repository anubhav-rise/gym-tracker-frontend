package com.java.gymtrackerui;

import com.java.gymtrackerui.api.DashboardApiClient;
import com.java.gymtrackerui.model.DashboardData;
import com.java.gymtrackerui.model.WorkoutSummary;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label totalWorkoutsLabel;

    @FXML
    private Label totalExercisesLabel;

    @FXML
    private Label recentWorkoutLabel;

    private final DashboardApiClient dashboardApiClient = new DashboardApiClient();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String email = Session.getInstance().getEmail();
        welcomeLabel.setText("Welcome, " + email + "!");

        try {
            DashboardData data = dashboardApiClient.getDashboard();

            totalWorkoutsLabel.setText(String.valueOf(data.getTotalWorkouts()));
            totalExercisesLabel.setText(String.valueOf(data.getTotalExercisesPerformed()));

            WorkoutSummary recent = data.getRecentWorkout();
            if (recent != null) {
                recentWorkoutLabel.setText(
                        (recent.getTitle() != null ? recent.getTitle() : "Untitled") +
                                " on " + recent.getWorkoutDate() +
                                (recent.getDurationMinutes() != null ? " (" + recent.getDurationMinutes() + " min)" : "")
                );
            } else {
                recentWorkoutLabel.setText("No workouts logged yet — get started!");
            }

        } catch (Exception e) {
            totalWorkoutsLabel.setText("—");
            totalExercisesLabel.setText("—");
            recentWorkoutLabel.setText("Couldn't load dashboard data.");
        }
    }

    @FXML
    private void handleLogout() throws Exception {
        Session.getInstance().clear();
        SessionStorage.clear();
        App.switchScene("login.fxml");
    }

    @FXML
    private void goToWorkouts() throws Exception {
        App.switchScene("workouts.fxml");
    }

    @FXML
    private void goToCalendar() throws Exception {
        App.switchScene("calendar.fxml");
    }

    @FXML
    private void goToGoals() throws Exception {
        App.switchScene("goals.fxml");
    }

    @FXML
    private void goToMeasurements() throws Exception {
        App.switchScene("measurements.fxml");
    }

    @FXML
    private void goToExercises() throws Exception {
        App.switchScene("exercises.fxml");
    }

    @FXML
    private void goToProfile() throws Exception {
        App.switchScene("profile.fxml");
    }
}