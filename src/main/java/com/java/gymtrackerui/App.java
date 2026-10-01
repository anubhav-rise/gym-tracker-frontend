package com.java.gymtrackerui;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("Gym Progress Tracker");

        if (SessionStorage.load() && isSessionValid()) {
            switchScene("dashboard.fxml");
        } else {
            SessionStorage.clear();
            Session.getInstance().clear();
            switchScene("login.fxml");
        }

        primaryStage.show();
    }

    private boolean isSessionValid() {
        try {
            new com.java.gymtrackerui.api.DashboardApiClient().getDashboard();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void switchScene(String fxmlFile) throws Exception {
        Parent root = loadFxml(fxmlFile);
        applySceneWithFade(root);
    }

    public static <T> T switchSceneAndGetController(String fxmlFile) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                App.class.getResource("/com/java/gymtrackerui/" + fxmlFile)
        );
        Parent root = loader.load();
        applySceneWithFade(root);
        return loader.getController();
    }

    private static Parent loadFxml(String fxmlFile) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                App.class.getResource("/com/java/gymtrackerui/" + fxmlFile)
        );
        return loader.load();
    }

    private static void applySceneWithFade(Parent root) {

        Scene scene = new Scene(root, 800, 620);
        scene.getStylesheets().add(
                App.class.getResource("/com/java/gymtrackerui/style.css").toExternalForm()
        );

        root.setOpacity(0);
        primaryStage.setScene(scene);

        FadeTransition fade = new FadeTransition(Duration.millis(250), root);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}