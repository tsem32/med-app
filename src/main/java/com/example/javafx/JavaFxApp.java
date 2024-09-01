package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class JavaFxApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Locale greekLocale = new Locale("el", "GR");
        Locale.setDefault(greekLocale);
        System.setProperty("file.encoding", "UTF-8");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("Dash Board");
        primaryStage.setScene(new Scene(root, 1200, 800));  // Adjust size as needed
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
