package com.example.applicationgestinemployes.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;

    private String nom;
    private String adresse;
    private String numeroTelephone;
    private String courriel;
    private String poste;
    private double salaire;
    private String password;  // Ajout du champ pour le mot de passe
    private String username;  // Ajout du champ pour le nom d'utilisateur

    @ManyToOne
    @JoinColumn(name = "id_responsable")
    private Responsable responsable;

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL)
    private Set<Conge> conges = new HashSet<>();

    @ManyToMany(mappedBy = "destinataires")
    private Set<Message> messagesRecus = new HashSet<>();

    // Getters and setters

    public Long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public String getPassword() {
        return password;  // Getter pour le mot de passe
    }

    public void setPassword(String password) {
        this.password = password;  // Setter pour le mot de passe
    }

    public String getUsername() {
        return username;  // Getter pour le nom d'utilisateur
    }

    public void setUsername(String username) {
        this.username = username;  // Setter pour le nom d'utilisateur
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public Set<Conge> getConges() {
        return conges;
    }

    public void setConges(Set<Conge> conges) {
        this.conges = conges;
    }

    public Set<Message> getMessagesRecus() {
        return messagesRecus;
    }

    public void setMessagesRecus(Set<Message> messagesRecus) {
        this.messagesRecus = messagesRecus;
    }
}
