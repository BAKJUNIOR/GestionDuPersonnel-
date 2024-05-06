package com.example.applicationgestinemployes.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;

    private String contenu;
    private Date dateEnvoi;
    private String sujet;


    @ManyToOne
    @JoinColumn(name = "id_responsable")
    private Responsable responsable;

    @ManyToMany
    @JoinTable(
            name = "employe_message",
            joinColumns = @JoinColumn(name = "id_message"),
            inverseJoinColumns = @JoinColumn(name = "id_employe")
    )
    private Set<Employe> destinataires = new HashSet<>();

    // Getters and setters


    public Long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public Set<Employe> getDestinataires() {
        return destinataires;
    }

    public void setDestinataires(Set<Employe> destinataires) {
        this.destinataires = destinataires;
    }
}
