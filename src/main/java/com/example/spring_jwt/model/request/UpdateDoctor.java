package com.example.spring_jwt.model.request;

import com.example.spring_jwt.entities.Specialization;
import lombok.Data;

@Data
public class UpdateDoctor {
    private String fullName;
    private Specialization specialization;
}
