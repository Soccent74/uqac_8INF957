/**
 * 
 */
package uqac_8INF957;
import java.io.IOException;
import java.net.*;
/**
 * @author Benjamin Bourgeaux et Lucas Hélaine
 *
 */
public class applicationServer {
	
	public static void main(String[] zero) {
		ServerSocket socketserver  ;
		Socket socketduserveur ;
		try {
	    	socketserver = new ServerSocket(2009);
	    	socketduserveur = socketserver.accept(); 
	    	System.out.println("Un zéro s'est connecté !");
	        socketserver.close();
	        socketduserveur.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	
    }// Fin du main.
	
}

