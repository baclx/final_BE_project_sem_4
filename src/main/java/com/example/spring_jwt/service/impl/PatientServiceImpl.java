package com.example.spring_jwt.service.impl;


import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.repository.PatientRepository;
import com.example.spring_jwt.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public Patient getPatientById(Integer patientId){
        try {
            Patient patient = patientRepository.findById(patientId).get();
            if(patient != null){
                return patient;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Patient savePatient(Patient patient){
        try {
           return patientRepository.save(patient);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Patient getPatientByUserId(Integer userId) {
        try {
            Patient patient = patientRepository.getPatientByUserId(userId);
            return patient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
