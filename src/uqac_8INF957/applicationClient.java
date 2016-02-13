/**
 * 
 */
package uqac_8INF957;
import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 * @author Benjamin Bourgeaux et Lucas H�laine
 *
 */
public class applicationClient {
	public static void main(String[] zero) {
		File streamCommande = null;
//	    String filePath = "commandes.txt";

	    
	    // On test si on peut ouvrir le fichier, ainsi que si il n'y a pas de probl�me avec les entr�es/sorties. 
		try { 
	    	streamCommande = new File("commandes.txt");
	    	Scanner scanner=new Scanner(streamCommande);
	    	 
	    	// On boucle sur chaque champ detect�
	    	while (scanner.hasNextLine()) {
	    	    String line = scanner.nextLine();
	    	    System.out.println(line);
	    		//faites ici votre traitement
	    	    
	    	    
	    	    
	    	}
	    	scanner.close();
	    }catch(FileNotFoundException e) {
	         // Cette exception est lev�e si l'objet FileInputStream ne trouve aucun fichier
	         e.printStackTrace();
	    }catch(IOException e) {
	         // Celle-ci se produit lors d'une erreur d'�criture ou de lecture
	         e.printStackTrace();
	    }
		
//		Socket socket;
//		try {
//			socket = new Socket(InetAddress.getLocalHost(),2009);	
//			socket.close();
//		}catch (UnknownHostException e) {
//			e.printStackTrace();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
