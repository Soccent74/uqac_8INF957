/**
 * 
 */
package uqac_8INF957;
import java.io.*;
import java.util.Arrays;

/**
 * @author lucas
 *
 */
public class Commande implements Serializable {

	private String type_commande = ""; //type de la commande
	private int taille = 2; //nombre de sources maximum
	private String[] chemin_source = new String[taille]; //chemin des fichiers sources
	private String chemin_classe = ""; //chemin du fichier classe
	private String nom_classe = ""; //nom de la classe
	private String identificateur = ""; //identifiant de l'objet
	private String nom_attribut = ""; //nom de l'attribut
	private String valeur = ""; //valeur à écrire
	private String nom_fonction = ""; //nom de la fonction à appeler

	public Commande(String commande){

		String[] str = commande.split("#");
		if(str[0].equals("compilation")){
			this.type_commande = str[0];
			String[] source = str[1].split(",");
			for(int i = 0; i < 2; i++){
				this.chemin_source[i] = source[i];
			}
			this.chemin_classe = str[2];
		}
		else if(str[0].equals("chargement")){
			this.type_commande = str[0];
			this.nom_classe = str[1];
		}
		else if(str[0].equals("creation")){
			this.type_commande = str[0];
			this.nom_classe = str[1];
			this.identificateur = str[2];
		}
		else if(str[0].equals("lecture")){
			this.type_commande = str[0];
			this.identificateur = str[1];
			this.nom_attribut = str[2];
		}
		else if(str[0].equals("ecriture")){
			this.type_commande = str[0];
			this.identificateur = str[1];
			this.nom_attribut = str[2];
			this.valeur = str[3];
		}
		else{
			System.out.println("Commande non traitée");
		}
	}

	public void setType_commande(String type_commande) { //Setter de type_commande
		this.type_commande = type_commande;
	}
	public String getType_commande() { //Getter de type_commande
		return type_commande;
	}

	public void setChemin_source(String[] chemin_source) { //Setter de chemin_source
		this.chemin_source = chemin_source;
	}
	public String[] getChemin_source() { //Getter de chemin_source
		return chemin_source;
	}
	
	public void setChemin_classe(String chemin_classe) { //Setter de chemin_classe
		this.chemin_classe = chemin_classe;
	}
	public String getChemin_classe() { //Getter de chemin_classe
		return chemin_classe;
	}
	
	public void setNom_classe(String nom_classe) { //Setter de nom_classe
		this.nom_classe = nom_classe;
	}
	public String getNom_classe() { //Getter de nom_classe
		return nom_classe;
	}
	
	public void setIdentificateur(String identificateur) { //Setter de identificateur
		this.identificateur = identificateur;
	}
	public String getIdentificateur() { //Getter de identificateur
		return identificateur;
	}
	
	public void setNom_attribut(String nom_attribut) { //Setter de nom_attribut
		this.nom_attribut = nom_attribut;
	}
	public String getNom_attribut() { //Getter de nom_attribut
		return nom_attribut;
	}
	
	public void setValeur(String valeur) { //Setter de valeur
		this.valeur = valeur;
	}
	public String getValeur() { //Getter de valeur
		return valeur;
	}
	
	public void setNom_fonction(String nom_fonction) { //Setter de nom_fonction
		this.nom_fonction = nom_fonction;
	}
	public String getNom_fonction() { //Getter de nom_fonction
		return nom_fonction;
	}
	
	
	public static void main (String [] arg){
		final String defaut = "chargement#ca.uqac.registraire.Cours";
        Commande test= new Commande(defaut);
        System.out.println(test.getType_commande());
        System.out.println(test.getNom_classe());
		//System.out.println(Arrays.toString(test.getChemin_source()));
        //System.out.println(test.getChemin_classe());
        //System.out.println(test.getIdentificateur());
        //System.out.println(test.getNom_attribut());
        //System.out.println(test.getValeur());
   }

}