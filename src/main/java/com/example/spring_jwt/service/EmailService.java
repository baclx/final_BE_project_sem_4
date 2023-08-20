package com.example.spring_jwt.service;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String text, String filePath) throws MessagingException;
}
