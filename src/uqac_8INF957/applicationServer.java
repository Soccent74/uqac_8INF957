/**
 * 
 */
package uqac_8INF957;
import uqac_8INF957.*;
import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.*;

import org.omg.Messaging.SyncScopeHelper;
/**
 * @author Benjamin Bourgeaux et Lucas Hélaine
 *
 */
public class applicationServer{
	/**ATTRIBUT **/
	public ServerSocket socketserver; // Static pour être disponible partout pour toutes les instances.
	private Class classe;
	private int tailletab = 2;
	Etudiant nouvel_etudiant;
	Cours nouveau_cours;
	private Cours[] tabcours = new Cours[tailletab];
	private Etudiant[] tabetudiant = new Etudiant[tailletab];
	static int compt_cours = 0;
	static int compt_etud = 0;
	
	/** METHODES **/
	public applicationServer(int port){ //prend le numéro de port, crée un SocketServer sur le port
		try {
	    	socketserver = new ServerSocket(port);
	    	System.out.println("Server : Socket créé");
//	    	aVosOrdres();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
    /**
    * Se met en attente de connexions des clients. Suite aux connexions, elle lit
    * ce qui est envoyé à travers la Socket, recrée l’objet Commande envoyé par
    * le client, et appellera traiterCommande(Commande uneCommande)
     * @throws ClassNotFoundException 
    */         
    public void aVosOrdres() throws ClassNotFoundException {
    /** Variable de la fonction. **/
    	System.out.println("Socket serveur: " + socketserver);
        Socket socketduserveur;
        Object objetRecu;
        ObjectInputStream in;
        ObjectOutputStream out;
		
    /** On essaye d'accepter les connections et de recevoir, traiter, renvoyer les données. **/
        try {
        /** Boucle permettant d'ouvrir une nouvelle socket pour chaque commande du client.  **/
			do{
				// On accepte les connections entrantes. 
				socketduserveur = this.socketserver.accept();
				System.out.println("Serveur a accepte connexion: " + socketduserveur);
				
			/** On gère le flux d'entrées provenant du client. **/
				in = new ObjectInputStream(socketduserveur.getInputStream());
				objetRecu = in.readObject();				
				Commande commandeRecu = (Commande) objetRecu;
				// Affichage du type de commande pour contrôle réception.
				System.out.println("Serveur recoit: " + commandeRecu.getType_commande());
				
			/** On gère le flux de sortie du server qui sert aux confirmations. 
			 *  C'est dans traite commande que l'on fait appel à la procédure de 
			 *  traitement de la commande.
			 * **/
				out = new ObjectOutputStream(socketduserveur.getOutputStream());
				out.flush();
				String accuser = traiteCommande(commandeRecu);
				out.writeObject(accuser/*"Le serveur confirme le traitement de la commande sans incident."*/);
				out.flush();

				/** On ferme les flux et la socket. **/
				in.close();
				out.close();
				socketduserveur.close();
			}while(objetRecu!=null);
        } catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
    }
    
    /**
    * prend une Commande dument formattée, et la traite. Dépendant du type de commande, elle appelle la méthode spécialisée
    */
    public String traiteCommande(Commande uneCommande) {
    	String resultat = "";
    	String nom_commande;
    	nom_commande = uneCommande.getType_commande();
    	if(nom_commande.equals("compilation")){
    		System.out.println("Je suis rentre dans compilation");
    		for(int i = 0; i < uneCommande.getTaille();i++){
    			String cheminsource = Arrays.toString(uneCommande.getChemin_source());
    			resultat = traiterCompilation(cheminsource);
    		}
    		//System.out.println("La compilation ne marche pas");
    	}
    	else if(nom_commande.equals("chargement")){
    		System.out.println("Je suis rentre dans chargement");
    		resultat = traiterChargement(uneCommande.getNom_classe());
    	}
    	else if(nom_commande.equals("creation")){
    		System.out.println("Je suis rentre dans creation");
    		if(uneCommande.getNom_classe().equals("uqac_8INF957.Cours")){
    			resultat = traiterCreation(Cours.class, uneCommande.getIdentificateur());
    		}
    		else if(uneCommande.getNom_classe().equals("uqac_8INF957.Etudiant")){
    			resultat = traiterCreation(Etudiant.class, uneCommande.getIdentificateur());
    		}
    	}
    	else if(nom_commande.equals("lecture")){
    		System.out.println("Je suis rentre dans lecture");
    		if(uneCommande.getIdentificateur().matches("[0-9]+[a-zA-Z]+[0-9]+")){
    			resultat = traiterLecture(tabcours[compt_cours-1], uneCommande.getNom_attribut());
    		}
    		else{
    			resultat = traiterLecture(tabetudiant[compt_etud-1], uneCommande.getNom_attribut());
    		}
    	}
    	else if(nom_commande.equals("ecriture")){
    		System.out.println("Je suis rentre dans ecriture");
    		if(uneCommande.getIdentificateur().matches("[0-9]+[a-zA-Z]+[0-9]+")){
    			resultat = traiterEcriture(tabcours[compt_cours-1], uneCommande.getNom_attribut(), uneCommande.getValeur());
    		}
    		else{
    			resultat = traiterEcriture(tabetudiant[compt_etud-1], uneCommande.getNom_attribut(), uneCommande.getValeur());
    		}
    	}
    	else if(nom_commande.equals("fonction")){
    		System.out.println("Je suis rentre dans fonction");
    		if(uneCommande.getIdentificateur().matches("[0-9]+[a-zA-Z]+[0-9]+")){
    			resultat = traiterAppel(tabcours[compt_cours-1], uneCommande.getNom_fonction(), uneCommande.getTabpar(), uneCommande.getTabval());
    		}
    		else{
    			resultat = traiterAppel(tabetudiant[compt_etud-1], uneCommande.getNom_fonction(), uneCommande.getTabpar(), uneCommande.getTabval());
    		}
    	}
    	else{
    		System.out.println("Erreur dans la commande");
    	}
    	return resultat;
    }
    /**
    * traiterLecture : traite la lecture d’un attribut. Renvoies le résultat par le socket
    */
    public String traiterLecture(Object pointeurObjet, String attribut) {
    	String res = "";
    	if(attribut.equals("titre")){
    		res = ((Cours) pointeurObjet).getTitre();
    		System.out.println(res);
    	}
    	else if(attribut.equals("prenom")){
    		res = ((Etudiant) pointeurObjet).getNom();
    		System.out.println(res);
    	}
    	return res;
    }
    /**
    * traiterEcriture : traite l’écriture d’un attribut. Confirmes au client que l’écriture s’est faite correctement.
    */
    public String traiterEcriture(Object pointeurObjet, String attribut, String valeur) {
    	String res = "";
    	if(attribut.equals("titre")){
    		((Cours) pointeurObjet).setTitre(valeur);
    		System.out.println("La valeur titre a été mis à jour");
    		res = "La valeur titre a été mis à jour";
    	}
       	else if((attribut.equals("prenom")||attribut.equals("nom"))){
       		((Etudiant) pointeurObjet).setNom(valeur);
    		System.out.println("La valeur nom a été mis à jour");
    		res = "La valeur nom a été mis à jour";
       	}
    	return res;
    }
    /**
    * traiterCreation : traite la création d’un objet. Confirme au client que la création s’est faite correctement.
    */
    public String traiterCreation(Class classeDeLobjet, String identificateur) {
    	String res = "";
    	if(compt_etud > tailletab){
    		System.out.println("Il y a trop d'etudiants");
    		res = "Il y a trop d'etudiants";
		}
    	else if(compt_cours > tailletab){
    		System.out.println("Il y a trop de cours");
    		res = "Il y a trop de cours";
    	}
    	else{
    		if(classeDeLobjet == Etudiant.class){
        		nouvel_etudiant = new Etudiant(identificateur);//mettre en attribut puis mettre à jour
        		tabetudiant[compt_etud] = nouvel_etudiant;
        		System.out.println("objet Etudiant bien créé");
        		nouvel_etudiant = null;
        		compt_etud++;
        		res = "L'objet Etudiant a été créé";
        	}
        	else if(classeDeLobjet == Cours.class){
        		nouveau_cours = new Cours(identificateur);
        		tabcours[compt_cours] = nouveau_cours;
        		System.out.println("objet Cours bien créé");
        		nouveau_cours = null;
        		compt_cours++;
        		res = "L'objet Cours a été créé";
        	}
    	}
    	return res;
    }
    /**
    * traiterChargement : traite le chargement d’une classe. Confirmes au client que la création s’est faite correctement.
    */
    public String traiterChargement(String nomQualifie) {
    	String res = "";
    	try {
    		classe = Class.forName(nomQualifie);
    		System.out.println("Classe chargée : " + nomQualifie);
    		res = "La classe a été chargée";
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return res;
    }
    /**
    * traiterCompilation : traite la compilation d’un fichier source java. Confirme au client
	* que la compilation s’est faite correctement. Le fichier source est donné par son chemin
	* relatif par rapport au chemin des fichiers sources.
    */
    public String traiterCompilation(String cheminRelatifFichierSource) {
    	String res = "";
    	String command = "javac " + cheminRelatifFichierSource;
    	try {
			Process pro = Runtime.getRuntime().exec(command);
			res = "La compilation a réussi";
			try {
				pro.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return res;
    }
    /**
    * traiterAppel : traite l’appel d’une méthode, en prenant comme argument l’objet
	* sur lequel on effectue l’appel, le nom de la fonction à appeler, un tableau de nom de 
	* types des arguments, et un tableau d’arguments pour la fonction. Le résultat de la 
	* fonction est renvoyé par le serveur au client (ou le message que tout s’est bien 
	* passé)
    /**/
    public String traiterAppel(Object pointeurObjet, String nomFonction, String[] types, String[] valeurs) {
    	String res = "";
    	if(nomFonction.equals("titre")){
    		//nouveau_cours.setTitre(valeur); //MEGA CASSEGUEULE
    		System.out.println("La valeur titre a été mis à jour");
    		res = "La valeur titre a été mis à jour";
    	}
       	else if(nomFonction.equals("prenom")){
    		//nouvel_etudiant.setNom(valeur); //MEGA CASSEGUEULE
    		System.out.println("La valeur nom a été mis à jour");
    		res = "La valeur nom a été mis à jour";
       	}
    	return res;
    }

	public static void main(String[] arg) {
		applicationServer serv = new applicationServer(2009);
		try {
			serv.aVosOrdres();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }// Fin du main.
	
}

