package com.example.spring_jwt.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Specialization {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    private String specName;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Doctor> doctors;


}
