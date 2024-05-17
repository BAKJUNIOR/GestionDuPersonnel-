package com.example.applicationgestinemployes.service;

import com.example.applicationgestinemployes.model.Conge;
import com.example.applicationgestinemployes.model.Employe;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

@RequestScoped
public class CongeService {

    @PersistenceContext(unitName = "GestEmploye_dbConfig")
    private EntityManager em;

    @Inject
    private EmailService emailService;

    @Transactional
    public void create(Conge conge) {
        em.persist(conge);
    }

    @Transactional
    public void approuverConge(Long congeId) {
        Conge conge = findById(congeId);
        conge.setStatut("Approuvé");
        update(conge);

        // Envoyer une notification par e-mail à l'employé
        emailService.sendEmail(conge.getEmploye().getCourriel(), "Demande de congé approuvée",
                "Votre demande de congé a été approuvée.");
    }

    @Transactional
    public void rejeterConge(Long congeId) {
        Conge conge = findById(congeId);
        conge.setStatut("Rejeté");
        update(conge);

        // Envoyer une notification par e-mail à l'employé
        emailService.sendEmail(conge.getEmploye().getCourriel(), "Demande de congé rejetée",
                "Votre demande de congé a été rejetée.");
    }

    public Conge findById(Long id) {
        return em.find(Conge.class, id);
    }

    public List<Conge> findAllPending() {
        return em.createQuery("SELECT c FROM Conge c WHERE c.statut = 'En attente'", Conge.class).getResultList();
    }

    @Transactional
    public void update(Conge conge) {
        em.merge(conge);
    }

    public void sendLeaveRequestNotificationToManager(String managerEmail, String employeeName, String leaveStartDate, String leaveEndDate) {
        String subject = "Nouvelle demande de congé";
        String content = String.format("L'employé %s a demandé un congé du %s au %s.", employeeName, leaveStartDate, leaveEndDate);
        emailService.sendEmail(managerEmail, subject, content);
    }

}
