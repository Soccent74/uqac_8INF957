/**
 * 
 */
package uqac_8INF957;
import uqac_8INF957.*;
import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.*;
/**
 * @author Benjamin Bourgeaux et Lucas Hélaine
 *
 */
public class applicationServer{
	/**ATTRIBUT **/
	static ServerSocket socketserver; // Static pour être disponible partout pour toutes les instances.
	//Cours nouveau_cours = new Cours();
	
	/** METHODES **/
	public applicationServer(int port){ //prend le numéro de port, crée un SocketServer sur le port
		try {
	    	socketserver = new ServerSocket(port);
	    	System.out.println("Socket créé");
	    	aVosOrdres();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
    /**
    * Se met en attente de connexions des clients. Suite aux connexions, elle lit
    * ce qui est envoyé à travers la Socket, recrée l’objet Commande envoyé par
    * le client, et appellera traiterCommande(Commande uneCommande)
    */         
    public void aVosOrdres() {
//    	ServerSocket socketserver;
		Socket socketduserveur;
    	final BufferedReader in;
    	final PrintWriter out;
    	final Scanner sc=new Scanner(System.in);
    	try {
    	    socketduserveur = socketserver.accept();
    	    System.out.println("Connexion effectue");
    	    out = new PrintWriter(socketduserveur.getOutputStream());
    	    in = new BufferedReader (new InputStreamReader (socketduserveur.getInputStream()));
    	    String s;
    	    s = sc.next();
    	    out.println(s);
    	    out.flush();    
    	    String message_client;
    	    message_client = in.readLine();
    	    System.out.println("Commande : "+message_client);
    	    socketserver.close();
            socketduserveur.close();
    	    }
    	catch (IOException e) {
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
    			traiterCompilation(cheminsource); //peu de chances que ça marche
    		}
    	}
    	else if(nom_commande.equals("chargement")){
    		traiterChargement(uneCommande.getNom_classe());
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
    
    /**
    * traiterLecture : traite la lecture d’un attribut. Renvoies le résultat par le 
	* socket
    */
    public void traiterLecture(Object pointeurObjet, String attribut) {
    
    }
    
    /**
    * traiterEcriture : traite l’écriture d’un attribut. Confirmes au client que l’écriture
	* s’est faite correctement.
    */
    public void traiterEcriture(Object pointeurObjet, String attribut, Object valeur) {
    	
    }

    /**
    * traiterCreation : traite la création d’un objet. Confirme au client que la création
	* s’est faite correctement.
    */
    public void traiterCreation(Class classeDeLobjet, String identificateur) {
    	if(classeDeLobjet == Etudiant.class){
    		Etudiant nouvel_etudiant = new Etudiant(identificateur);
    	}
    }

    /**
    * traiterChargement : traite le chargement d’une classe. Confirmes au client que la création
	* s’est faite correctement.
    */
    public void traiterChargement(String nomQualifie) {
    	
    }

    /**
    * traiterCompilation : traite la compilation d’un fichier source java. Confirme au client
	* que la compilation s’est faite correctement. Le fichier source est donné par son chemin
	* relatif par rapport au chemin des fichiers sources.
    */
    public void traiterCompilation(String cheminRelatifFichierSource) {
    	
    }

    /**
    * traiterAppel : traite l’appel d’une méthode, en prenant comme argument l’objet
	* sur lequel on effectue l’appel, le nom de la fonction à appeler, un tableau de nom de 
	* types des arguments, et un tableau d’arguments pour la fonction. Le résultat de la 
	* fonction est renvoyé par le serveur au client (ou le message que tout s’est bien 
	* passé)
    **/
    /*public void traiterAppel(Object pointeurObjet, String nomFonction, String[] types, Object[] valeurs) {
    	
    }*/

	
	public static void main(String[] arg) {
		applicationServer serv = new applicationServer(2009);
    }// Fin du main.
	
}

