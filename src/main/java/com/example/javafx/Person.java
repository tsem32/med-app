package com.example.javafx;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {

    private final String firstName;
    private final String lastName;
    private final String healthId;
    private final String fathersName;

}
