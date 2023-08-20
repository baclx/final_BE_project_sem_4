package com.example.spring_jwt.repository;


import com.example.spring_jwt.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    @Query(value = "FROM Doctor d WHERE d.user.id = :userId")
    Doctor getDoctorByUserId(@Param("userId") Integer userId);

    @Query(value = "FROM Doctor d WHERE d.specialization.id = :specId")
    List<Doctor> getDoctorBySpecializationId(@Param("specId") Integer specId);
}
