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
    private List<Message> messagesEnvoyes;

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
        loadMessagesEnvoyes();
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

    public List<Message> getMessagesEnvoyes() {
        return messagesEnvoyes;
    }

    public void setMessagesEnvoyes(List<Message> messagesEnvoyes) {
        this.messagesEnvoyes = messagesEnvoyes;
    }

    public void handleDestinataireSelect(Employe selectedEmploye) {
        if (!selectedDestinataires.contains(selectedEmploye)) {
            selectedDestinataires.add(selectedEmploye);
        }
    }

    public void envoyerMessage() {
        Date dateEnvoi = new Date();

        Responsable responsable = getLoggedInResponsable();
        if (responsable != null) {
            message.setDestinataires(new HashSet<>(selectedDestinataires));
            message.setResponsable(responsable);
            message.setDateEnvoi(dateEnvoi);
            messageService.saveMessage(message);

            message = new Message();
            selectedDestinataires = new ArrayList<>();

            loadMessagesEnvoyes();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Message envoyé", "Succès."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Impossible de trouver le responsable."));
        }
    }

    public Responsable getLoggedInResponsable() {
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        if (username != null) {
            return responsableService.findByUsername(username);
        }
        return null;
    }

    private void loadMessagesEnvoyes() {
        Responsable responsable = getLoggedInResponsable();
        if (responsable != null) {
            messagesEnvoyes = messageService.findMessagesByResponsable(responsable.getIdResponsable());
        } else {
            messagesEnvoyes = new ArrayList<>();
        }
    }

    // Méthode pour récupérer les messages reçus par l'employé connecté
    public List<Message> getMessagesRecus() {
        Employe employe = getLoggedInEmploye();
        if (employe != null) {
            return messageService.findMessagesForEmployee(employe.getIdEmploye());
        }
        return new ArrayList<>();
    }

    public Employe getLoggedInEmploye() {
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        if (username != null) {
            return employeService.findByUsername(username);
        }
        return null;
    }
}
