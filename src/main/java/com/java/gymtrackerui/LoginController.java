package com.java.gymtrackerui;

import com.java.gymtrackerui.api.AuthApiClient;
import com.java.gymtrackerui.model.LoginResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    private final AuthApiClient authApiClient = new AuthApiClient();

    @FXML
    private void handleLogin() {

        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            LoginResponse response = authApiClient.login(email, password);

            Long userId = JwtUtils.extractUserId(response.getToken());

            Session.getInstance().setToken(response.getToken());
            Session.getInstance().setEmail(email);
            Session.getInstance().setUserId(userId);

            SessionStorage.save(response.getToken(), email, userId);

            App.switchScene("dashboard.fxml");

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Login failed: invalid email or password");
        }
    }
    @FXML
    private void goToRegister() throws Exception {
        App.switchScene("register.fxml");
    }

    @FXML
    private void goToForgotPassword() throws Exception {
        App.switchScene("forgot-password.fxml");
    }
}