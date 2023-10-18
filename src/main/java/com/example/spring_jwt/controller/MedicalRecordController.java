package com.example.spring_jwt.controller;


import com.example.spring_jwt.configuration.SendMailProperties;
import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.MedicalRecord;
import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.model.request.CreateMedicalRecord;
import com.example.spring_jwt.service.*;
import com.example.spring_jwt.util.FileStorageService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/medicalRecord")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class MedicalRecordController {
    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    PdfService pdfService;

    @Autowired
    EmailService emailService;


    @Autowired
    DoctorService doctorService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    PatientService patientService;

    @Autowired
    SendMailProperties sendMailProperties;


    @PostMapping("/create")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@ModelAttribute CreateMedicalRecord requestBody, @RequestParam("files") MultipartFile[] files) {
        MedicalRecord medicalRecord = new MedicalRecord();
        try {
            medicalRecord = medicalRecordService.createOrUpdateMedicalRecord(null, requestBody);
            fileStorageService.uploadImage(files);
            List<String> imageList = new ArrayList<>();
            for (MultipartFile file : files) {
                String imagePath = "images/" + file.getOriginalFilename();
                imageList.add(imagePath);
            }
            String imagesString = String.join(",", imageList);
            genFilePDF(requestBody, imagesString);

            return ResponseEntity.ok(medicalRecord);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void genFilePDF(CreateMedicalRecord createMedicalRecord, String imagesString) throws DocumentException, IOException, MessagingException {
        Patient patient = patientService.getPatientById(createMedicalRecord.getPatientId());
        Doctor doctor = doctorService.getDocTorById(createMedicalRecord.getDoctorId());
        createMedicalRecord.setDoctorName(doctor.getFullName());

        String to = patient.getEmail();
        String subject = sendMailProperties.getTestResultSubject();
        String text = sendMailProperties.getTestResultText();

        String outputFile = pdfService.generatePdf(createMedicalRecord, imagesString);
        String filePath = outputFile;

        emailService.sendEmail(to, subject, text, filePath);
    }

}
