package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Appointment;
import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.model.request.CreateAppointment;
import com.example.spring_jwt.service.AppointmentService;
import com.example.spring_jwt.service.DoctorService;
import com.example.spring_jwt.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;

    @PostMapping("/create")
    public String createAppointment ( CreateAppointment requestBody){
        Appointment appointment = new Appointment();
        try{
            Doctor doctor = doctorService.getDocTorById(requestBody.getDoctorId());
            Patient patient = patientService.getPatientById(requestBody.getPatientId());
            appointment.setAppointmentTime(requestBody.getAppointmentTime());
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setPhoneNumber(requestBody.getPhoneNumber());
            appointment.setPatientName(requestBody.getPatientName());
            appointment.setPurpose(requestBody.getPurpose());
            if(appointmentService.createOrUpdateAppointment(appointment) == null){
                return "Get appointment failed, appointment is full slot today";
            }
            return "Get appointment success!";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Get appointment failed";
    }

}