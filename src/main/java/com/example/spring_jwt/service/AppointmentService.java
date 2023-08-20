package com.example.spring_jwt.service;

import com.example.spring_jwt.entities.Appointment;

import java.util.Date;
import java.util.List;

public interface AppointmentService {
    Appointment createOrUpdateAppointment(Appointment requestBody);

    List<Appointment> getAppointmentsByDate(Date date);
}
