package com.example.applicationgestinemployes.repository;

import com.example.applicationgestinemployes.model.Employe;

import jakarta.persistence.EntityManager;

import java.util.List;

public interface EmployeRepository {
    List<Employe> findAll();
    void save(Employe employe);
    void delete(Employe employe);
}
