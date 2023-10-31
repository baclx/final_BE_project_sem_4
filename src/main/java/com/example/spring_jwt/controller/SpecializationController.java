package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Appointment;
import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.entities.Specialization;
import com.example.spring_jwt.model.request.CreateAppointment;
import com.example.spring_jwt.model.request.CreateSpec;
import com.example.spring_jwt.model.request.UpdateSpec;
import com.example.spring_jwt.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
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

    @PostMapping("/create")
    public String createSpec(@RequestBody CreateSpec requestBody) {
        Specialization specialization = new Specialization();
        try {
            if(specializationService.isExitSpec(requestBody.getSpecName())){
                specialization.setSpecName(requestBody.getSpecName());
                specializationService.saveSpecialization(specialization);
                return "Create success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Create failed";
    }

    @PutMapping("/updateSpec/{id}")
    public ResponseEntity<Specialization> updateSpecialization(@PathVariable("id") Integer id, @RequestBody UpdateSpec specialization){
        Specialization oldSpec = specializationService.getSpecializationById(id);
        if (oldSpec == null){
            return ResponseEntity.notFound().build();
        }else{
            oldSpec.setSpecName(specialization.getSpecName());
            return ResponseEntity.ok(oldSpec);
        }
    }

    @DeleteMapping("/deleteSpec/{id}")
    public ResponseEntity<Specialization> deleteSpecialization(@PathVariable("id") Integer id){
        Specialization deleteSpec = specializationService.getSpecializationById(id);
        if (deleteSpec == null){
            return ResponseEntity.notFound().build();
        }else{
            specializationService.deleteSpecialization(id);
            return ResponseEntity.ok().build();
        }
    }
}
