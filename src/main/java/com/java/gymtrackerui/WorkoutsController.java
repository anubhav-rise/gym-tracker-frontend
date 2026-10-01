package com.java.gymtrackerui;

import com.java.gymtrackerui.api.WorkoutApiClient;
import com.java.gymtrackerui.model.Workout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkoutsController implements Initializable {

    @FXML
    private ListView<Workout> workoutListView;

    @FXML
    private TextField titleField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField durationField;

    @FXML
    private Label statusLabel;

    private final WorkoutApiClient workoutApiClient = new WorkoutApiClient();

    private final ObservableList<Workout> workoutData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        workoutListView.setItems(workoutData);
        loadWorkouts();
    }

    private void loadWorkouts() {
        try {
            workoutData.clear();
            workoutData.addAll(workoutApiClient.getMyWorkouts());
        } catch (Exception e) {
            statusLabel.setText("Failed to load workouts: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddWorkout() {

        try {
            Workout workout = new Workout();
            workout.setTitle(titleField.getText());
            workout.setWorkoutDate(dateField.getText());

            if (!durationField.getText().isBlank()) {
                workout.setDurationMinutes(Integer.parseInt(durationField.getText()));
            }

            workoutApiClient.createWorkout(workout);

            titleField.clear();
            dateField.clear();
            durationField.clear();
            statusLabel.setText("Workout added!");

            loadWorkouts(); // refresh the list

        } catch (Exception e) {
            statusLabel.setText("Failed to add workout: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteWorkout() {

        Workout selected = workoutListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusLabel.setText("Select a workout to delete first.");
            return;
        }

        boolean confirmed = DialogUtils.confirm(
                "Delete Workout",
                "Are you sure you want to delete this workout? This cannot be undone."
        );

        if (!confirmed) {
            return;
        }

        try {
            workoutApiClient.deleteWorkout(selected.getWorkoutId());
            statusLabel.setText("Workout deleted.");
            loadWorkouts();

        } catch (Exception e) {
            statusLabel.setText("Failed to delete workout: " + e.getMessage());
        }
    }

    @FXML
    private void goBack() throws Exception {
        App.switchScene("dashboard.fxml");
    }
}