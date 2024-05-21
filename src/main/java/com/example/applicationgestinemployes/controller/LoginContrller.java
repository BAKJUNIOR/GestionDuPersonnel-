package com.example.applicationgestinemployes.controller;

import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.model.Responsable;
import com.example.applicationgestinemployes.service.EmployeServiceImpl;
import com.example.applicationgestinemployes.service.ResponsableService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

@Named
@SessionScoped
public class LoginContrller implements Serializable {

    private String username;
    private String password;
    private String role;

    @Inject
    private EmployeServiceImpl employeService;

    @Inject
    private ResponsableService responsableService;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String login() {
        // Vérifier les informations de connexion pour les employés
        List<Employe> employes = employeService.getAllEmployes();
        for (Employe employe : employes) {
            if (employe.getUsername().equals(username) && employe.getPassword().equals(password)) {
                role = "EMPLOYE";
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
                return "/views/admin/indexEmploye.xhtml?faces-redirect=true";
            }
        }

        // Vérifier les informations de connexion pour les responsables
        List<Responsable> responsables = responsableService.findAll();
        for (Responsable responsable : responsables) {
            if (responsable.getNom().equals(username) && responsable.getPassword().equals(password)) {
                role = "RESPONSABLE";
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
                return "/views/admin/index.xhtml?faces-redirect=true";
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid username or password"));
        return null;
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean isEmploye() {
        return "EMPLOYE".equals(role);
    }

    public boolean isResponsable() {
        return "RESPONSABLE".equals(role);
    }
}
