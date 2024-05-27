package com.example.booknbunk.services.implementations;

import com.example.booknbunk.services.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.Map;

import org.thymeleaf.context.Context;

@Service
public class EmailServiceImplementation implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImplementation(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }



    @Override
    public void sendEmailWithTemplate(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

        Context context = new Context();
        context.setVariables(variables);
        String body = templateEngine.process(templateName, context);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body,true);

        mailSender.send(message);
    }

}

