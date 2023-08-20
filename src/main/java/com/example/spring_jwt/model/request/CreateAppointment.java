package com.example.spring_jwt.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class CreateAppointment {
    private Integer doctorId;
    private Integer patientId;
    private String patientName;
    private String phoneNumber;
    private Date appointmentTime;
    private String purpose;
}
