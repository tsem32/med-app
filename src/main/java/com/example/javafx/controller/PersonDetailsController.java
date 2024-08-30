package com.example.javafx.controller;

import com.example.javafx.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PersonDetailsController {

    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField fathersNameText;
    @FXML
    private TextField healthIdText;

    public void initialize() {
        // Initialization logic, if any
    }

    public void setPerson(Person person) {
        firstNameText.setText( person.getFirstName());
        lastNameText.setText(person.getLastName());
        healthIdText.setText(person.getHealthId());
        fathersNameText.setText(person.getFathersName());
    }
}
