package com.example.spring_jwt.service.impl;

import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.repository.DoctorRepository;
import com.example.spring_jwt.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public Doctor getDocTorById(Integer doctorId) {
        try {
            Doctor doctor = doctorRepository.findById(doctorId).get();
            if(doctor != null){
                return doctor;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Doctor getDoctorByUserId(Integer userId) {
        try{
            Doctor doctor = doctorRepository.getDoctorByUserId(userId);
            return doctor;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> getDoctorsBySpecId(Integer specId) {
        try {
            return doctorRepository.getDoctorBySpecializationId(specId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
