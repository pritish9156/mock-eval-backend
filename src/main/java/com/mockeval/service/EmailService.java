package com.mockeval.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String link) {

        String html = "<div style='font-family:sans-serif;padding:20px'>"
                + "<h2 style='color:#333'>Reset Your Password</h2>"
                + "<p>Click the button below to reset your password:</p>"
                + "<a href='" + link + "' style='"
                + "display:inline-block;"
                + "padding:10px 20px;"
                + "background:#00d9f5;"
                + "color:white;"
                + "text-decoration:none;"
                + "border-radius:5px;'>"
                + "Reset Password</a>"
                + "<p style='margin-top:20px;font-size:12px;'>"
                + "This link expires in 15 minutes."
                + "</p>"
                + "</div>";

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}