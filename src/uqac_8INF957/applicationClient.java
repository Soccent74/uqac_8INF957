/**
 * 
 */
package uqac_8INF957;
import java.io.*;
import java.net.*;
import uqac_8INF957.Commande;
/**
 * @author Benjamin Bourgeaux et Lucas Hélaine
 *
 */
public class applicationClient {
    /** ATTRIBUT **/
	static String fichier = null;
	static String fichSortie = null;
	static BufferedReader br = null;
	static InputStream ips = null;
	static InputStreamReader ipsr  = null;
	static Commande prochaine;
	
	
	/** GETTERS **/
//	public String getFichier() {
//		return fichier;
//	}
//	/** SETTERS **/
//	public void setFichier(String fichier) {
//		this.fichier = fichier;
//	}
	
	
	/**
    * prend le fichier contenant la liste des commandes, et le charge dans une
    * variable du type Commande qui est retournée
    */         
    public static Commande saisisCommande(String line) {
    	Commande ordre = new Commande(line);
		return ordre;
    }
    
    /**
    * initialise : ouvre les différents fichiers de lecture et écriture
    * @throws FileNotFoundException 
    */
    public static void initialise(String fichierCommande) throws FileNotFoundException {
	    try{	
	    	ips = new FileInputStream(fichierCommande); 
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			
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
    public static void traiteCommande(Commande uneCommande) {
		Socket socket;
		try {
			// Ouverture de la socket client. :)
			socket = new Socket(InetAddress.getLocalHost(),2009);	
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			 // Permet de vider le buffer. 
			out.flush();
	       
//			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
	        
			// 
			out.writeObject(uneCommande);
	        // Permet de vider le buffer. 
	        out.flush();
			
			
			
			
			// Fermeture de la socket client.
			socket.close();
		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
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
    	fichier = "commandes.txt";
		//lecture du fichier texte	
		try{
			initialise(fichier);
			String ligne;
			while ((ligne=br.readLine())!=null){
				prochaine = saisisCommande(ligne);
				traiteCommande(prochaine);
			}
			br.close(); 
		}catch (Exception e){
			System.out.println(e.toString());
		}
		

	}
}
