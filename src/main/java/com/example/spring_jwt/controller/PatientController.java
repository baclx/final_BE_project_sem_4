package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.entities.User;
import com.example.spring_jwt.model.request.UpdatePatientReq;
import com.example.spring_jwt.model.response.GetPatientByUserIdRes;
import com.example.spring_jwt.model.response.PatientResponse;
import com.example.spring_jwt.service.DoctorService;
import com.example.spring_jwt.service.PatientService;
import com.example.spring_jwt.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
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
            patientResponse.setAddress(user.getPatient().getAddress() != null ? user.getPatient().getAddress() : "");
            patientResponse.setDateOfBirth(user.getPatient().getDateOfBirth() != null ? user.getPatient().getDateOfBirth() : null);
            patientResponse.setFullName(user.getFullName() != null ? user.getFullName() : "");
            patientResponse.setGender(user.getGender() != null ? user.getGender() : "");
            patientResponse.setPhoneNumber(user.getPatient().getPhoneNumber() != null ? user.getPatient().getPhoneNumber() : "");
            patientResponse.setId(user.getPatient().getId() != null ? user.getPatient().getId() : null);
            patientResponse.setHeight(user.getPatient().getHeight() != null ? user.getPatient().getHeight() : "");
            patientResponse.setWeight(user.getPatient().getWeight() != null ? user.getPatient().getWeight() : "");
            patientResponse.setEmail(user.getEmail() != null ? user.getEmail() : "");
            patientResponse.setImage(user.getImage() != null ? user.getImage() : "");

//            private String height;
//            private String weight;
//            private String email;
//            private String image;
        }catch (Exception e){
            e.printStackTrace();
        }
        return patientResponse;
    }

    @GetMapping("/getPatient/{userId}")
    public GetPatientByUserIdRes getPatientByUserId(@PathVariable(name = "userId") Integer userId){
        Patient patient = new Patient();
        GetPatientByUserIdRes response = new GetPatientByUserIdRes();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            patient = patientService.getPatientByUserId(userId);
            if(Objects.nonNull(patient)){
                response = objectMapper.convertValue(patient,GetPatientByUserIdRes.class);
                response.setFullName(patient.getUser().getFullName());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }


}
