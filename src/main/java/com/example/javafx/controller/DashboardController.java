package com.example.javafx.controller;

import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private StackPane centerPane;
    @FXML
    private Button patientsBtn;
    @FXML
    private Button appointmentsBtn;
    @FXML
    private Button settingsBtn;

    private final AppointmentController appointmentController = new AppointmentController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Call showScreen2 when the controller is initialized
        showAppointmentsScreen();
    }

    @FXML
    public void showPatientsScreen() {
        try {
            setActiveButton(patientsBtn);

            // Load the separate FXML for Screen 1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patients.fxml"));
            Node patientsScreen = loader.load();

            // Get the controller of the patients.fxml
            PatientsController patientsController = loader.getController();
            patientsController.setCenterPane(centerPane);

            centerPane.getChildren().clear();
            centerPane.getChildren().add(patientsScreen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showAppointmentsScreen() {
        setActiveButton(appointmentsBtn);
        centerPane.getChildren().clear();

        // Add the CalendarView to the center pane
        final CalendarView calendarView = appointmentController.getCalendarView();
        centerPane.getChildren().add(calendarView);

    }

    @FXML
    public void showSettingsScreen() {
        setActiveButton(settingsBtn);

        centerPane.getChildren().clear();
        centerPane.getChildren().add(new Text("This is Screen 3"));
    }

    private void setActiveButton(Button activeButton) {
        // Remove active class from all buttons
        patientsBtn.getStyleClass().remove("active");
        appointmentsBtn.getStyleClass().remove("active");
        settingsBtn.getStyleClass().remove("active");

        // Add active class to the selected button
        activeButton.getStyleClass().add("active");
    }
}
