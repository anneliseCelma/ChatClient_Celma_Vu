package com.chat.client;

import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.GestionnaireEvenement;
import com.chat.commun.net.Connexion;
import java.net.Socket;


/**
 * Cette classe représente un gestionnaire d'événement d'un client. Lorsqu'un client reçoit un texte d'un serveur,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementClient implements GestionnaireEvenement {
    private Client client;
    private boolean commenceEchec;

    /**
     * Construit un gestionnaire d'événements pour un client.
     *
     * @param client Client Le client pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementClient(Client client) {
        this.client = client;
    }
    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un serveur.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String typeEvenement, arg;
        String[] membres;
       
       

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            typeEvenement = evenement.getType();
            commenceEchec = false;
            
            switch (typeEvenement) {
                case "END" : //Le serveur demande de fermer la connexion
                    client.deconnecter(); //On ferme la connexion
                    break;
                case "LIST" : //Le serveur a renvoyé la liste des connectés
                    arg = evenement.getArgument();
                    membres = arg.split(":");
                    System.out.println("\t\t"+membres.length+" personnes dans le salon :");
                    for (String s:membres)
                        System.out.println("\t\t\t- "+s);
                    break;
                case "HIST":
                	String historique=evenement.getArgument();
                	String[] messages =historique.split("\n");
                	for (String message : messages) {
                		System.out.println("\t\t\t. "+message);
                	}
                	break;
                case "INV":
                	String inv=evenement.getArgument();
                	String[] Invitations=inv.split(":");
                	System.out.println("\t\t Voici la liste de vos invitations :");
                	for (String invitation : Invitations) {
                		System.out.println("\t\t\t- "+ invitation);
                	}
                	break;
                case "MOVE":
                	String POS=evenement.getArgument();
                	System.out.println("\t\t Mouvement d'échecs : " + POS);
                	
                	break;
               
                default: 
                    System.out.println("\t\t\t."+evenement.getType()+" "+evenement.getArgument());
                    
            }
        }
    }
}
