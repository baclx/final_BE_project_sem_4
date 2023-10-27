//package com.example.spring_jwt.schedule;
//
//import com.example.spring_jwt.configuration.SendMailProperties;
//import com.example.spring_jwt.entities.Appointment;
//import com.example.spring_jwt.service.AppointmentService;
//import com.example.spring_jwt.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Component
//@EnableScheduling
//public class SendmailRemindAppointmentSchedule {
//    @Autowired
//    AppointmentService appointmentService;
//
//    @Autowired
//    SendMailProperties sendMailProperties;
//
//    @Autowired
//    EmailService emailService;
//
//    @Scheduled(cron = "*/5 * * * * *")
//    public void sendMailRemindAppointment() {
//        try {
//            System.out.println("Chay job");
//            List<Appointment> appointments = appointmentService.getAppointmentsByDate(LocalDate.now());
//            if (appointments.size() != 0) {
//                String subject = sendMailProperties.getGetAppointmentSubject();
//
//                for (Appointment appointment : appointments) {
//                    String to = appointment.getPatient().getUser().getEmail();
//                    String text = sendMailProperties.getGetAppointmentText() + appointment.getDoctor().getUser().getFullName() + " at: " + appointment.getAppointmentTime();
//                    emailService.sendEmail(to, subject, text, null);
//                }
//                System.out.println("Send email success!!!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
