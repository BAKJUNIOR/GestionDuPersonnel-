package com.example.applicationgestinemployes.service;

import com.example.applicationgestinemployes.model.Message;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class MessageService {

    @PersistenceContext(unitName = "GestEmploye_dbConfig")
    private EntityManager em;

    @Transactional
    public void saveMessage(Message message) {
        em.persist(message);
    }

    public List<Message> findMessagesByResponsable(Long responsableId) {
        return em.createQuery("SELECT m FROM Message m WHERE m.responsable.idResponsable = :responsableId", Message.class)
                .setParameter("responsableId", responsableId)
                .getResultList();
    }

    public List<Message> findMessagesForEmployee(Long employeeId) {
        return em.createQuery("SELECT m FROM Message m JOIN m.destinataires e WHERE e.idEmploye = :employeeId", Message.class)
                .setParameter("employeeId", employeeId)
                .getResultList();
    }

    public List<Message> findAllMessages() {
        return em.createQuery("SELECT m FROM Message m", Message.class).getResultList();
    }

    public void deleteMessage(Message message) {
        em.remove(em.merge(message));
    }

    public Message findById(Long id) {
        return em.find(Message.class, id);
    }
}
