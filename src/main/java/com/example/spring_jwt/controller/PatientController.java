package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.entities.User;
import com.example.spring_jwt.model.request.UpdatePatientReq;
import com.example.spring_jwt.model.response.PatientResponse;
import com.example.spring_jwt.service.DoctorService;
import com.example.spring_jwt.service.PatientService;
import com.example.spring_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@CrossOrigin("*")
@RequestMapping("api/patients")

public class PatientController {
    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;

    @GetMapping("/id")
    public Patient getPatientById(@RequestParam(name = "id") Integer patientId) {
        return patientService.getPatientById(patientId);
    }

//    @PostMapping("/updatePatient/{userId}")
//    public Patient updatePatientByUserId(@PathVariable(name = "userId") Integer userId, @RequestBody UpdatePatientReq patientUpdate){
//        try {
//            Patient patient = patientService.getPatientByUserId(userId);
//            patient.setEmail(patientUpdate.getEmail());
//            patient.setAddress(patientUpdate.getAddress());
//            patient.setFullName(patientUpdate.getFullName());
//            patient.setDateOfBirth(patientUpdate.getDateOfBirth());
//            patient.setGender(patientUpdate.getGender());
//            patient.setHeight(patientUpdate.getHeight());
//            patient.setAge(patientUpdate.getAge());
//            patient.setImage(patientUpdate.getImage());
//            patient.setPhoneNumber(patientUpdate.getPhoneNumber());
//            patient.setWeight(patientUpdate.getWeight());
//            patientService.savePatient(patient);
//            return patient;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
    @GetMapping("/email")
    public PatientResponse getPatientByEmail(@RequestParam("email") String email){
        PatientResponse patientResponse = new PatientResponse();
        try {
            User user = userService.findByEmail(email);
            patientResponse.setAddress(user.getPatient().getAddress());
            patientResponse.setDateOfBirth(user.getPatient().getDateOfBirth());
            patientResponse.setFullName(user.getFullName());
            patientResponse.setGender(user.getGender());
            patientResponse.setPhoneNumber(user.getPatient().getPhoneNumber());
            patientResponse.setId(user.getPatient().getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return patientResponse;
    }

    @GetMapping("/getPatient/{userId}")
    public Patient getPatientByUserId(@PathVariable(name = "userId") Integer userId){
        Patient patient = new Patient();
        try {
            patient = patientService.getPatientByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return patient;
    }


}
