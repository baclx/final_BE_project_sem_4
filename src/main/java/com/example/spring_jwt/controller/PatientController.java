package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.service.DoctorService;
import com.example.spring_jwt.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("api/patients")

public class PatientController {
    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @GetMapping("/email")
    public Patient getPatientByEmail(@RequestParam(name = "email") String email) {
        return patientService.getPatientByEmail(email);
    }

    @GetMapping("/id")
    public Patient getPatientById(@RequestParam(name = "id") Integer patientId) {
        return patientService.getPatientById(patientId);
    }

    @GetMapping("/getBySpecId")
    public List<Doctor> getDoctorBySpecId(@RequestParam("specId") Integer specId) {
        try {
            return doctorService.getDoctorsBySpecId(specId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
