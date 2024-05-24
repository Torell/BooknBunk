package com.example.booknbunk.services.interfaces;

import java.util.Map;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmailWithTemplate (String to, String subject, String templateName, Map<String, Object> variables);
}
