package com.example.applicationgestinemployes.service;

import com.example.applicationgestinemployes.model.Responsable;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@RequestScoped
public class ResponsableService {
    @PersistenceContext(unitName = "GestEmploye_dbConfig")
    private EntityManager em;

    public void create(Responsable responsable) {
        em.persist(responsable);
    }

    public void update(Responsable responsable) {
        em.merge(responsable);
    }

    public void delete(Responsable responsable) {
        em.remove(em.merge(responsable));
    }

    public Responsable findById(Long id) {
        return em.find(Responsable.class, id);
    }

    public List<Responsable> findAll() {
        return em.createQuery("SELECT r FROM Responsable r", Responsable.class).getResultList();
    }
}
