package com.example.javafx.controller;

import com.example.javafx.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Setter;

import java.io.IOException;
import java.util.stream.Collectors;

public class PatientsController {

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TableColumn<Person, String> fathersNameColumn;

    @FXML
    private TableColumn<Person, String> healthIdColumn;
    @FXML
    private TextField searchField;

    @Setter
    private StackPane centerPane;

    @FXML
    public void initialize() {
        personTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Link columns to Person properties
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        healthIdColumn.setCellValueFactory(new PropertyValueFactory<>("healthId"));
        fathersNameColumn.setCellValueFactory(new PropertyValueFactory<>("fathersName"));

        // Add a listener to handle double-click events on rows
        personTableView.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();

            addRightClickMenu(row);

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Person person = row.getItem();
                    showPersonDetails(person);
                }
            });

            return row;
        });

        // Load data into the table
        personTableView.setItems(getPersonList());
    }

    public void filterPatients() {

        if (searchField.getText().trim().isEmpty()) {
            personTableView.setItems(getPersonList());
        }
        ObservableList<Person> collect = personTableView.getItems().stream()
                .filter(person -> person.getHealthId().contains(searchField.getText()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        personTableView.setItems(collect);
    }

    private void showPersonDetails(Person person) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/person-details.fxml"));

            VBox root = loader.load();

            // Get the controller and set the person
            PersonDetailsController controller = loader.getController();
            controller.setPerson(person);

            // Display the details in the center pane
            centerPane.getChildren().clear();
            centerPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception (e.g., show an error dialog)
        }
    }

    private void deletePerson(Person person) {
        // Implement the deletion logic here
        // Example: Remove from the list and update the TableView
        personTableView.getItems().remove(person);
    }

    private ObservableList<Person> getPersonList() {
        // Sample data for the table
        return FXCollections.observableArrayList(new Person("John", "Doe", "20049301011", "Father John"), new Person("Jane", "Doe", "20049301012", "Father Jane"), new Person("Michael", "Smith", "20049301013", "Father Mic"), new Person("Emily", "Johnson", "20049301014", "Father Em"));
    }


    //adds right click menu per table row
    private void addRightClickMenu(TableRow<Person> row) {
        ContextMenu contextMenu = getContextMenu(row);

        // Set context menu on row
        row.setOnContextMenuRequested(event -> {
            if (!row.isEmpty()) {
                contextMenu.show(row, event.getScreenX(), event.getScreenY());
            }
        });
    }

    private ContextMenu getContextMenu(TableRow<Person> row) {
        // Create context menu
        ContextMenu contextMenu = new ContextMenu();

        MenuItem showMenuItem = new MenuItem("Εμφάνιση");
        showMenuItem.setOnAction(event -> {
            if (!row.isEmpty()) {
                Person person = row.getItem();
                showPersonDetails(person);
            }
        });

        MenuItem deleteMenuItem = new MenuItem("Διαγραφή");
        deleteMenuItem.setOnAction(event -> {
            if (!row.isEmpty()) {
                Person person = row.getItem();
                // Implement deletion logic here
                deletePerson(person);
            }
        });

        contextMenu.getItems().addAll(showMenuItem, deleteMenuItem);
        return contextMenu;
    }
}
