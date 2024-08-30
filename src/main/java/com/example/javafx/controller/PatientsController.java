package com.example.javafx.controller;

import com.example.javafx.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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

    private void showPersonDetails(Person person) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/person-details.fxml"));

            VBox root = loader.load();

            // Get the controller and set the person
            PersonDetailsController controller = loader.getController();
            controller.setPerson(person);

            // Create a new stage for the details
            Stage stage = new Stage();
            stage.setTitle("Person Details");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception (e.g., show an error dialog)
        }
    }

    private ObservableList<Person> getPersonList() {
        // Sample data for the table
        return FXCollections.observableArrayList(
                new Person("John", "Doe", "20049301011", "Father John"),
                new Person("Jane", "Doe", "20049301012", "Father Jane"),
                new Person("Michael", "Smith", "20049301013", "Father Mic"),
                new Person("Emily", "Johnson", "20049301014", "Father Em")
        );
    }
}
