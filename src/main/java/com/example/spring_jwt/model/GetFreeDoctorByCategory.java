package com.example.spring_jwt.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetFreeDoctorByCategory {
    private Integer categoryId;
    private LocalDateTime appointmentTime;
}
