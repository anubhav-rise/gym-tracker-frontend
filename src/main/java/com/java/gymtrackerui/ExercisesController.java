package com.java.gymtrackerui;

import com.java.gymtrackerui.api.ExerciseApiClient;
import com.java.gymtrackerui.model.Exercise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ExercisesController implements Initializable {

    @FXML
    private ListView<Exercise> exerciseListView;

    @FXML
    private Label statusLabel;

    private final ExerciseApiClient exerciseApiClient = new ExerciseApiClient();

    private final ObservableList<Exercise> exerciseData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exerciseListView.setItems(exerciseData);
        loadExercises();
    }

    private void loadExercises() {
        try {
            exerciseData.clear();
            exerciseData.addAll(exerciseApiClient.getVisibleExercises());
        } catch (Exception e) {
            statusLabel.setText("Failed to load exercises: " + e.getMessage());
        }
    }

    @FXML
    private void goBack() throws Exception {
        App.switchScene("dashboard.fxml");
    }
}