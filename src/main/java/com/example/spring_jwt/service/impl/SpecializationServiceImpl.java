package com.example.spring_jwt.service.impl;

import com.example.spring_jwt.entities.Specialization;
import com.example.spring_jwt.model.request.CreateSpec;
import com.example.spring_jwt.repository.SpecializationRepository;
import com.example.spring_jwt.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecializationServiceImpl implements SpecializationService {
    @Autowired
    SpecializationRepository specializationRepository;

    @Override
    public List<Specialization> getAllSpecialization() {
        List<Specialization> specializations = new ArrayList<>();
        try {
            specializations = specializationRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return specializations;
    }

    @Override
    public void saveSpecialization(Specialization specialization) {
        specializationRepository.save(specialization);
    }

    @Override
    public Specialization getSpecializationById(Integer id) {
        return specializationRepository.findById(id).get();
    }

    @Override
    public void deleteSpecialization(Integer id) {
        specializationRepository.deleteById(id);
    }
}
