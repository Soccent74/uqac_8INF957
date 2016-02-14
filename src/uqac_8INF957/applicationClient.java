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
	
	/**
	 * CONSTRUCTEUR
	 * **/
	public applicationClient() {
		this.fichier = null;
		this.fichSortie = null;
		this.br = null;
		this.ips = null;
		this.ipsr = null;
		this.prochaine = null;
	}
	
	/** 
	 * GETTERS 
	 * **/
	public String getFichier() {
		return fichier;
	}
	public BufferedReader getBr() {
		return br;
	}
	public String getFichSortie() {
		return fichSortie;
	}
	public InputStream getIps() {
		return ips;
	}
	public Commande getProchaine() {
		return prochaine;
	}
	public InputStreamReader getIpsr() {
		return ipsr;
	}
	
	/** 
	 * SETTERS 
	 * **/
	public void setFichier(String fichier) {
		this.fichier = fichier;
	}
	
	public void setProchaine(Commande prochaine) {
		this.prochaine = prochaine;
	}
	
	public void setBr(BufferedReader br) {
		this.br = br;
	}
	public void setFichSortie(String fichSortie) {
		this.fichSortie = fichSortie;
	}
	public void setIps(InputStream ips) {
		this.ips = ips;
	}
	public void setIpsr(InputStreamReader ipsr) {
		this.ipsr = ipsr;
	}
	
	/**
	 * METHODES
	 */
	
	/**
    * prend le fichier contenant la liste des commandes, et le charge dans une
    * variable du type Commande qui est retournée
    */         
    public Commande saisisCommande(String line) {
    	Commande ordre = new Commande(line);
		return ordre;
    }
    
    /**
    * initialise : ouvre les différents fichiers de lecture et écriture
    * @throws FileNotFoundException 
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
			socket = new Socket(InetAddress.getLocalHost(), 2009);
			System.out.println("Socket client: " + socket);
			
			/** Gestion de l'envoie de la commande. **/
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	        out.flush();
	        out.writeObject(uneCommande);
	        out.flush();
	        
	        /** Réception de la réponse du serveur. **/
	        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
	        Object objetRecu = in.readObject();
	        objetRecu = (String)objetRecu;
	        System.out.println("Client recoit: " + objetRecu);
	        
	        
	        /** Fermeture des différents flux.**/ 
	        in.close();
	        out.close();
	        socket.close();
	        
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
    * cette méthode vous sera fournie plus tard. Elle indiquera la séquence d’étapes à exécuter
    * pour le test. Elle fera des appels successifs à saisisCommande(BufferedReader fichier) et
    * traiteCommande(Commande uneCommande). 
    */
//    public void scenario() {
//    	sortieWriter.println("Debut des traitements:");
//		Commande prochaine = saisisCommande(commandesReader);
//		while (prochaine != null) {
//			sortieWriter.println("\tTraitement de la commande " + prochaine + " ...");
//			Object resultat = traiteCommande(prochaine);
//			sortieWriter.println("\t\tResultat: " + resultat);
//			prochaine = saisisCommande(commandesReader);
//		}
//		sortieWriter.println("Fin des traitements");
//    }
    
    /**
    * programme principal. Prend 4 arguments: 1) “hostname” du serveur, 2) numéro de port, 
    * 3) nom fichier commandes, et 4) nom fichier sortie. Cette méthode doit créer une 
    * instance de la classe ApplicationClient, l’initialiser, puis exécuter le scénario
    */
    public static void main(String[] zero) {
    	applicationClient client = new applicationClient();
    	client.setFichier("commandes.txt");
		//lecture du fichier texte	
		System.out.println("Client : Juste avant le try.");
    	try{
			client.initialise(client.getFichier());
			System.out.println("Client : INITIALISE");
			String ligne;
			
			while ((ligne=client.br.readLine())!=null){
				System.out.println("client ligne = : " + ligne);
				client.setProchaine(client.saisisCommande(ligne));
				System.out.println("CLIENT :" + client.getProchaine());
				client.traiteCommande(client.getProchaine());
			}
			client.br.close(); 
		}catch (Exception e){
			System.out.println(e.toString());
		}
		

	}
}
