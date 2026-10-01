package com.java.gymtrackerui;

import com.java.gymtrackerui.api.AuthApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    private final AuthApiClient authApiClient = new AuthApiClient();

    @FXML
    private void handleRegister() {

        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            authApiClient.register(fullName, email, password);

            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Registered successfully! Redirecting to login...");

            App.switchScene("login.fxml");

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Registration failed: " + e.getMessage());
        }
    }

    @FXML
    private void goToLogin() throws Exception {
        App.switchScene("login.fxml");
    }
}