package com.example.spring_jwt.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class MedicalRecord {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private String medicalHistory;

    private String images;

    private String testResult;

    private String currentCondition;

    private String noteFromDoctor;

    private String diseaseProgression;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String medicationDetails;

    private Integer isDeleted;

    private String biochemicalTests;

    private String imageAnalysation;



}
