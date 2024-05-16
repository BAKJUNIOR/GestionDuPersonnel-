package com.example.applicationgestinemployes.service;

import com.example.applicationgestinemployes.model.Conge;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    public Conge findById(Long id) {
        return em.find(Conge.class, id);
    }

    public List<Conge> findAll() {
        return em.createQuery("SELECT c FROM Conge c", Conge.class).getResultList();
    }
}
