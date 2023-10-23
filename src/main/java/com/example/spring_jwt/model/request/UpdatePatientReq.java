package com.example.spring_jwt.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdatePatientReq {
    private int age;

    private String fullName;

    private String email;

    private String gender;

    private String image;

    private String height;

    private String weight;

    private String address;

    private String phoneNumber;

    private LocalDate dateOfBirth;
}
