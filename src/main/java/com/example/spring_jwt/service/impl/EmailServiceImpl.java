package com.example.spring_jwt.service.impl;


import com.example.spring_jwt.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;


import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;


@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Autowired

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String text,String filePath) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        if(filePath != null){
            FileSystemResource file = new FileSystemResource(new File(filePath));
            helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
        }
        javaMailSender.send(message);
    }
}
