package com.example.applicationgestinemployes.controller;


import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.service.EmployeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EmployeController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EmployeService employeService;

    private transient List<Employe> employes;
    private Employe selectedEmploye = new Employe();

    @PostConstruct
    public void init() {
        employes = employeService.getAllEmployes();
    }

    public void addEmploye() {
        employeService.addEmploye(selectedEmploye);
        selectedEmploye = new Employe(); // Reset
        init(); // Recharger la liste des employés
    }

    public void updateEmploye() {
        employeService.updateEmploye(selectedEmploye);
        selectedEmploye = new Employe(); // Reset
    }

    public void deleteEmploye() {
        boolean deleted = employeService.deleteEmploye(selectedEmploye.getIdEmploye());
        if (deleted) {
            employes.remove(selectedEmploye);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Employé supprimé avec succès"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur", "L'employé n'existe pas ou ne peut être supprimé"));
        }
        selectedEmploye = new Employe(); // Reset
        init(); // Recharger la liste des employés
    }
    // Getters and Setters
    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public Employe getSelectedEmploye() {
        return selectedEmploye;
    }

    public void setSelectedEmploye(Employe selectedEmploye) {
        this.selectedEmploye = selectedEmploye;
    }
}
