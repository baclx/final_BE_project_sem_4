package com.example.spring_jwt.repository;

import com.example.spring_jwt.entities.MedicalRecord;
import com.example.spring_jwt.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

//    @Query(value = "FROM MedicalRecord WHERE doctor.id =: doctorId AND Patient.id =: patientId")
//    MedicalRecord getMedicalRecordByDoctorIdAndPatientId(@Param("doctorId") String doctorId, @Param("patienId") String patientId);


    @Query(value = "FROM MedicalRecord m WHERE m.patient.user.id =: userId ")
    List<MedicalRecord> getByUserId(@Param("userId") Integer userId);

    List<MedicalRecord> getAllByPatient(Patient patient);


}
