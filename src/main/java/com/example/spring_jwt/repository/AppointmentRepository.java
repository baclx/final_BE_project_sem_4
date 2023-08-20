package com.example.spring_jwt.repository;

import com.example.spring_jwt.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT COUNT (a) FROM Appointment a WHERE a.doctor.id= :doctorId AND a.createdAt = :date")
    Integer countAppointmentByDoctorIdAndDate(@Param("doctorId") Integer doctorId, @Param("date") Date date);

    @Query(value = "FROM Appointment a WHERE a.appointmentTime = :date AND a.isDeleted = 0")
    List<Appointment> getAppointmentByAppointmentTime(@Param("date") Date date);
}
