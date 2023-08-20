package com.example.spring_jwt.service;


import com.example.spring_jwt.entities.MedicalRecord;
import com.example.spring_jwt.model.request.CreateMedicalRecord;

public interface MedicalRecordService {
    MedicalRecord createOrUpdateMedicalRecord(Integer medicalRecordId, CreateMedicalRecord requestBody);
}
