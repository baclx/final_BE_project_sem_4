package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Appointment;
import com.example.spring_jwt.entities.Doctor;
import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.model.request.CreateAppointment;
import com.example.spring_jwt.model.response.AppointmentDetail;
import com.example.spring_jwt.service.AppointmentService;
import com.example.spring_jwt.service.DoctorService;
import com.example.spring_jwt.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;

    @PostMapping("/create")
    public String createAppointment(@RequestBody CreateAppointment requestBody) {
        Appointment appointment = new Appointment();
        try {
            Doctor doctor = doctorService.getDocTorById(requestBody.getDoctorId());
            Patient patient = patientService.getPatientByUserId(requestBody.getPatientId());
            appointment.setAppointmentTime(requestBody.getAppointmentTime());
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setPhoneNumber(requestBody.getPhoneNumber());
            //appointment.setPatientName(requestBody.getPatientName());
            appointment.setPurpose(requestBody.getPurpose());
            if (appointmentService.createOrUpdateAppointment(appointment) == null) {
                return "Get appointment failed, appointment is full slot today";
            }
            return "Get appointment success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Get appointment failed";
    }


    @PostMapping("/update/{id}")
    public String updateAppointment(@PathVariable(name = "id") Integer id, @RequestBody CreateAppointment requestBody) {
        Appointment appointment = appointmentService.getById(id);
        try {
            Doctor doctor = doctorService.getDocTorById(requestBody.getDoctorId());
            Patient patient = patientService.getPatientByUserId(requestBody.getPatientId());
            appointment.setAppointmentTime(requestBody.getAppointmentTime());
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setPhoneNumber(requestBody.getPhoneNumber());
            //appointment.setPatientName(requestBody.getPatientName());
            appointment.setPurpose(requestBody.getPurpose());
            if (appointmentService.saveAppointment(appointment) == null) {
                return "Get appointment failed, appointment is full slot today";
            }
            return "Update appointment success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Get appointment failed";
    }

    @GetMapping("/getByUserId/{userId}")
    public List<AppointmentDetail> getAppointmentsByUserId(@PathVariable("userId") Integer userId) {
        List<Appointment> appointments = new ArrayList<>();
        List<AppointmentDetail> appointmentDetails = new ArrayList<>();
        try {
            appointments = appointmentService.getAppointmentsByUserId(userId);
            for (Appointment appointment : appointments) {

                Integer userIdByPatient = appointment.getPatient().getUser().getId();
                if (userIdByPatient.equals(userId)) {

                    if (appointment.getAppointmentTime().isAfter(LocalDateTime.now())) {
                        AppointmentDetail appointmentDetail = new AppointmentDetail();
                        appointmentDetail.setAppointment(appointment);
                        appointmentDetail.setPatientName(appointment.getPatient().getUser().getFullName());
                        appointmentDetail.setDoctorName(appointment.getDoctor().getUser().getFullName());
                        appointmentDetails.add(appointmentDetail);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentDetails;
    }

    @GetMapping("/getByUserId/doctor/{userId}")
    public List<AppointmentDetail> getAppointmentsFromDoctorByUserId(@PathVariable("userId") Integer userId) {
        List<Appointment> appointments = new ArrayList<>();
        List<AppointmentDetail> appointmentDetails = new ArrayList<>();
        try {
            appointments = appointmentService.getAppointmentsByUserId(userId);
            for (Appointment appointment : appointments) {
                Integer userIdByDoctor = appointment.getDoctor().getUser().getId();
                if (userIdByDoctor.equals(userId)) {
                    if (appointment.getAppointmentTime().isAfter(LocalDateTime.now())) {
                        AppointmentDetail appointmentDetail = new AppointmentDetail();
                        appointmentDetail.setAppointment(appointment);
                        appointmentDetail.setPatientName(appointment.getPatient().getUser().getFullName());
                        appointmentDetail.setDoctorName(appointment.getDoctor().getUser().getFullName());
                        appointmentDetails.add(appointmentDetail);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentDetails;
    }

    @GetMapping("/getByAppointmentId/{id}")
    public ResponseEntity<AppointmentDetail> getById(@PathVariable("id") Integer id) {
        Appointment appointment = new Appointment();
        AppointmentDetail appointmentDetail = new AppointmentDetail();
        try {
            appointment = appointmentService.getById(id);
            appointmentDetail.setPatientName(appointment.getPatient().getUser().getFullName());
            appointmentDetail.setDoctorName(appointment.getDoctor().getUser().getFullName());
            appointmentDetail.setAppointment(appointment);
            appointmentDetail.setEmail(appointment.getPatient().getUser().getEmail());
            return ResponseEntity.ok(appointmentDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<AppointmentDetail>) ResponseEntity.notFound();
    }

}
