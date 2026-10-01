package com.java.gymtrackerui;

import com.java.gymtrackerui.api.GoalProgressApiClient;
import com.java.gymtrackerui.model.Goal;
import com.java.gymtrackerui.model.GoalProgress;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class GoalProgressController {

    @FXML
    private Label titleLabel;

    @FXML
    private ListView<GoalProgress> progressListView;

    @FXML
    private TextField dateField;

    @FXML
    private TextField valueField;

    @FXML
    private Label statusLabel;

    private final GoalProgressApiClient progressApiClient = new GoalProgressApiClient();

    private final ObservableList<GoalProgress> progressData = FXCollections.observableArrayList();

    private Goal goal;

    public void setGoal(Goal goal) {
        this.goal = goal;
        titleLabel.setText("Progress for: " + goal.getGoalType());
        progressListView.setItems(progressData);
        loadProgress();
    }

    private void loadProgress() {
        try {
            progressData.clear();
            progressData.addAll(progressApiClient.getProgress(goal.getGoalId()));
        } catch (Exception e) {
            statusLabel.setText("Failed to load progress: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddProgress() {

        try {
            GoalProgress progress = new GoalProgress();
            progress.setRecordedOn(dateField.getText());
            progress.setCurrentValue(Double.parseDouble(valueField.getText()));

            progressApiClient.addProgress(goal.getGoalId(), progress);

            dateField.clear();
            valueField.clear();
            statusLabel.setText("Progress logged!");

            loadProgress();

        } catch (Exception e) {
            statusLabel.setText("Failed to add progress: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteProgress() {

        GoalProgress selected = progressListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusLabel.setText("Select a progress entry to delete first.");
            return;
        }

        try {
            progressApiClient.deleteProgress(selected.getProgressId());
            statusLabel.setText("Progress entry deleted.");
            loadProgress();

        } catch (Exception e) {
            statusLabel.setText("Failed to delete progress: " + e.getMessage());
        }
    }

    @FXML
    private void goBack() throws Exception {
        App.switchScene("goals.fxml");
    }
}