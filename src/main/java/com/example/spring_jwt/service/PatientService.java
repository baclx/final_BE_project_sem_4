package com.example.spring_jwt.service;


import com.example.spring_jwt.entities.Patient;

public interface PatientService {
    Patient getPatientById(Integer patientId);

    Patient savePatient(Patient patient);

    Patient getPatientByEmail(String email);

    Patient getPatientByUserId(Integer userId);


}
