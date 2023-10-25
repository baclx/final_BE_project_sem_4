package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.Role;
import com.example.spring_jwt.entities.Specialization;
import com.example.spring_jwt.entities.User;
import com.example.spring_jwt.model.request.UpdateUserToDoctorReq;
import com.example.spring_jwt.service.DoctorService;
import com.example.spring_jwt.service.RoleService;
import com.example.spring_jwt.service.SpecializationService;
import com.example.spring_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    SpecializationService specializationService;


    @GetMapping("/hello")
    public String hello() {
        return "hello admin";
    }

    @PostMapping("/updateUserToDoctor")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<String> updateUserToDoctor(@RequestBody UpdateUserToDoctorReq updateUser){
        try {
            User user = userService.getUserById(updateUser.getUserId());
            Set<Role> roles = user.getRoles();
            if(!roles.contains(roleService.findByName("ROLE_DOCTOR"))){
                roles.add(roleService.findByName("ROLE_DOCTOR"));
                Specialization specialization = specializationService.getSpecializationById(updateUser.getSpecId());
                Doctor doctor = new Doctor();
                doctor.setUser(user);
                doctor.setSpecialization(specialization);
                doctorService.saveDoctor(doctor);
                userService.save(user);
            }
            return ResponseEntity.ok("Update User to doctor successfully!!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("Update failed!!");
    }
}
