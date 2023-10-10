package com.example.spring_jwt.controller;


import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.model.GetFreeDoctorByCategory;
import com.example.spring_jwt.model.response.GetFreeDoctorsResponse;
import com.example.spring_jwt.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/doctor")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @GetMapping("/getDoctorByUserId/userId/{userId}")
    public Doctor getDoctorByUserId(@PathVariable("userId") Integer userId) {
        try {
            return doctorService.getDoctorByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/getDoctorsByCategory")
    public GetFreeDoctorsResponse getDoctorsByCategory(@RequestBody GetFreeDoctorByCategory requestBody) {
        GetFreeDoctorsResponse response = new GetFreeDoctorsResponse();
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = doctorService.getFreeDoctorByCategory(requestBody);
            response.setFreeDoctors(doctors);
            response.setAppointmentTime(requestBody.getAppointmentTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


}
