package com.mista.soft.hospital_project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String body, String topic){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("hospital.grodno@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);

    }

    public void sendMessageWithAttachment(
            String to, String subject, String text, String pathToAttachment) throws MessagingException {


        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("hospital.grodno@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file
                = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("invoice.pdf", file);

        javaMailSender.send(message);

    }

    public void send(String to, String subject, String message){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("hospital.grodno@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);

    }
}