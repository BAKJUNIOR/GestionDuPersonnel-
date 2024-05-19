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
        if (conge != null) {
            conge.setStatut("Approuvé");
            update(conge);

            emailService.sendEmail(conge.getEmploye().getCourriel(), "Demande de congé approuvée", "Votre demande de congé a été approuvée.");
            System.out.println("Conge approuvé : " + congeId);
        } else {
            System.out.println("Conge non trouvé : " + congeId);
        }
    }

    @Transactional
    public void rejeterConge(Long congeId) {
        Conge conge = findById(congeId);
        if (conge != null) {
            conge.setStatut("Rejeté");
            update(conge);
            emailService.sendEmail(conge.getEmploye().getCourriel(), "Demande de congé rejetée", "Votre demande de congé a été rejetée.");
            System.out.println("Conge rejeté : " + congeId);
        } else {
            System.out.println("Conge non trouvé : " + congeId);
        }
    }

    public Conge findById(Long id) {
        return em.find(Conge.class, id);
    }

    public List<Conge> findAllPending() {
        return em.createQuery("SELECT c FROM Conge c WHERE c.statut = 'EN_ATTENTE'", Conge.class).getResultList();
    }

    @Transactional
    public void update(Conge conge) {
        em.merge(conge);
    }

    public void sendLeaveRequestNotificationToManager(String managerEmail, String employeeName, String leaveStartDate, String leaveEndDate) {
        String subject = "Nouvelle demande de congé de " + employeeName;
        String content = "L'employé " + employeeName + " a demandé un congé du " + leaveStartDate + " au " + leaveEndDate + ".";
        emailService.sendEmail(managerEmail, subject, content);
    }

}
