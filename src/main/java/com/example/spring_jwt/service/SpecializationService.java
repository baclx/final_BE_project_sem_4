package com.example.spring_jwt.service;

import com.example.spring_jwt.entities.Specialization;
import com.example.spring_jwt.model.request.CreateSpec;

import java.util.List;

public interface
SpecializationService {
    List<Specialization> getAllSpecialization();

    void saveSpecialization(Specialization specialization);

    Specialization getSpecializationById(Integer id);

    void deleteSpecialization(Integer id);

    boolean isExitSpec(String specName);

}
