package com.example.applicationgestinemployes.controller;

import com.example.applicationgestinemployes.model.Conge;
import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.service.CongeService;
import com.example.applicationgestinemployes.service.EmployeService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class CongeController implements Serializable {

    @Inject
    private CongeService congeService;

    @Inject
    private EmployeService employeService;

    private Conge newConge = new Conge();
    private List<Conge> pendingConges;

    @PostConstruct
    public void init() {
        pendingConges = congeService.findAllPending();
    }

    public String createConge() {
        Employe employe = getLoggedInEmploye();
        newConge.setEmploye(employe);
        newConge.setStatut("EN_ATTENTE");
        congeService.create(newConge);

        // Envoyer une notification par e-mail au responsable
        String managerEmail = employe.getResponsable().getCourriel();
        String employeeName = employe.getNom();
        String leaveStartDate = newConge.getDateDebut().toString();
        String leaveEndDate = newConge.getDateFin().toString();
        congeService.sendLeaveRequestNotificationToManager(managerEmail, employeeName, leaveStartDate, leaveEndDate);

        return "success"; // Rediriger vers une page de succès si nécessaire
    }


    public void approuverConge(Long congeId) {
        congeService.approuverConge(congeId);
        pendingConges = congeService.findAllPending(); // Rafraîchir la liste
    }

    public void rejeterConge(Long congeId) {
        congeService.rejeterConge(congeId);
        pendingConges = congeService.findAllPending(); // Rafraîchir la liste
    }


    public Employe getLoggedInEmploye() {
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        if (username != null) {
            List<Employe> employes = employeService.getAllEmployes();
            for (Employe employe : employes) {
                if (employe.getCourriel().equals(username)) {
                    return employe;
                }
            }
        }
        return null;
    }

    public Conge getNewConge() {
        return newConge;
    }

    public void setNewConge(Conge newConge) {
        this.newConge = newConge;
    }

    public List<Conge> getPendingConges() {
        return pendingConges;
    }

    public void setPendingConges(List<Conge> pendingConges) {
        this.pendingConges = pendingConges;
    }
}
