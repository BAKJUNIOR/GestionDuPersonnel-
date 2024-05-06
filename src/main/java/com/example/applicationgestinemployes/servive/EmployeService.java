package com.example.applicationgestinemployes.servive;

import com.example.applicationgestinemployes.model.Employe;

import java.util.List;

public interface EmployeService {
    List<Employe> getAllEmployes();
    void saveEmploye(Employe employe);
    void deleteEmploye(Employe employe);
}
