package com.example.spring_jwt.model.request;


import lombok.Data;

import java.util.List;


@Data
public class CreateMedicalRecord {
    //private String patientEmail;
    private Integer patientId;
    private Integer doctorId;
    private String doctorName;
    //private String testResult;
    private String currentCondition;
    private String noteFromDoctor;
    private String diseaseProgression;
    private String medicationDetails;
    private List<String> files;
    private String biochemicalTests;
    private String imageAnalysation;
}
