package com.example.applicationgestinemployes.service;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@RequestScoped
public class EmailService {

    @Resource(name = "mail/MyMailSession")
    private Session mailSession;

    public void sendEmail(String to, String subject, String content) {
        try {
            // Créer un nouveau message
            MimeMessage message = new MimeMessage(mailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            // Envoyer le message
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Log de l'exception ou gestion spécifique des erreurs
        }
    }
}
