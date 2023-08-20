package com.example.spring_jwt.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app.mail.send"
)
@Data
@Component
public class SendMailProperties {
    private String testResultSubject;
    private String testResultText;
    private String getAppointmentSubject;
    private String getAppointmentText;

}
