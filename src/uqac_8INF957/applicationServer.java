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
 * @author Benjamin Bourgeaux et Lucas H�laine
 *
 */
public class applicationServer{
	/**ATTRIBUT **/
	public ServerSocket socketserver; // Static pour �tre disponible partout pour toutes les instances.
	private Class classe;
	
	/** METHODES **/
	public applicationServer(int port){ //prend le num�ro de port, cr�e un SocketServer sur le port
		try {
	    	socketserver = new ServerSocket(port);
	    	System.out.println("Server : Socket cr��");
//	    	aVosOrdres();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
    /**
    * Se met en attente de connexions des clients. Suite aux connexions, elle lit
    * ce qui est envoy� � travers la Socket, recr�e l�objet Commande envoy� par
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
		
    /** On essaye d'accepter les connections et de recevoir, traiter, renvoyer les donn�es. **/
        try {
        /** Boucle permettant d'ouvrir une nouvelle socket pour chaque commande du client.  **/
			do{
				// On accepte les connections entrantes. 
				socketduserveur = this.socketserver.accept();
				System.out.println("Serveur a accepte connexion: " + socketduserveur);
				
			/** On g�re le flux d'entr�es provenant du client. **/
				in = new ObjectInputStream(socketduserveur.getInputStream());
				objetRecu = in.readObject();				
				Commande commandeRecu = (Commande) objetRecu;
				// Affichage du type de commande pour contr�le r�ception.
				System.out.println("Serveur recoit: " + commandeRecu.getType_commande());
				
			/** On g�re le flux de sortie du server qui sert aux confirmations. **/
				out = new ObjectOutputStream(socketduserveur.getOutputStream());
				out.flush();
//				String accuser = traiteCommande(commandeRecu);
				out.writeObject("Le serveur confirme le traitement de la commande sans incident.");
				out.flush();
			
			/** On ferme les flux et la socket. **/
				in.close();
		        out.close();
				socketduserveur.close();
			}while(objetRecu!=null);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
    * prend une Commande dument formatt�e, et la traite. D�pendant du type de commande, 
    * elle appelle la m�thode sp�cialis�e
    */
    public void traiteCommande(Commande uneCommande) {
    	String nom_commande;
    	nom_commande = uneCommande.getType_commande();

    	if(nom_commande.equals("compilation")){
    		for(int i = 0; i < uneCommande.getTaille();i++){
    			String cheminsource = Arrays.toString(uneCommande.getChemin_source());
    			traiterCompilation(cheminsource);
    		}
    	}
    	else if(nom_commande.equals("chargement")){
    		traiterChargement(uneCommande.getNom_classe());
    	}
    	else if(nom_commande.equals("creation")){
    		if(uneCommande.getNom_classe().equals("ca.uqac.registraire.Cours")){
    			traiterCreation(Cours.class, uneCommande.getIdentificateur());
    		}
    		else if(uneCommande.getNom_classe().equals("ca.uqac.registraire.Etudiant")){
    			traiterCreation(Etudiant.class, uneCommande.getIdentificateur());
    		}
    	}
    	else if(nom_commande.equals("lecture")){
    		/*if(uneCommande.getIdentificateur().equals("ca.uqac.registraire.Cours")){
    			traiterLecture(nouveau_cours, uneCommande.getNom_attribut());
    		}*/
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
    * traiterLecture : traite la lecture d�un attribut. Renvoies le r�sultat par le 
	* socket
    */
    public void traiterLecture(Object pointeurObjet, String attribut) {
    	if(attribut.equals("")){
    		//String titre = pointeurObjet.getTitre();
    	}
    	
    }
    
    /**
    * traiterEcriture : traite l��criture d�un attribut. Confirmes au client que l��criture
	* s�est faite correctement.
    */
    public void traiterEcriture(Object pointeurObjet, String attribut, Object valeur) {
    	
    }

    /**
    * traiterCreation : traite la cr�ation d�un objet. Confirme au client que la cr�ation
	* s�est faite correctement.
    */
    public void traiterCreation(Class classeDeLobjet, String identificateur) {
    	if(classeDeLobjet == Etudiant.class){
    		Etudiant nouvel_etudiant = new Etudiant(identificateur);
    		System.out.println("objet Etudiant bien cr��");
    	}
    	else if(classeDeLobjet == Cours.class){
    		Cours nouveau_cours = new Cours(identificateur);
    		System.out.println("objet Cours bien cr��");
    	}
    }

    /**
    * traiterChargement : traite le chargement d�une classe. Confirmes au client que la cr�ation
	* s�est faite correctement.
    */
    public void traiterChargement(String nomQualifie) {
    	try {
    		classe = Class.forName(nomQualifie);
    		System.out.println("Classe charg�e : " + nomQualifie);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
    * traiterCompilation : traite la compilation d�un fichier source java. Confirme au client
	* que la compilation s�est faite correctement. Le fichier source est donn� par son chemin
	* relatif par rapport au chemin des fichiers sources.
    */
    public void traiterCompilation(String cheminRelatifFichierSource) {
    	String command = "javac " + cheminRelatifFichierSource;
    	try {
			Process pro = Runtime.getRuntime().exec(command);
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
    }

    /**
          * traiterAppel : traite l�appel d�une m�thode, en prenant comme argument l�objet
	* sur lequel on effectue l�appel, le nom de la fonction � appeler, un tableau de nom de 
	* types des arguments, et un tableau d�arguments pour la fonction. Le r�sultat de la 
	* fonction est renvoy� par le serveur au client (ou le message que tout s�est bien 
	* pass�)
    /**/
    public void traiterAppel(Object pointeurObjet, String nomFonction, String[] types, 
Object[] valeurs) {
    	
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

