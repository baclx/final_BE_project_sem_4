package com.example.spring_jwt.model.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateUserReq {
    private String fullName;
    private String email;
    private String image;
    private String height;
    private String weight;
    private String address;
    private String phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
}
