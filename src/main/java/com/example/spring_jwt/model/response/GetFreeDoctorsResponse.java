package com.example.spring_jwt.model.response;

import com.example.spring_jwt.entities.Doctor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetFreeDoctorsResponse {
    private LocalDateTime appointmentTime;
    private List<Doctor> freeDoctors;
}
