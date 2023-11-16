/*
 Etats du client :
DISCONNECTED : le client est d�connect�
SEARCHING : le client recherche le serveur
NOTFOUND : le client n'a pas trouv� le serveur
CONNECTING : le serveur a �t� trouv� mais le client attend que le serveur valide la demande (utilisateur+mot de passe)
REFUSED : le serveur a refus� la connexion car l'utilisateur ou son mot de passe sont incorrects
CONNECTED : le client est connect� 
DISCONNECTING : le client est entrain de se d�connecter
*/

package com.chat.client;

import java.net.Socket;
import java.io.*;

import com.chat.commun.net.Connexion;
import com.chat.commun.thread.Lecteur;
import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.EvenementUtil;
import com.chat.commun.thread.ThreadEcouteurDeTexte;

/**
 * Cette classe repr�sente un client capable de se connecter � un serveur.
 *
 * @author Abdelmoum�ne Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class Client implements Lecteur {

    private String adrServeur = Config.ADRESSE_SERVEUR;
    private int portServeur = Config.PORT_SERVEUR;
    private boolean connecte;
    private Connexion connexion;
    private GestionnaireEvenementClient gestionnaireEvenementClient;
    private ThreadEcouteurDeTexte vt;

    /**
     * Connecte le client au serveur en utilisant un socket. Si la connexion r�ussit, un objet
     * Connexion est cr�� qui cr�e les flux d'entr�e/sortie permettant de communiquer du texte
     * avec le serveur.
     *
     * @return boolean true, si la connexion a r�ussi. false, si la connexion �choue
     * ou si le client �tait d�j� connect�.
     */
    public boolean connecter() {
        boolean resultat = false;
        if (this.isConnecte()) //deja connecte
            return resultat;

        try {
            Socket socket = new Socket(adrServeur, portServeur);
            connexion = new Connexion(socket);
            this.setAdrServeur(adrServeur);
            this.setPortServeur(portServeur);

            //On cree l'ecouteur d'evenements pour le client :
            gestionnaireEvenementClient = new GestionnaireEvenementClient(this);

            //D�marrer le thread inspecteur de texte:
            vt = new ThreadEcouteurDeTexte(this);
            vt.start();  //la methode run() de l'ecouteur de texte s'execute en parallele avec le reste du programme.
            resultat = true;
            this.setConnecte(true);
        } catch (IOException e) {
            this.deconnecter();
        }
        return resultat;
    }

    /**
     * D�connecte le client, s'il est connect�, en fermant l'objet Connexion. Le texte "exit" est envoy� au serveur
     * pour l'informer de la d�connexion. Le thread �couteur de texte est arr�t�.
     *
     * @return boolean true, si le client s'est d�connect�, false, s'il �tait d�j� d�connect�
     */
    public boolean deconnecter() {
        if (!isConnecte())
            return false;

        connexion.envoyer("exit");
        connexion.close();
        if (vt != null)
            vt.interrupt();
        this.setConnecte(false);
        return true;
    }
    /**
     * Cette m�thode v�rifie s'il y a du texte qui arrive sur la connexion du client et, si c'est le cas, elle cr�e
     * un �v�nement contenant les donn�es du texte et demande au gestionnaire d'�v�nement client de traiter l'�v�nement.
     *
     * @author Abdelmoum�ne Toudeft
     * @version 1.0
     * @since   2023-09-20
     */
    public void lire() {

        String[] t;
        Evenement evenement;
        String texte = connexion.getAvailableText();

        if (!"".equals(texte)){
            t = EvenementUtil.extraireInfosEvenement(texte);
            evenement = new Evenement(connexion,t[0],t[1]);
            gestionnaireEvenementClient.traiter(evenement);
        }
    }
    /**
     * Cette m�thode retourne l'adresse IP du serveur sur lequel ce client se connecte.
     *
     * @return String l'adresse IP du serveur dans le format "192.168.25.32"
     * @author Abdelmoum�ne Toudeft
     * @version 1.0
     * @since   2023-09-20
     */
    public String getAdrServeur() {
        return adrServeur;
    }
    public void setAdrServeur(String adrServeur) {
        this.adrServeur = adrServeur;
    }
    /**
     * Indique si le client est connect� � un serveur..
     *
     * @return boolean true si le client est connect� et false sinon
     */
    public boolean isConnecte() {
        return connecte;
    }

    /**
     * Marque ce client comme �tant connect� ou d�connect�.
     *
     * @param connecte boolean Si true, marque le client comme �tant connect�, si false, le marque comme d�connect�
     */
    public void setConnecte(boolean connecte) {
        this.connecte = connecte;
    }

    /**
     * Retourne le port d'�coute du serveur auquel ce client se connecte.
     *
     * @return int Port d'�coute du serveur
     */
    public int getPortServeur() {
        return portServeur;
    }

    /**
     * Sp�cifie le port d'�coute du serveur sur lequel ce client se connecte.
     *
     * @param portServeur int Port d'�coute du serveur
     */
    public void setPortServeur(int portServeur) {
        this.portServeur = portServeur;
    }

    /**
     * Envoie un texte au serveur en utilisant un objet Connexion.
     *
     * @param s String texte � envoyer
     */
    public void envoyer(String s) {
        this.connexion.envoyer(s);
    }
}