package com.example.spring_jwt.repository;

import com.example.spring_jwt.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Integer> {

//    @Query(value = "FROM MedicalRecord WHERE doctor.id =: doctorId AND Patient.id =: patientId")
//    MedicalRecord getMedicalRecordByDoctorIdAndPatientId(@Param("doctorId") String doctorId, @Param("patienId") String patientId);
}
