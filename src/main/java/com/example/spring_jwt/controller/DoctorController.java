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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/doctor")
@CrossOrigin(origins = "*")
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
            if(Objects.nonNull(user.getPatient())){
                doctorDetail.setAge(user.getPatient().getAge() != null ? user.getPatient().getAge():null);
                doctorDetail.setPhoneNumber(user.getPatient().getPhoneNumber() != null ? user.getPatient().getPhoneNumber() : "");
            }

            doctorDetail.setGender(user.getGender() != null ? user.getGender(): "");
            doctorDetail.setFullName(user.getFullName() != null ? user.getFullName(): "");
            doctorDetail.setImage(user.getImage() != null ? user.getImage(): "");
            doctorDetail.setSpecName(user.getDoctor().getSpecialization().getSpecName() != null ? user.getDoctor().getSpecialization().getSpecName(): "");

            doctorDetail.setId(user.getDoctor().getId() != null ? user.getDoctor().getId() : null);

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
    public List<DoctorDetail> getDoctorBySpecId(@PathVariable("specId") Integer specId) {
        List<DoctorDetail> doctorDetails = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = doctorService.getDoctorsBySpecId(specId);
            if(!CollectionUtils.isEmpty(doctors)){
                for (Doctor doctor : doctors){
                    DoctorDetail doctorDetail = new DoctorDetail();
                    doctorDetail.setFullName(doctor.getUser().getFullName());
                    doctorDetail.setSpecName(doctor.getSpecialization().getSpecName());
                    if(Objects.nonNull(doctor.getUser().getPatient())){

                        doctorDetail.setPhoneNumber(doctor.getUser().getPatient().getPhoneNumber() != null ? doctor.getUser().getPatient().getPhoneNumber() : "");
                    }
                    if(Objects.nonNull(doctor.getUser().getPatient())){
                        doctorDetail.setAge(doctor.getUser().getPatient().getAge());
                    }
                    doctorDetail.setImage(doctor.getUser().getImage());
                    doctorDetail.setGender(doctor.getUser().getGender());
                    doctorDetail.setId(doctor.getId());
                    doctorDetail.setWorkExperience(doctor.getWorkExperience() != null ? doctor.getWorkExperience() : "");
                    doctorDetail.setGraduateAt(doctor.getGraduateAt() != null ? doctor.getGraduateAt() : "");
                    doctorDetails.add(doctorDetail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctorDetails;
    }

}
