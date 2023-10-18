package com.example.spring_jwt.service;

import com.example.spring_jwt.entities.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    Appointment createOrUpdateAppointment(Appointment requestBody);

    List<Appointment> getAppointmentsByDate(LocalDate date);

    List<Appointment> getAppointmentsByUserId(Integer userId);
}
