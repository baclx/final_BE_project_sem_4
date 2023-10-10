package com.example.spring_jwt.service.impl;

import com.example.spring_jwt.entities.Appointment;
import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.model.GetFreeDoctorByCategory;
import com.example.spring_jwt.repository.AppointmentRepository;
import com.example.spring_jwt.repository.DoctorRepository;
import com.example.spring_jwt.service.DoctorService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public Doctor getDocTorById(Integer doctorId) {
        try {
            Doctor doctor = doctorRepository.findById(doctorId).get();
            if (doctor != null) {
                return doctor;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Doctor getDoctorByUserId(Integer userId) {
        try {
            Doctor doctor = doctorRepository.getDoctorByUserId(userId);
            return doctor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> getDoctorsBySpecId(Integer specId) {
        try {
            return doctorRepository.getDoctorBySpecializationId(specId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> getFreeDoctorByCategory(GetFreeDoctorByCategory requestBody) {
        List<Doctor> freeDoctorsByCategory = new ArrayList<>();
        try {
            List<Doctor> doctorsBySpecialization = doctorRepository.getDoctorBySpecializationId(requestBody.getCategoryId());
            List<Doctor> doctorsByAppointmentTime = new ArrayList<>();
            List<Appointment> appointments;
            if (!Collections.isEmpty(appointmentRepository.getAppointmentByAppointmentTime(requestBody.getAppointmentTime()))) {
                appointments = appointmentRepository.getAppointmentByAppointmentTime(requestBody.getAppointmentTime());
                for (Appointment appointment : appointments) {
                    doctorsByAppointmentTime.add(appointment.getDoctor());
                }
            }

            for (Doctor doctor : doctorsByAppointmentTime) {
                if (doctorsBySpecialization.contains(doctor)) {
                    doctorsBySpecialization.remove(doctor);
                }
            }
            freeDoctorsByCategory.addAll(doctorsBySpecialization);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return freeDoctorsByCategory;
    }
}
