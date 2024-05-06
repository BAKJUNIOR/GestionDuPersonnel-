package com.example.applicationgestinemployes.controller;

import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.servive.EmployeService;
import jakarta.annotation.PostConstruct;


import jakarta.faces.bean.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;


import java.util.List;

@Named
@RequestScoped
public class EmployeController {

    @Inject
    private EmployeService employeService;

    private List<Employe> employes;
    private Employe newEmploye;

    @PostConstruct
    public void init() {
        employes = employeService.getAllEmployes();
        newEmploye = new Employe();
    }

    public void saveEmploye() {
        employeService.saveEmploye(newEmploye);
        employes = employeService.getAllEmployes(); // Rafraîchir la liste des employés
        newEmploye = new Employe(); // Réinitialiser le nouvel employé
    }

    public void deleteEmploye(Employe employe) {
        employeService.deleteEmploye(employe);
        employes.remove(employe); // Supprimer l'employé de la liste affichée
    }

    // Getters and setters

    public List<Employe> getEmployes() {
        return employes;
    }

    public Employe getNewEmploye() {
        return newEmploye;
    }

    public void setNewEmploye(Employe newEmploye) {
        this.newEmploye = newEmploye;
    }
}
