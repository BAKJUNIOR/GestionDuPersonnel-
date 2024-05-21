package com.example.applicationgestinemployes.controller;

import com.example.applicationgestinemployes.model.Message;
import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.service.EmployeServiceImpl;
import com.example.applicationgestinemployes.service.MessageService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

@Named
@RequestScoped
public class MessageController implements Serializable {

    @Inject
    private MessageService messageService;

    @Inject
    private EmployeServiceImpl employeService;

    private Message newMessage = new Message();
    private List<Message> employeeMessages;
    private List<Employe> allEmployees;
    private List<Employe> selectedEmployees;

    @PostConstruct
    public void init() {
        Employe loggedInEmployee = getLoggedInEmployee();
        if (loggedInEmployee != null) {
            employeeMessages = messageService.getMessagesForEmployee(loggedInEmployee.getIdEmploye());
        }
        allEmployees = employeService.getAllEmployes();
    }

    public String sendMessage() {
        Employe sender = getLoggedInEmployee();
        if (sender != null) {
            newMessage.setResponsable(sender.getResponsable());
            newMessage.setDestinataires(new HashSet<>(selectedEmployees));
            messageService.sendMessage(newMessage);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Message envoyé avec succès"));
            newMessage = new Message();  // Reset the message after sending
            selectedEmployees = null;    // Reset the selected employees
            return null; // Stay on the same page
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employé non trouvé"));
            return null;
        }
    }

    public Employe getLoggedInEmployee() {
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        if (username != null) {
            return employeService.findByUsername(username);
        }
        return null;
    }

    // Getters and Setters

    public List<Employe> getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(List<Employe> allEmployees) {
        this.allEmployees = allEmployees;
    }

    public List<Employe> getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(List<Employe> selectedEmployees) {
        this.selectedEmployees = selectedEmployees;
    }

    public Message getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(Message newMessage) {
        this.newMessage = newMessage;
    }

    public List<Message> getEmployeeMessages() {
        return employeeMessages;
    }

    public void setEmployeeMessages(List<Message> employeeMessages) {
        this.employeeMessages = employeeMessages;
    }
}
