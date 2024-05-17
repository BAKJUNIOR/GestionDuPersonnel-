package com.example.applicationgestinemployes.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.InputStream;
import java.util.Properties;

@RequestScoped
public class EmailService {

    @Resource(name = "mail/MyMailSession")
    private Session mailSession;

    private Properties mailProperties;

    @PostConstruct
    public void init() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("javamail.properties")) {
            mailProperties = new Properties();
            mailProperties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle the exception
        }
    }

    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(
                    mailProperties.getProperty("mail.smtp.host"),
                    mailProperties.getProperty("mail.smtp.user"),
                    mailProperties.getProperty("mail.smtp.password")
            );
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}
