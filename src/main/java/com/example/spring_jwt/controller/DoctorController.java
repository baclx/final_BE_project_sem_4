package com.example.spring_jwt.controller;


import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.User;
import com.example.spring_jwt.model.GetFreeDoctorByCategory;
import com.example.spring_jwt.model.request.UpdateDoctor;
import com.example.spring_jwt.model.response.DoctorDetail;
import com.example.spring_jwt.model.response.GetFreeDoctorsResponse;
import com.example.spring_jwt.service.DoctorService;
import com.example.spring_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/doctor")
@CrossOrigin("*")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;

    @PostMapping("")
    public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor){
        doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(doctor);
    }

//    @PutMapping("/updateDoctor/{id}")
//    public ResponseEntity<Doctor> updateDoctor(@PathVariable("id") Integer id, @RequestBody UpdateDoctor doctor){
//        Doctor oldDoctor = doctorService.getDocTorById(id);
//        if (oldDoctor == null){
//            return ResponseEntity.notFound().build();
//        }else{
//            oldDoctor.setFullName(doctor.getFullName());
//            oldDoctor.setSpecialization(doctor.getSpecialization());
//            return ResponseEntity.ok(oldDoctor);
//        }
//    }

    @DeleteMapping("/deleteDoctor/{id}")
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable("id") Integer id){
        Doctor deleteDoctor = doctorService.getDocTorById(id);
        if (deleteDoctor == null){
            return ResponseEntity.notFound().build();
        }else{
            doctorService.deleteDoctor(id);
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/getDoctorByUserId/userId/{userId}")
    public DoctorDetail getDoctorByUserId(@PathVariable("userId") Integer userId) {
        DoctorDetail doctorDetail = new DoctorDetail();
        try {
            User user = userService.getUserById(userId);
            doctorDetail.setAge(user.getPatient().getAge());
            doctorDetail.setGender(user.getGender());
            doctorDetail.setFullName(user.getFullName());
            doctorDetail.setImage(user.getImage());
            doctorDetail.setSpecName(user.getDoctor().getSpecialization().getSpecName());
            doctorDetail.setPhoneNumber(user.getPatient().getPhoneNumber());
            doctorDetail.setId(user.getDoctor().getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctorDetail;
    }

    @PostMapping("/getDoctorsByCategory")
    public GetFreeDoctorsResponse getDoctorsByCategory(@RequestBody GetFreeDoctorByCategory requestBody) {
        GetFreeDoctorsResponse response = new GetFreeDoctorsResponse();
        List<DoctorDetail> doctors = new ArrayList<>();
        try {
            doctors = doctorService.getFreeDoctorByCategory(requestBody);
            response.setFreeDoctors(doctors);
            response.setAppointmentTime(requestBody.getAppointmentTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("/getBySpecId/{specId}")
    public List<Doctor> getDoctorBySpecId(@PathVariable("specId") Integer specId) {
        try {
            return doctorService.getDoctorsBySpecId(specId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
