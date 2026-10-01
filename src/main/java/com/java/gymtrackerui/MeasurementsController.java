package com.java.gymtrackerui;

import com.java.gymtrackerui.api.BodyMeasurementApiClient;
import com.java.gymtrackerui.model.BodyMeasurement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MeasurementsController implements Initializable {

    @FXML
    private ListView<BodyMeasurement> measurementListView;

    @FXML
    private TextField dateField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField waistField;

    @FXML
    private TextField chestField;

    @FXML
    private Label statusLabel;

    private final BodyMeasurementApiClient measurementApiClient = new BodyMeasurementApiClient();

    private final ObservableList<BodyMeasurement> measurementData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        measurementListView.setItems(measurementData);
        loadMeasurements();
    }

    private void loadMeasurements() {
        try {
            measurementData.clear();
            measurementData.addAll(measurementApiClient.getMyMeasurements());
        } catch (Exception e) {
            statusLabel.setText("Failed to load measurements: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddMeasurement() {

        try {
            BodyMeasurement measurement = new BodyMeasurement();
            measurement.setMeasuredOn(dateField.getText());
            measurement.setWeightKg(Double.parseDouble(weightField.getText()));

            if (!waistField.getText().isBlank()) {
                measurement.setWaistCm(Double.parseDouble(waistField.getText()));
            }
            if (!chestField.getText().isBlank()) {
                measurement.setChestCm(Double.parseDouble(chestField.getText()));
            }

            measurementApiClient.createMeasurement(measurement);

            dateField.clear();
            weightField.clear();
            waistField.clear();
            chestField.clear();
            statusLabel.setText("Measurement added!");

            loadMeasurements();

        } catch (Exception e) {
            statusLabel.setText("Failed to add measurement: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteMeasurement() {

        BodyMeasurement selected = measurementListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusLabel.setText("Select a measurement to delete first.");
            return;
        }

        boolean confirmed = DialogUtils.confirm(
                "Delete Measurement",
                "Are you sure you want to delete this measurement?"
        );

        if (!confirmed) {
            return;
        }

        try {
            measurementApiClient.deleteMeasurement(selected.getMeasurementId());
            statusLabel.setText("Measurement deleted.");
            loadMeasurements();

        } catch (Exception e) {
            statusLabel.setText("Failed to delete measurement: " + e.getMessage());
        }
    }

    @FXML
    private void goBack() throws Exception {
        App.switchScene("dashboard.fxml");
    }
}