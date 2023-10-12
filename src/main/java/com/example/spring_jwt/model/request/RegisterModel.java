package com.example.spring_jwt.model.request;

import lombok.Data;

@Data
public class RegisterModel {
    private String username;

    private String password;

    private String fullName;
}
