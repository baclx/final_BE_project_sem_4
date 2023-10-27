package com.example.spring_jwt.model.response;

import com.example.spring_jwt.entities.Appointment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDetail {
    private String doctorName;
    private String patientName;
    private Appointment appointment;
}
