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
        System.out.println("Socket serveur: " + socketserver);
        Socket socketduserveur;
        Object objetRecu;
        ObjectInputStream in;
		try {
			do{
				socketduserveur = this.socketserver.accept();
				System.out.println("Serveur a accepte connexion: " + socketduserveur);
				
				in = new ObjectInputStream(socketduserveur.getInputStream());
				System.out.println("récupération objet"); 
		        
				objetRecu = in.readObject();
				System.out.println("Server : objet recu");
				
				Commande commandeRecu = (Commande) objetRecu;
				System.out.println("objet casté");
				 
				System.out.println("Serveur recoit: " + commandeRecu.getType_commande());
				
			}while(objetRecu!=null);
			in.close();
//	        out.close();
	        socketduserveur.close();
//	        ObjectOutputStream out = new ObjectOutputStream(socketduserveur.getOutputStream());
//	        out.flush();
//	        int[] tableauAEmettre = {7, 8, 9};
//	 		out.writeObject(tableauAEmettre);
//	        out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        
        
    }
    
    /**
    * prend une Commande dument formattée, et la traite. Dépendant du type de commande, 
    * elle appelle la méthode spécialisée
    */
    public void traiteCommande(Commande uneCommande) {
    	String nom_commande;
    	nom_commande = uneCommande.getType_commande();

    	if(nom_commande.equals("compilation")){
    		for(int i = 0; i < uneCommande.getTaille();i++){
    			String cheminsource = Arrays.toString(uneCommande.getChemin_source());
    			//traiterCompilation(cheminsource); //peu de chances que ça marche
    		}
    	}
    	else if(nom_commande.equals("chargement")){
    		//traiterChargement(uneCommande.getNom_classe());
    	}
    	else if(nom_commande.equals("creation")){
    		if(uneCommande.getNom_classe().equals("ca.uqac.registraire.Cours")){
    			//traiterCreation(Cours.class, uneCommande.getIdentificateur());
    		}
    		else if(uneCommande.getNom_classe().equals("ca.uqac.registraire.Etudiant")){
    			//traiterCreation(Etudiant.class, uneCommande.getIdentificateur());
    		}
    	}
    	else if(nom_commande.equals("lecture")){
    		//traiterLecture(Object pointeurObjet, uneCommande.getNom_attribut());
    	}
    	else if(nom_commande.equals("ecriture")){
    		//traiterEcriture(Object pointeurObjet, uneCommande.getNom_attribut(), Object valeur);
    	}
    	else if(nom_commande.equals("fonction")){
    		//traiterAppel(Object pointeurObjet, uneCommande.getNom_fonction(), uneCommande.getTabpar(), Object[] valeurs);
    	}
    	else{
    		
    	}
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

