package com.example.spring_jwt.model.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponse {
    private String fullName;
    private String address;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private Integer id;
    private String height;
    private String weight;
    private String email;
    private String image;
}
