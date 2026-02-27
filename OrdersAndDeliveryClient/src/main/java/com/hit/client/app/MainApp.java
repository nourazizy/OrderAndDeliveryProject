package com.hit.client.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // טעינת קובץ העיצוב של המסך
        Parent root = FXMLLoader.load(getClass().getResource("/MainDashboard.fxml"));

        primaryStage.setTitle("Order & Delivery Management System");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}