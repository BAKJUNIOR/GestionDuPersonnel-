package com.example.applicationgestinemployes.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RequestScoped
public class EmailService {

    private Session mailSession;

    public EmailService() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("META-INF/javamail.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find javamail.properties");
            }
            properties.load(input);
            mailSession = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),
                            properties.getProperty("mail.smtp.password"));
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load mail properties", ex);
        }
    }

    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("your_email@example.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
