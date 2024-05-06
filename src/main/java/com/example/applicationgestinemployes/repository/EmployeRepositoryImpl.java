package com.example.applicationgestinemployes.repository;

import com.example.applicationgestinemployes.model.Employe;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Named
public class EmployeRepositoryImpl implements EmployeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employe> findAll() {
        return entityManager.createQuery("SELECT e FROM Employe e", Employe.class).getResultList();
    }

    @Override
    public void save(Employe employe) {
        entityManager.persist(employe);
    }

    @Override
    public void delete(Employe employe) {
        entityManager.remove(employe);
    }
}
