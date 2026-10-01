package com.java.gymtrackerui;

import com.java.gymtrackerui.GoalProgressController;
import com.java.gymtrackerui.api.GoalApiClient;
import com.java.gymtrackerui.model.Goal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GoalsController implements Initializable {

    @FXML
    private ListView<Goal> goalListView;

    @FXML
    private ComboBox<String> goalTypeCombo;

    @FXML
    private TextField baselineField;

    @FXML
    private TextField targetField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField targetDateField;

    @FXML
    private Label statusLabel;

    private final GoalApiClient goalApiClient = new GoalApiClient();

    private final ObservableList<Goal> goalData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goalListView.setItems(goalData);
        goalTypeCombo.getItems().addAll("WEIGHT_LOSS", "WEIGHT_GAIN", "MUSCLE_GAIN");
        loadGoals();
    }

    private void loadGoals() {
        try {
            goalData.clear();
            goalData.addAll(goalApiClient.getMyGoals());
        } catch (Exception e) {
            statusLabel.setText("Failed to load goals: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddGoal() {

        try {
            Goal goal = new Goal();
            goal.setGoalType(goalTypeCombo.getValue());
            goal.setBaselineValue(Double.parseDouble(baselineField.getText()));
            goal.setTargetValue(Double.parseDouble(targetField.getText()));
            goal.setStartDate(startDateField.getText());
            goal.setTargetDate(targetDateField.getText());

            goalApiClient.createGoal(goal);

            goalTypeCombo.setValue(null);
            baselineField.clear();
            targetField.clear();
            startDateField.clear();
            targetDateField.clear();
            statusLabel.setText("Goal added!");

            loadGoals();

        } catch (Exception e) {
            statusLabel.setText("Failed to add goal: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteGoal() {

        Goal selected = goalListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusLabel.setText("Select a goal to delete first.");
            return;
        }

        boolean confirmed = DialogUtils.confirm(
                "Delete Goal",
                "Are you sure you want to delete this goal? All its progress history will be deleted too."
        );

        if (!confirmed) {
            return;
        }

        try {
            goalApiClient.deleteGoal(selected.getGoalId());
            statusLabel.setText("Goal deleted.");
            loadGoals();

        } catch (Exception e) {
            statusLabel.setText("Failed to delete goal: " + e.getMessage());
        }
    }

    @FXML
    private void handleViewProgress() throws Exception {

        Goal selected = goalListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusLabel.setText("Select a goal to view its progress.");
            return;
        }

        GoalProgressController controller =
                App.switchSceneAndGetController("goal-progress.fxml");
        controller.setGoal(selected);
    }

    @FXML
    private void goBack() throws Exception {
        App.switchScene("dashboard.fxml");
    }
}