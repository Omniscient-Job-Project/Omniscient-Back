package com.omniscient.omniscientback.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    private JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public boolean sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("zhuyaan93@gmail.com"); // 실제 발신 이메일 주소
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            return true; // 메일 전송 성공 시 true 반환
        } catch (Exception e) {
            // 메일 전송 실패 시 false 반환
            System.err.println("메일 전송 실패: " + e.getMessage());
            return false;
        }
    }
}
