package com.example.applicationgestinemployes.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "conge")
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConge;

    private Date dateDebut;
    private Date dateFin;
    private String motif;
    private String statut;


    @ManyToOne
    @JoinColumn(name = "id_employe")
    private Employe employe;
    // Getters and setters


    public Long getIdConge() {
        return idConge;
    }

    public void setIdConge(Long idConge) {
        this.idConge = idConge;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
}