package com.example.javafx.service;

import com.calendarfx.model.Entry;
import com.example.javafx.rest.client.AppointmentClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor
public class EntryService {

    private final AppointmentClient appointmentClient = new AppointmentClient();

    public List<Entry<?>> getAllEntries() throws IOException, InterruptedException,RuntimeException {
            return appointmentClient.getAppointments();
    }

    public void delete(final String entryId) throws IOException, InterruptedException,RuntimeException {
        appointmentClient.deleteAppointmentById(entryId);
    }

    public void create(final Entry<?> entry) throws IOException, InterruptedException,RuntimeException {
        appointmentClient.createAppointment(entry);
    }
}
