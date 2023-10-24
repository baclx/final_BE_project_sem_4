package com.example.spring_jwt.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDetail {
    private String doctorName;
    private String patientName;
    private LocalDateTime appointmentTime;
}
