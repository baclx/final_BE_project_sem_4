package com.example.spring_jwt.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Doctor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    private String fullName;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "spec_id")
    @JsonBackReference
    private Specialization specialization;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<MedicalRecord> medicalRecords;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Appointment> appointments;

    @CreationTimestamp
    private Date createdAt;

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
