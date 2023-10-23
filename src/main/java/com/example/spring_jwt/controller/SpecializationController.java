package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Specialization;
import com.example.spring_jwt.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
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

    @PostMapping("/add")
    public void addSpecialization(@RequestBody Specialization specialization){
        try {
            specializationService.
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
