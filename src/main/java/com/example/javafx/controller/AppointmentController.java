package com.example.javafx.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.example.javafx.service.EntryService;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AppointmentController {

    private final EntryService entryService = new EntryService();

    public CalendarView getCalendarView() {

        // Create a CalendarView instance
        CalendarView calendarView = new CalendarView();

        // Create a calendar
        Calendar<Entry<String>> calendar = new Calendar<>("Ημερολόγιο");

        loadEntries(calendar); //loads all entries to calendar

        // Add the calendar to CalendarView
        Collections.fill(calendarView.getCalendarSources().get(0).getCalendars(), calendar);
        calendarView.setShowAddCalendarButton(false);
        calendarView.showMonthPage();

        calendar.addEventHandler(event -> {
            Entry<?> entry = event.getEntry();

            if (event.isEntryRemoved()) {
                deleteEntry(entry.getId());
            }

            if (event.isEntryAdded()) {
                createEntry(entry);
            }

            // Save the new or updated event
            System.out.println(event.getEventType());
        });

        // Add the CalendarView to the center pane
        return calendarView;
    }

    private void loadEntries(Calendar<Entry<String>> calendar) {
        try {
            List<Entry<?>> appointments = entryService.getAllEntries();
            calendar.addEntries(appointments);
        } catch (IOException|InterruptedException|RuntimeException e) {
            showErrorAlert("Rest Call Error", "Failed to load appointments", e.getMessage());
        }
    }

    private void deleteEntry(final String entryId) {
        try {
            entryService.delete(entryId);
        } catch (IOException|InterruptedException|RuntimeException e) {
            showErrorAlert("Rest Call Error", "Failed to delete appointment", e.getMessage());
        }
    }

    private void createEntry(final Entry<?> entry) {
        try {
            entryService.create(entry);
        } catch (IOException|InterruptedException|RuntimeException e) {
            showErrorAlert("Rest Call Error", "Failed to create appointment", e.getMessage());
        }
    }


    // Method to display an alert box
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);  // AlertType.ERROR indicates it's an error dialog
        alert.setTitle(title);                     // Set the title of the alert
        alert.setHeaderText(header);               // Set the header of the alert
        alert.setContentText(content);             // Set the detailed message (content)

        alert.showAndWait();                       // Show the alert and wait for the user to dismiss it
    }
}
