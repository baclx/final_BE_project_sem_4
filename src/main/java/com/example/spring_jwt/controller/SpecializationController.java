package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Specialization;
import com.example.spring_jwt.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/specialization")
public class SpecializationController {
    @Autowired
    SpecializationService specializationService;

    @GetMapping("/getAll")
    public List<Specialization> getAll(){
        try {
            if(!CollectionUtils.isEmpty(specializationService.getAllSpecialization())){
                return specializationService.getAllSpecialization();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
