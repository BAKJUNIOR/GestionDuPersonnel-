package com.example.applicationgestinemployes.service;

import com.example.applicationgestinemployes.model.Employe;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class EmployeServiceImpl implements EmployeService {

    @PersistenceContext(unitName = "GestEmploye_dbConfig")
    private EntityManager em;

    @Override
    public List<Employe> getAllEmployes() {
        return em.createQuery("SELECT e FROM Employe e", Employe.class).getResultList();
    }

    @Override
    public Employe getEmploye(Long id) {
        return em.find(Employe.class, id);
    }

    @Transactional
    @Override
    public void addEmploye(Employe employe) {
        em.persist(employe);
    }

    @Transactional
    @Override
    public void updateEmploye(Employe employe) {
        em.merge(employe);
    }


    @Transactional
    @Override
    public boolean deleteEmploye(Long id) {
        Employe employe = em.find(Employe.class, id);
        if (employe != null) {
            em.remove(employe);
            return true;
        }
        return false;
    }
}