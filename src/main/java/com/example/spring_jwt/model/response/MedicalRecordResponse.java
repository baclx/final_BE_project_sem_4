package com.example.spring_jwt.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MedicalRecordResponse {
    private String doctorName;
    private String patientName;
    private LocalDateTime dateTime;
}
