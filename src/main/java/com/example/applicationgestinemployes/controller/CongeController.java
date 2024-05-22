package com.example.applicationgestinemployes.controller;

import com.example.applicationgestinemployes.model.Conge;
import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.service.CongeService;
import com.example.applicationgestinemployes.service.EmployeService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
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
    private List<Conge> findAllCongeAll;
    private List<Conge> employeeConges;

    @PostConstruct
    public void init() {
        findAllCongeAll = congeService.findAllCongeAll();
        pendingConges = congeService.findAllPending();
        Employe employe = getLoggedInEmploye();
        if (employe != null) {
            employeeConges = congeService.findCongesByEmploye(employe);
        }
    }

    public String createConge() {
        Employe employe = getLoggedInEmploye();
        if (employe != null) {
            newConge.setEmploye(employe);
            newConge.setStatut("EN_ATTENTE");
            congeService.create(newConge);

            if (employe.getResponsable() != null) {
                String managerEmail = employe.getResponsable().getCourriel();
                String employeeName = employe.getNom();
                String leaveStartDate = newConge.getDateDebut().toString();
                String leaveEndDate = newConge.getDateFin().toString();
                congeService.sendLeaveRequestNotificationToManager(managerEmail, employeeName, leaveStartDate, leaveEndDate);
            }

            return "success";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employé non trouvé"));
            return null;
        }
    }

    public void approuverConge(Long congeId) {
        congeService.approuverConge(congeId);
        pendingConges = congeService.findAllPending();
    }

    public void rejeterConge(Long congeId) {
        congeService.rejeterConge(congeId);
        pendingConges = congeService.findAllPending();
    }

    public Employe getLoggedInEmploye() {
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        if (username != null) {
            List<Employe> employes = employeService.getAllEmployes();
            for (Employe employe : employes) {
                if (employe.getUsername().equals(username)) {
                    return employe;
                }
            }
        }
        return null;
    }


    public int getPendingCongesCount() {
        return pendingConges != null ? pendingConges.size() : 0;
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

    public List<Conge> getEmployeeConges() {
        return employeeConges;
    }

    public void setEmployeeConges(List<Conge> employeeConges) {
        this.employeeConges = employeeConges;
    }

    public List<Conge> getFindAllCongeAll() {
        return findAllCongeAll;
    }

    public void setFindAllCongeAll(List<Conge> findAllCongeAll) {
        this.findAllCongeAll = findAllCongeAll;
    }
}
