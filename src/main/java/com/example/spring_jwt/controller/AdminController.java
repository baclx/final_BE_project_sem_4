package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    DoctorService doctorService;


    @Secured("ROLE_ADMIN")
    @GetMapping("/hello")
    public String hello(){
        return "hello admin";
    }


}
