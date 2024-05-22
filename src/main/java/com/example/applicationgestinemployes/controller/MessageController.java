package com.example.applicationgestinemployes.controller;

import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.model.Message;
import com.example.applicationgestinemployes.model.Responsable;
import com.example.applicationgestinemployes.service.EmployeService;
import com.example.applicationgestinemployes.service.MessageService;
import com.example.applicationgestinemployes.service.ResponsableService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Named
@SessionScoped
public class MessageController implements Serializable {
    private Message message;
    private List<Employe> employes;
    private List<Employe> selectedDestinataires;

    @Inject
    private EmployeService employeService;

    @Inject
    private MessageService messageService;

    @Inject
    private ResponsableService responsableService;

    @PostConstruct
    public void init() {
        message = new Message();
        employes = employeService.getAllEmployes();
        selectedDestinataires = new ArrayList<>();
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Employe> getSelectedDestinataires() {
        return selectedDestinataires;
    }

    public void setSelectedDestinataires(List<Employe> selectedDestinataires) {
        this.selectedDestinataires = selectedDestinataires;
    }

    public void handleDestinataireSelect(Employe selectedEmploye) {
        if (!selectedDestinataires.contains(selectedEmploye)) {
            selectedDestinataires.add(selectedEmploye);
        }
    }

    public void envoyerMessage() {
        // Récupérer la date actuelle
        Date dateEnvoi = new Date();

        Responsable responsable = getLoggedInResponsable();
        if (responsable != null) {
            message.setDestinataires(new HashSet<>(selectedDestinataires));
            message.setResponsable(responsable); // Définir le responsable récupéré
            message.setDateEnvoi(dateEnvoi); // Définir la date d'envoi
            messageService.saveMessage(message);
            // Réinitialiser le message après l'envoi
            message = new Message();
            selectedDestinataires = new ArrayList<>();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Message envoyé."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Impossible de trouver le responsable."));
        }
    }

    public Responsable getLoggedInResponsable() {
        Long userId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        String userRole = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role");
        System.out.println("Récupération de l'utilisateur connecté. ID: " + userId + ", Role: " + userRole);

        if (userId != null && "RESPONSABLE".equals(userRole)) {
            return responsableService.findByCourriel(userRole);
        }
        return null;
    }
}
