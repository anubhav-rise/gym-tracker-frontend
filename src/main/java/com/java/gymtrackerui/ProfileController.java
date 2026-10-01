package com.java.gymtrackerui;

import com.java.gymtrackerui.api.ProfileApiClient;
import com.java.gymtrackerui.model.UpdateProfileRequest;
import com.java.gymtrackerui.model.UserProfile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Label emailLabel;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField dobField;

    @FXML
    private ComboBox<String> genderCombo;

    @FXML
    private TextField heightField;

    @FXML
    private Label statusLabel;

    private final ProfileApiClient profileApiClient = new ProfileApiClient();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        genderCombo.getItems().addAll("MALE", "FEMALE", "OTHER");

        try {
            UserProfile profile = profileApiClient.getMyProfile();

            emailLabel.setText(profile.getEmail());
            fullNameField.setText(profile.getFullName());
            dobField.setText(profile.getDateOfBirth());
            genderCombo.setValue(profile.getGender());

            if (profile.getHeightCm() != null) {
                heightField.setText(String.valueOf(profile.getHeightCm()));
            }

        } catch (Exception e) {
            statusLabel.setText("Failed to load profile: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {

        try {
            UpdateProfileRequest request = new UpdateProfileRequest();

            if (!fullNameField.getText().isBlank()) {
                request.setFullName(fullNameField.getText());
            }
            if (!dobField.getText().isBlank()) {
                request.setDateOfBirth(dobField.getText());
            }
            if (genderCombo.getValue() != null) {
                request.setGender(genderCombo.getValue());
            }
            if (!heightField.getText().isBlank()) {
                request.setHeightCm(Double.parseDouble(heightField.getText()));
            }

            profileApiClient.updateMyProfile(request);

            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Profile updated!");

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Failed to update profile: " + e.getMessage());
        }
    }

    @FXML
    private void goBack() throws Exception {
        App.switchScene("dashboard.fxml");
    }
}