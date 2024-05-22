package com.example.applicationgestinemployes.service;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.util.ByteArrayDataSource;

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

    public void sendEmailWithAttachment(String to, String subject, String content, byte[] attachmentData, String fileName) {
        try {
            // Créer un nouveau message
            MimeMessage message = new MimeMessage(mailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Créer la partie texte du message
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(content);

            // Créer un conteneur multipart pour le message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Créer la partie de la pièce jointe
            messageBodyPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(attachmentData, "application/pdf");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            // Attacher le multipart au message
            message.setContent(multipart);

            // Envoyer le message
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Log de l'exception ou gestion spécifique des erreurs
        }
    }
}
