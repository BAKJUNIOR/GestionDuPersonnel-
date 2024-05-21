package com.example.applicationgestinemployes.service;

import com.example.applicationgestinemployes.model.Message;
import com.example.applicationgestinemployes.model.Employe;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RequestScoped
public class MessageService {

    @PersistenceContext(unitName = "GestEmploye_dbConfig")
    private EntityManager em;

    @Transactional
    public void sendMessage(Message message) {
        message.setDateEnvoi(new Date());
        em.persist(message);
    }

    public List<Message> getMessagesForEmployee(Long employeeId) {
        return em.createQuery("SELECT m FROM Message m JOIN m.destinataires d WHERE d.idEmploye = :employeeId", Message.class)
                .setParameter("employeeId", employeeId)
                .getResultList();
    }
}
