/**
 * 
 */
package uqac_8INF957;
import java.io.*;
import java.net.*;
/**
 * @author Benjamin Bourgeaux et Lucas Hélaine
 *
 */
public class applicationClient {
	public static void main(String[] zero) {
		Socket socket;
		try {
			socket = new Socket(InetAddress.getLocalHost(),2009);	
			socket.close();
		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
