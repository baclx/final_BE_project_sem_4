package com.example.spring_jwt.service;


import com.example.spring_jwt.entities.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor getDocTorById(Integer doctorId);

    Doctor getDoctorByUserId(Integer userId);

    List<Doctor> getDoctorsBySpecId(Integer specId);
}
