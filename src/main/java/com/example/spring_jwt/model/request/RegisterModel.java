package com.example.spring_jwt.model.request;

import lombok.Data;

@Data
public class RegisterModel {
    private String email;

    private String password;

    private String fullName;
}
