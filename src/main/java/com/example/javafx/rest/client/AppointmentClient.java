package com.example.javafx.rest.client;

import com.calendarfx.model.Entry;
import com.example.javafx.rest.dto.EntryDTO;
import com.example.javafx.rest.mapper.EntryMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AppointmentClient {

    private static final String API_URL = "http://localhost:8080/appointments"; // Update with your API URL
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AppointmentClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();

        // Register the JavaTimeModule to handle Java 8 date/time types
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public List<Entry<?>> getAppointments() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Deserialize JSON into EntryDto list
            List<EntryDTO> entryDTOS = objectMapper.readValue(response.body(), new TypeReference<List<EntryDTO>>() {
            });
            // Convert EntryDto to Entry
            return EntryMapper.mapToEntryList(entryDTOS);
        } else {
            throw new RuntimeException("Failed to fetch appointments. Status code: " + response.statusCode());
        }
    }

    public void deleteAppointmentById(final String appointmentId) throws IOException, InterruptedException {
        // Build the DELETE request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + appointmentId))
                .DELETE()
                .header("Accept", "application/json")
                .build();

        // Send the request
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Check the response code
        if (response.statusCode() != 204) {
            throw new RuntimeException("Failed to delete appointment. Status code: " + response.statusCode());
        }
    }

    public void createAppointment(final Entry<?> appointment) throws IOException, InterruptedException {
        // Convert the appointment object to JSON
        EntryDTO dto = EntryMapper.toDTO(appointment);
        String appointmentJson = objectMapper.writeValueAsString(dto);

        // Build the POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(appointmentJson))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();

        // Send the request
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Check the response code
        if (response.statusCode() != 201) {
            throw new RuntimeException("Failed to create appointment. Status code: " + response.statusCode());
        }

        // Optionally handle the response body
        System.out.println("Appointment created successfully: " + response.body());
    }
}
