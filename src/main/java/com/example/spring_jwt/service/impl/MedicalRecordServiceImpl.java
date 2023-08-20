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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public MedicalRecord createOrUpdateMedicalRecord(Integer medicalRecordId, CreateMedicalRecord requestBody) {
        MedicalRecord medicalRecord = new MedicalRecord();
        Patient patient = patientService.getPatientById(requestBody.getPatientId());
        try {
            if(medicalRecordId != null){
                medicalRecord = medicalRecordRepository.findById(medicalRecordId).get();

                medicalRecord.setDiseaseProgression(medicalRecord.getDiseaseProgression()+","+requestBody.getDiseaseProgression());
            }else {
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
            //System.out.println(new Gson().toJson(medicalRecord));
            medicalRecordRepository.save(medicalRecord);
        } catch (Exception ex) {
            return null;
        }
        return medicalRecord;
    }
}
