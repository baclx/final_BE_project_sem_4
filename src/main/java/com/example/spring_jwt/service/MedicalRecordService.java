package com.example.spring_jwt.service;


import com.example.spring_jwt.entities.MedicalRecord;
import com.example.spring_jwt.model.request.CreateMedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecord createOrUpdateMedicalRecord(Integer medicalRecordId, CreateMedicalRecord requestBody, String linkImage);

    String getMedicalRecord(MedicalRecord medicalRecord);

    MedicalRecord getById(Integer medicalRecordId);

    List<MedicalRecord> getAllByUserId(Integer userId);
}
