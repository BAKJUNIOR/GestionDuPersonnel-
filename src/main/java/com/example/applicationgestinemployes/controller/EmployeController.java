package com.example.applicationgestinemployes.controller;

import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.model.Responsable;
import com.example.applicationgestinemployes.service.EmployeService;
import com.example.applicationgestinemployes.service.ResponsableService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class EmployeController implements Serializable {

    private static final long serialVersionUID = 1L;


    @Inject
    private EmployeService employeService;
    @Inject
    private ResponsableService responsableService;

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



    public void addEmploye() throws IOException {
        Responsable responsable = getLoggedInResponsable();
        if (responsable != null) {
            selectedEmploye.setResponsable(responsable);
            employeService.addEmploye(selectedEmploye);
            selectedEmploye = new Employe(); // Reset
            init(); // Recharger la liste des employés

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès Employé enregistré", "Employé enregistré."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Employé non enregistré. Responsable non trouvé."));
        }
    }


//    private Responsable getLoggedInResponsable() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        Long username = (Long) facesContext.getExternalContext().getSessionMap().get("username");
//        String userRole = (String) facesContext.getExternalContext().getSessionMap().get("role");
//
//        if (username != null && "RESPONSABLE".equals(userRole)) {
//            return responsableService.findById(username);
//        }
//
//        return null;
//    }

    public Responsable getLoggedInResponsable() {
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        if (username != null) {
            return responsableService.findByUsername(username);
        }
        return null;
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
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Employé mis à jour avec succès.", "succès."));
                FacesContext.getCurrentInstance().getExternalContext().redirect("listEmploye.xhtml?id=" + existingEmploye.getIdEmploye());
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'employé sélectionné n'existe pas."));
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

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Employé supprimé avec succès.", "succès."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Aucun employé sélectionné."));
        }


    }

    public int getTotalEmployeesCount() {
        List<Employe> allEmployees = employeService.getAllEmployes();
        return allEmployees != null ? allEmployees.size() : 0;
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
