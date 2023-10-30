package com.example.spring_jwt.service.impl;

import com.example.spring_jwt.entities.Appointment;
import com.example.spring_jwt.repository.AppointmentRepository;
import com.example.spring_jwt.service.AppointmentService;
import com.example.spring_jwt.service.DoctorService;
import com.example.spring_jwt.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;

    @Override
    public Appointment createOrUpdateAppointment(Appointment requestBody) {
        Appointment appointment = null;
        try {
            requestBody.setIsCheck(0);
            requestBody.setIsDeleted(0);
            appointment = appointmentRepository.save(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointment;
    }

    @Override
    public Appointment saveAppointment(Appointment appointment){
        try {
            appointmentRepository.save(appointment);
        }catch (Exception e){
            e.printStackTrace();
        }
        return appointment;
    }

    @Override
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> appointments = new ArrayList<>();
        try {
            appointments = appointmentRepository.findAppointmentsForToday();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(Integer userId) {
        List<Appointment> appointments = new ArrayList<>();
        try {
            appointments = appointmentRepository.getAppointmentByUserId(userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Appointment getById(Integer id) {
        try {
            return appointmentRepository.findById(id).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
