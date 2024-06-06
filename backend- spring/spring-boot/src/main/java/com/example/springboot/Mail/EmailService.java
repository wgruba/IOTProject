package com.example.springboot.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("iwenthub01@op.pl");

            mailSender.send(message);
        }
        catch (Exception e) {
            System.err.println("Failed to send email to " + to);
            e.printStackTrace();
        }
    }

    public void sendVerificationEmail(String to, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("iwenthub01@op.pl");
            message.setTo(to);
            message.setSubject("Your verification code");
            message.setText("Your verification code is: " + code);
            mailSender.send(message);
            System.out.println("Email sent successfully to " + to);

        } catch (Exception e) {
            System.err.println("Failed to send email to " + to);
            e.printStackTrace();
        }
    }
}

