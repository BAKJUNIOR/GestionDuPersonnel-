package com.example.applicationgestinemployes;

import com.example.applicationgestinemployes.model.Conge;
import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.model.Message;
import com.example.applicationgestinemployes.model.Responsable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Date;

public class TestHibernate {

    public static void main(String[] args) {
        // Créer une instance d'EntityManagerFactory à partir du fichier de configuration persistence.xml
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestEmploye_dbConfig");

        // Créer une instance d'EntityManager à partir de l'EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Ouvrir une transaction
        entityManager.getTransaction().begin();

        try {
            // Création des employés
            Employe employe1 = new Employe();
            employe1.setNom("John Doe");
            employe1.setAdresse("123 rue de la Rue");
            employe1.setNumeroTelephone("1234567890");
            employe1.setCourriel("john.doe@example.com");
            employe1.setPoste("Développeur");
            employe1.setSalaire(50000.0);

            Employe employe2 = new Employe();
            employe2.setNom("Jane Doe");
            employe2.setAdresse("456 avenue de l'Avenue");
            employe2.setNumeroTelephone("0987654321");
            employe2.setCourriel("jane.doe@example.com");
            employe2.setPoste("Manager");
            employe2.setSalaire(60000.0);

            // Création des congés
            Conge conge1 = new Conge();
            conge1.setDateDebut(new Date());
            conge1.setDateFin(new Date());
            conge1.setMotif("Vacances");
            conge1.setStatut("En attente");
            conge1.setEmploye(employe1);

            Conge conge2 = new Conge();
            conge2.setDateDebut(new Date());
            conge2.setDateFin(new Date());
            conge2.setMotif("Maladie");
            conge2.setStatut("En attente");
            conge2.setEmploye(employe2);

            // Création du responsable
            Responsable responsable = new Responsable();
            responsable.setNom("Responsable 1");
            responsable.setAdresse("789 boulevard du Boulevard");
            responsable.setNumeroTelephone("1357924680");
            responsable.setCourriel("responsable1@example.com");

            // Création des messages
            Message message1 = new Message();
            message1.setContenu("Message 1");
            message1.setDateEnvoi(new Date());
            message1.setResponsable(responsable);
            message1.getDestinataires().add(employe2);

            Message message2 = new Message();
            message2.setContenu("Message 2");
            message2.setDateEnvoi(new Date());
            message2.setResponsable(responsable);
            message2.getDestinataires().add(employe2);

            // Association des employés avec le responsable
            employe1.setResponsable(responsable);
            employe2.setResponsable(responsable);

            // Persist des entités
            entityManager.persist(employe1);
            entityManager.persist(employe2);
            entityManager.persist(conge1);
            entityManager.persist(conge2);
            entityManager.persist(responsable);
            entityManager.persist(message1);
            entityManager.persist(message2);

            // Valider la transaction
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Annuler la transaction en cas d'erreur
            entityManager.getTransaction().rollback();
        } finally {
            // Fermer l'EntityManager et l'EntityManagerFactory
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
