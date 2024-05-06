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
            for (int i = 0; i < 10; i++) {
                // Créer un employé
                Employe employe1 = new Employe();
                employe1.setNom("John Doe");
                employe1.setAdresse("123 rue de la Rue");
                employe1.setNumeroTelephone("1234567890");
                employe1.setCourriel("john.doe@example.com");
                employe1.setPoste("Développeur");
                employe1.setSalaire(50000.0);

                // Créer un congé pour l'employé 1
                Conge conge1 = new Conge();
                conge1.setDateDebut(new Date());
                conge1.setDateFin(new Date());
                conge1.setMotif("Vacances");
                conge1.setStatut("En attente");
                conge1.setEmploye(employe1);
                employe1.getConges().add(conge1);

                // Créer un deuxième employé
                Employe employe2 = new Employe();
                employe2.setNom("Jane Doe");
                employe2.setAdresse("456 avenue de l'Avenue");
                employe2.setNumeroTelephone("0987654321");
                employe2.setCourriel("jane.doe@example.com");
                employe2.setPoste("Manager");
                employe2.setSalaire(60000.0);

                // Créer un deuxième congé pour l'employé 2
                Conge conge2 = new Conge();
                conge2.setDateDebut(new Date());
                conge2.setDateFin(new Date());
                conge2.setMotif("Maladie");
                conge2.setStatut("En attente");
                conge2.setEmploye(employe2);
                employe2.getConges().add(conge2);

                // Créer un responsable
                Responsable responsable = new Responsable();
                responsable.setNom("Responsable 1");
                responsable.setAdresse("789 boulevard du Boulevard");
                responsable.setNumeroTelephone("1357924680");
                responsable.setCourriel("responsable1@example.com");

                // Créer plusieurs messages
                for (int j = 0; j < 5; j++) {
                    Message message = new Message();
                    message.setContenu("Message " + (j + 1));
                    message.setDateEnvoi(new Date());
                    message.setResponsable(responsable);
                    message.getDestinataires().add(employe2); // Envoi à l'employé 2

                    // Ajouter le message aux messages envoyés par le responsable
                    responsable.getMessagesEnvoyes().add(message);

                    // Enregistrer le message dans la base de données
                    entityManager.persist(message);
                }

                // Enregistrer les autres entités dans la base de données
                entityManager.persist(employe1);
                entityManager.persist(employe2);
                entityManager.persist(conge1);
                entityManager.persist(conge2);
                entityManager.persist(responsable);
            }

            // Valider la transaction
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Si une exception se produit, annuler la transaction
            entityManager.getTransaction().rollback();
        } finally {
            // Fermer l'EntityManager et l'EntityManagerFactory
            entityManager.close();
            entityManagerFactory.close();
        }
    }
    }
