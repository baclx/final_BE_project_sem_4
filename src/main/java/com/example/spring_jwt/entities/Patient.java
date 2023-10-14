package com.example.spring_jwt.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Patient {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    private int age;

    private String email;

    private String gender;

    private String image;

    private String height;

    private String weight;

    private String address;

    private String phoneNumber;

    private Date dateOfBirth;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @CreationTimestamp
    private Date createdAt;

    private String fullName;

    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<MedicalRecord> medicalRecords;

    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Appointment> appointments;

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", geder='" + gender + '\'' +
                ", image='" + image + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
