package com.example.spring_jwt.service.impl;

import com.example.spring_jwt.entities.Appointment;
import com.example.spring_jwt.repository.AppointmentRepository;
import com.example.spring_jwt.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public Appointment createOrUpdateAppointment(Appointment requestBody){
        Appointment appointment = null;
        try {
            Integer countOfAppointmentToday = appointmentRepository.countAppointmentByDoctorIdAndDate(requestBody.getDoctor().getId(), new Date());
            if(countOfAppointmentToday < 8){
                requestBody.setIsCheck(0);
                requestBody.setIsDeleted(0);
                appointment = appointmentRepository.save(requestBody);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return appointment;
    }

    @Override
    public List<Appointment> getAppointmentsByDate(Date date) {
        List<Appointment> appointments = new ArrayList<>();
        try {
             appointments = appointmentRepository.getAppointmentByAppointmentTime(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return appointments;
    }
}
