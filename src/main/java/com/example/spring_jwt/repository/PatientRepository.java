package com.example.spring_jwt.repository;


import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "FROM Patient p WHERE p.user.id = :userId")
    Patient getPatientByUserId(@Param("userId") Integer userId);

}
