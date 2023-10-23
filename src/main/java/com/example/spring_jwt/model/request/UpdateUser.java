package com.example.spring_jwt.model.request;

import lombok.Data;

@Data
public class UpdateUser {
    private String fullName;
    private String email;
    private String roles;
}
