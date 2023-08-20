package com.example.spring_jwt.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Appointment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String patientName;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private Date appointmentTime;
    private String purpose;

    private Integer isCheck;

    private Integer isDeleted;

    @CreationTimestamp
    private Date createdAt;

}
