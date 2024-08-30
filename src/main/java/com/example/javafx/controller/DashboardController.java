package com.example.javafx.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import com.example.javafx.rest.client.AppointmentClient;
import com.example.javafx.service.EntryService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private StackPane centerPane;

    private final AppointmentController appointmentController = new AppointmentController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Call showScreen2 when the controller is initialized
        showScreen2();
    }

    @FXML
    public void showScreen1() {
        try {
            // Load the separate FXML for Screen 1
            Node screen1 = FXMLLoader.load(getClass().getResource("/fxml/patients.fxml"));
            centerPane.getChildren().clear();
            centerPane.getChildren().add(screen1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showScreen2() {

        centerPane.getChildren().clear();

        // Add the CalendarView to the center pane
        final CalendarView calendarView = appointmentController.getCalendarView();
        centerPane.getChildren().add(calendarView);


    }

    @FXML
    public void showScreen3() {
        centerPane.getChildren().clear();
        centerPane.getChildren().add(new Text("This is Screen 3"));
    }

}
