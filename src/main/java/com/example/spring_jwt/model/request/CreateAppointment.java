package com.example.spring_jwt.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointment {
    private Integer doctorId;
    private Integer patientId;
    //private String patientName;
    private String phoneNumber;
    private LocalDateTime appointmentTime;
    private String purpose;
}
