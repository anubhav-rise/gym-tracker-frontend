package com.java.gymtrackerui;

import com.java.gymtrackerui.api.AuthApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ForgotPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private Label tokenInfoLabel;

    @FXML
    private TextField tokenField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Label statusLabel;

    private final AuthApiClient authApiClient = new AuthApiClient();

    @FXML
    private void handleRequestToken() {

        try {
            String message = authApiClient.forgotPassword(emailField.getText());
            tokenInfoLabel.setText(message);

        } catch (Exception e) {
            statusLabel.setText("Failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleResetPassword() {

        try {
            authApiClient.resetPassword(tokenField.getText(), newPasswordField.getText());

            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Password reset! You can now log in.");

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Failed: " + e.getMessage());
        }
    }

    @FXML
    private void goBack() throws Exception {
        App.switchScene("login.fxml");
    }
}