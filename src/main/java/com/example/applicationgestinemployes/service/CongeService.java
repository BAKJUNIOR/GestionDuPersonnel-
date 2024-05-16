package com.example.applicationgestinemployes.service;

import com.example.applicationgestinemployes.model.Conge;
import com.example.applicationgestinemployes.model.Employe;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;

@RequestScoped
public class CongeService {
    @PersistenceContext(unitName = "GestEmploye_dbConfig")
    private EntityManager em;

    public void create(Conge conge) {
        em.persist(conge);
    }

    public void update(Conge conge) {
        em.merge(conge);
    }

    public void delete(Conge conge) {
        em.remove(em.merge(conge));
    }

    public void demanderConge(Employe employe, Date dateDebut, Date dateFin, String motif) {
        Conge conge = new Conge();
        conge.setEmploye(employe);
        conge.setDateDebut(dateDebut);
        conge.setDateFin(dateFin);
        conge.setMotif(motif);
        conge.setStatut("En attente");

        create(conge);

        // Vous pouvez appeler ici la méthode pour envoyer une notification par e-mail au responsable de l'employé
    }

    public void approuverConge(Conge conge) {
        conge.setStatut("Approuvé");

        update(conge);

        // Vous pouvez appeler ici la méthode pour envoyer une notification par e-mail à l'employé concerné
    }

    public void rejeterConge(Conge conge) {
        conge.setStatut("Rejeté");

        update(conge);

        // Vous pouvez appeler ici la méthode pour envoyer une notification par e-mail à l'employé concerné
    }

    public Conge findById(Long id) {
        return em.find(Conge.class, id);
    }

    public List<Conge> findAll() {
        return em.createQuery("SELECT c FROM Conge c", Conge.class).getResultList();
    }
}
