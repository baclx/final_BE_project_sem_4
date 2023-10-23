package com.example.spring_jwt.service;


import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.model.GetFreeDoctorByCategory;

import java.util.List;

public interface DoctorService {
    Doctor getDocTorById(Integer doctorId);

    Doctor getDoctorByUserId(Integer userId);

    List<Doctor> getDoctorsBySpecId(Integer specId);

    List<Doctor> getFreeDoctorByCategory(GetFreeDoctorByCategory requestBody);

    void saveDoctor(Doctor doctor);

    void deleteDoctor(Integer id);

}
