/**
 * 
 */
package uqac_8INF957;
import java.io.*;
import java.net.*;
import java.util.Arrays;

import uqac_8INF957.Commande;
/**
 * @author Benjamin Bourgeaux et Lucas Hélaine
 *
 */
public class applicationClient {
    
	/** ATTRIBUT **/
	private String fichier;
	private String fichSortie;
	private BufferedReader br;
	private InputStream ips;
	private InputStreamReader ipsr;
	private Commande prochaine;
	
	/*****************************
	 * CONSTRUCTEUR
	 ****************************/
	public applicationClient() {
		this.fichier = null;
		this.fichSortie = null;
		this.br = null;
		this.ips = null;
		this.ipsr = null;
		this.prochaine = null;
	}
	
	/****************************** 
	 * GETTERS 
	 *****************************/
	public String getFichier(){return fichier;}
	public BufferedReader getBr(){return br;}
	public String getFichSortie(){return fichSortie;}
	public InputStream getIps(){return ips;}
	public Commande getProchaine(){return prochaine;}
	public InputStreamReader getIpsr(){return ipsr;}
	
	/*******************************
	 * SETTERS 
	 ******************************/
	public void setFichier(String fichier) {this.fichier = fichier;	}
	public void setProchaine(Commande prochaine) {this.prochaine = prochaine;}
	public void setBr(BufferedReader br) {this.br = br;}
	public void setFichSortie(String fichSortie) {this.fichSortie = fichSortie;}
	public void setIps(InputStream ips) {this.ips = ips;}
	public void setIpsr(InputStreamReader ipsr) {this.ipsr = ipsr;}
	
	/*******************************
	 * METHODES
	 ******************************/
	
	/**
	*	prend le fichier contenant la liste des commandes, et le charge dans une
    * 	variable du type Commande qui est retournée
    * */         
    public Commande saisisCommande(String line) {
    	Commande ordre = new Commande(line);
		return ordre;
    }
    /**
    * 	initialise : ouvre les différents fichiers de lecture et écriture
    * 	@throws FileNotFoundException 
    */
    public void initialise(String fichierCommande) throws FileNotFoundException {
	    try{	
	    	this.ips = new FileInputStream(fichierCommande); 
			this.ipsr = new InputStreamReader(ips);
			this.br = new BufferedReader(ipsr);
		}catch (Exception e){
			System.out.println(e.toString());
		}
	}
    /**
    * prend une Commande dûment formatée, et la fait exécuter par le serveur. Le résultat de 
    * l’exécution est retournée. Si la commande ne retourne pas de résultat, on retourne null.
    * Chaque appel doit ouvrir une connexion, exécuter, et fermer la connexion. Si vous le 
    * souhaitez, vous pourriez écrire six fonctions spécialisées, une par type de commande 
	* décrit plus haut, qui seront appelées par  traiteCommande(Commande uneCommande)
    */
    public void traiteCommande(Commande uneCommande) {
    	Socket socket;
		try {
			// Création de la socket du client, et initialisation de celle-ci. 
			socket = new Socket(InetAddress.getLocalHost(), 2009);
			System.out.println("Socket client: " + socket);
			
			/** Gestion de l'envoie de la commande. **/
			// OUT : Création d'un stream d'objet avec un stream de socket. 
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	        out.flush();					// On vide le buffer.
	        out.writeObject(uneCommande); 	// Envoie de la commande au serveur via la socket.
	        out.flush();
	        
	        /** Réception de la réponse du serveur. **/
	        // IN : Création d'un stream d'objet à partir du stream de la socket. 
	        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
	        Object objetRecu = in.readObject();	// Réception de l'objet du serveur
	        objetRecu = (String)objetRecu;		// Je caste l'objet reçu comme étant une commande.
	        System.out.println("Client recoit: " + objetRecu);
	        
	        /** Fermeture des différents flux et de la socket client.**/ 
	        in.close();
	        out.close();
	        socket.close();
	        
	    /** Gestion des exceptions. **/
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
    }
    /**
    * PROGRAMME PRINCIPALE : Cette méthode doit créer une instance de la classe ApplicationClient, 
    * l’initialiser, ouvrir le fichier commandes.txt et demander au server d'exécuter les commandes.
    */
    public static void main(String[] arg) {
    /** Création de l'instance et initialisation. **/	
    	applicationClient client = new applicationClient();
    	client.setFichier("commandes.txt");
	/** On essaye de lire le fichier tout en gérant les exceptions. **/
    	try{
			client.initialise(client.getFichier());
			System.out.println("Client : INITIALISE");
			// On récupère une ligne du fichier, qui représente une commande.
			String ligne;
			// Tant que ligne n'est pas null (fin du fichier de commande), on envoie la commande au serveur. 
			while ((ligne=client.br.readLine())!=null){
				System.out.println("client ligne = : " + ligne);
				client.setProchaine(client.saisisCommande(ligne));
				System.out.println("CLIENT :" + client.getProchaine());
				// On appelle la commande qui va envoyer l'information au serveur. 
				client.traiteCommande(client.getProchaine());
			}
			client.br.close(); 
		}catch (Exception e){
			System.out.println(e.toString());
		}
	}
}
