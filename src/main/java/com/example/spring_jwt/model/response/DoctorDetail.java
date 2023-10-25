package com.example.spring_jwt.model.response;

import lombok.Data;

@Data
public class DoctorDetail {
    private Integer id;
    private String image;
    private String fullName;
    private String gender;
    private int age;
    private String specName;
    private String phoneNumber;
}
