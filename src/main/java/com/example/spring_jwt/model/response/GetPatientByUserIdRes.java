package com.example.spring_jwt.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class GetPatientByUserIdRes {
    private Integer id;
    private String height;
    private String weight;
    private String address;
    private String dateOfBirth;
    private Integer age;
    private String phoneNumber;
    private Date createdAt;
    private String fullName;
}
