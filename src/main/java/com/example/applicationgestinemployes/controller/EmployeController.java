package com.example.applicationgestinemployes.controller;

import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.service.EmployeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.transaction.Transactional;

import java.io.IOException;
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
        String employeId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (employeId != null) {
            selectedEmploye = employeService.getEmploye(Long.parseLong(employeId));
        }
    }


    public void addEmploye() {
        employeService.addEmploye(selectedEmploye);
        selectedEmploye = new Employe(); // Reset
        init(); // Recharger la liste des employés
    }



    @Transactional
    public void updateEmploye() throws IOException {
        if (selectedEmploye != null && selectedEmploye.getIdEmploye() != null) {
            Employe existingEmploye = employeService.getEmploye(selectedEmploye.getIdEmploye());
            if (existingEmploye != null) {
                existingEmploye.setNom(selectedEmploye.getNom());
                existingEmploye.setAdresse(selectedEmploye.getAdresse());
                existingEmploye.setCourriel(selectedEmploye.getCourriel());
                existingEmploye.setPoste(selectedEmploye.getPoste());
                existingEmploye.setNumeroTelephone(selectedEmploye.getNumeroTelephone());
                existingEmploye.setSalaire(selectedEmploye.getSalaire());

                employeService.updateEmploye(existingEmploye);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employé mis à jour avec succès."));
                FacesContext.getCurrentInstance().getExternalContext().redirect("listEmploye.xhtml?id=" + existingEmploye.getIdEmploye());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'employé sélectionné n'existe pas."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Aucun employé sélectionné."));
        }
    }


    public void deleteEmploye() {
        if (selectedEmploye != null && selectedEmploye.getIdEmploye() != null) {
            employeService.deleteEmploye(selectedEmploye.getIdEmploye());
            employes.remove(selectedEmploye);
            selectedEmploye = new Employe(); // Reset selected employe
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employé supprimé avec succès."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur", "Aucun employé sélectionné."));
        }
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
