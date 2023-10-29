package com.example.spring_jwt.service.impl;


import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.MedicalRecord;
import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.model.request.CreateMedicalRecord;
import com.example.spring_jwt.repository.DoctorRepository;
import com.example.spring_jwt.repository.MedicalRecordRepository;
import com.example.spring_jwt.repository.PatientRepository;
import com.example.spring_jwt.service.DoctorService;
import com.example.spring_jwt.service.MedicalRecordService;
import com.example.spring_jwt.service.PatientService;
import com.example.spring_jwt.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;

    @Autowired
    PdfService pdfService;

    @Override
    public MedicalRecord createOrUpdateMedicalRecord(Integer medicalRecordId, CreateMedicalRecord requestBody, String linkImages) {
        MedicalRecord medicalRecord = new MedicalRecord();
        Patient patient = patientService.getPatientById(requestBody.getPatientId());
        //Patient patient = patientService.getPatientByEmail(requestBody.getPatientEmail());
        try {
            if (medicalRecordId != null) {
                medicalRecord = medicalRecordRepository.findById(medicalRecordId).get();

                medicalRecord.setDiseaseProgression(medicalRecord.getDiseaseProgression() + "," + requestBody.getDiseaseProgression());
            } else {
                medicalRecord.setDiseaseProgression(requestBody.getDiseaseProgression());
            }
            Doctor doctor = doctorService.getDocTorById(requestBody.getDoctorId());
            medicalRecord.setMedicationDetails(requestBody.getMedicationDetails());
            medicalRecord.setCurrentCondition(requestBody.getCurrentCondition());
            medicalRecord.setNoteFromDoctor(requestBody.getNoteFromDoctor());
            medicalRecord.setPatient(patient);
            medicalRecord.setDoctor(doctor);
            medicalRecord.setDiseaseProgression(requestBody.getDiseaseProgression());
            medicalRecord.setTestResult(requestBody.getTestResult());
            medicalRecord.setIsDeleted(0);
            medicalRecord.setImages(linkImages);
            //System.out.println(new Gson().toJson(medicalRecord));
            medicalRecordRepository.save(medicalRecord);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return medicalRecord;
    }

    @Override
    public String getMedicalRecord(MedicalRecord medicalRecord) {
        String outputFile = null;
        try {
            CreateMedicalRecord createMedicalRecord = new CreateMedicalRecord();
            createMedicalRecord.setDoctorId(medicalRecord.getDoctor().getId());
            createMedicalRecord.setDoctorName(medicalRecord.getDoctor().getUser().getFullName());
            createMedicalRecord.setTestResult(medicalRecord.getTestResult());
            //createMedicalRecord.setPatientEmail(medicalRecord.getPatient().getEmail());
            createMedicalRecord.setPatientId(medicalRecord.getPatient().getId());
            createMedicalRecord.setNoteFromDoctor(medicalRecord.getNoteFromDoctor());
            createMedicalRecord.setMedicationDetails(medicalRecord.getMedicationDetails());
            createMedicalRecord.setDiseaseProgression(medicalRecord.getDiseaseProgression());

            outputFile = pdfService.generatePdf(createMedicalRecord, medicalRecord.getImages());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile;
    }


    @Override
    public MedicalRecord getById(Integer medicalRecordId) {
        MedicalRecord medicalRecord = null;
        try {
            medicalRecord = medicalRecordRepository.findById(medicalRecordId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicalRecord;
    }


    @Override
    public List<MedicalRecord> getAllByUserId(Integer userId) {
        List<MedicalRecord> medicalRecords = null;
        try {
            //Patient patient = patientService.getPatientByUserId(userId);
           // medicalRecords = medicalRecordRepository.getAllByPatient(patient);
            medicalRecords = medicalRecordRepository.getByUserId(userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicalRecords;
    }
}
