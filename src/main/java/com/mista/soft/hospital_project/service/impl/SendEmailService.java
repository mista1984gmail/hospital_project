package com.mista.soft.hospital_project.service.impl;

import com.mista.soft.hospital_project.model.entity.AnalysisResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
@Slf4j
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String firstName, String typeName, List<AnalysisResult> results, String topic){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("hospital.grodno@gmail.com");
        String body = String.format("Good afternoon, %s. Your test result of %s is %s",
                firstName,
                typeName,
                results);

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
        log.info("Email with test results has been sent to  " + to);

    }

    public void sendMessageWithAttachment(
            String to, String subject, String firstName, String lastName) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String body = String.format("Good afternoon, %s. Your invoice", firstName);
        String path ="D:\\Downloads\\"+ lastName +"_invoice.pdf";

        helper.setFrom("hospital.grodno@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        FileSystemResource file
                = new FileSystemResource(new File(path));
        helper.addAttachment("invoice.pdf", file);

        javaMailSender.send(message);
        log.info("Email with invoice has been sent to  " + to);
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
