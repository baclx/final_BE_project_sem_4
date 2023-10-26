package com.example.spring_jwt.model.response;

import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.Patient;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDetail {
    private String fullName;
    private String email;
    private List<String> roleNames;
    private Integer age;

    private String image;

    private String height;

    private String weight;

    private String address;

    private String phoneNumber;

    private String gender;

    private LocalDate dateOfBirth;
}
