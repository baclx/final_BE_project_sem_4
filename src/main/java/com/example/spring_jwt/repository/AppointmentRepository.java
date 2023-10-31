package com.example.spring_jwt.repository;

import com.example.spring_jwt.entities.Appointment;
import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT COUNT (a) FROM Appointment a WHERE a.doctor.id= :doctorId AND a.createdAt = :date")
    Integer countAppointmentByDoctorIdAndDate(@Param("doctorId") Integer doctorId, @Param("date") Date date);

    @Query(value = "FROM Appointment a WHERE a.appointmentTime = :date AND a.isDeleted = 0")
    List<Appointment> getAppointmentByAppointmentTime(@Param("date") LocalDateTime date);

    //@Query(value = "SELECT * FROM Appointment a WHERE CAST(appointment_time AS DATE) = DATE(NOW())", nativeQuery = true)
    @Query("SELECT a FROM Appointment a WHERE FUNCTION('DATE', a.appointmentTime) = FUNCTION('DATE', :todayDate) AND a.isCheck = 0")
    List<Appointment> findAppointmentsForToday(LocalDate todayDate);




    @Query("SELECT a FROM Appointment a WHERE a.doctor.user.id = :userId OR a.patient.user.id = :userId ORDER BY a.appointmentTime DESC")
    List<Appointment> getAppointmentByUserId(@Param("userId") Integer userId);

    List<Appointment> getAppointmentByDoctor(Doctor doctor);

    List<Appointment> getAppointmentByPatient(Patient patient);


}
