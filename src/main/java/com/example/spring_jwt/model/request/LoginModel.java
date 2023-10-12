package com.example.spring_jwt.model.request;

import lombok.Data;

@Data
public class LoginModel {
    private String password;
    private String username;
}
