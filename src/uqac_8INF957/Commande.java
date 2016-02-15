package uqac_8INF957;
import java.io.*;
//import java.util.Arrays;

/**
 * @author Benjamin Bourgeaux et Lucas Helaine
 *
 */
public class Commande implements Serializable {

	/** ATTRIBUTS **/
	private String type_commande = ""; //Type de la commande
	private int taille = 2; //Nombre de sources maximum
	private String[] chemin_source = new String[taille]; //Chemin des fichiers sources
	private String chemin_classe = ""; //Chemin du fichier classe
	private String nom_classe = ""; //Nom de la classe
	private String identificateur = ""; //Identifiant de l'objet
	private String nom_attribut = ""; //Nom de l'attribut
	private String valeur = ""; //Valeur a ecrire
	private String nom_fonction = ""; //Nom de la fonction a appeler
	private String[] tabpar = new String[taille]; //Tableau des parametres
	private String[] tabval = new String[taille]; //Tableau des valeurs des parametres
	private int nb_args = 0; //Nombre d'arguments dans la commande
	private int nb_param = 0; //Nombre de parametres

	/** METHODES **/
	public Commande(String commande){ //Constructeur de la classe Commande

		String[] str = commande.split("#"); //Separation de la commande en fonction du caractere #
		nb_args = str.length;
		if((str[0].equals("compilation"))&&(nb_args == 3)){ //Si le premier terme est "compilation", je recupere les valeurs
			this.type_commande = str[0];
			String[] source = str[1].split(","); //Separation de la commande en fonction du caractere ,
			//taille = source.length;
			for(int i = 0; i < 2/*taille+1*/; i++){
				this.chemin_source[i] = source[i]; //Recuperation des chemins des sources
			}
			this.chemin_classe = str[2];
		}
		else if((str[0].equals("chargement"))&&(nb_args == 2)){ //Si le premier terme est "compilation", je recupere les valeurs
			this.type_commande = str[0];
			this.nom_classe = str[1];
		}
		else if((str[0].equals("creation"))&&(nb_args == 3)){ //Si le premier terme est "creation", je recupere les valeurs
			this.type_commande = str[0];
			this.nom_classe = str[1];
			this.identificateur = str[2];
		}
		else if((str[0].equals("lecture"))&&(nb_args == 3)){ //Si le premier terme est "lecture", je recupere les valeurs
			this.type_commande = str[0];
			this.identificateur = str[1];
			this.nom_attribut = str[2];
		}
		else if((str[0].equals("ecriture"))&&(nb_args == 4)){ //Si le premier terme est "ecriture", je recupere les valeurs
			this.type_commande = str[0];
			this.identificateur = str[1];
			this.nom_attribut = str[2];
			this.valeur = str[3];
		}
		else if((str[0].equals("fonction"))&&(nb_args >= 3)){ //Si le premier terme est "fonction", je recupere les valeurs
			this.type_commande = str[0];
			this.identificateur = str[1];
			this.nom_fonction = str[2];
			if(nb_args == 4){
				String[] param = str[3].split(","); //Separation de la commande en fonction du caractere ,
				nb_param = param.length;
				for(int i = 0; i < nb_param; i++){ //Boucle en fonction du nombre de parametres contenu dans la liste
					String[] tampon = param[i].split(":"); //Separation de la commande en fonction du caractere :
					for(int j = 0; j < nb_param; j++){
						this.tabpar[i] = tampon[0];
						this.tabval[i] = tampon[1];
					}
				}
			}
		}
		else{ //Commande mal formée
			System.out.println("Commande non traitée. Veuillez reprendre la normalisation de votre commande.");
		}
	}

	/** GETTER / SETTER **/
	public void setType_commande(String type_commande) { //Setter de type_commande
		this.type_commande = type_commande;}
	public String getType_commande() { //Getter de type_commande
		return type_commande;}

	public void setChemin_source(String[] chemin_source) { //Setter de chemin_source
		this.chemin_source = chemin_source;}
	public String[] getChemin_source() { //Getter de chemin_source
		return chemin_source;}
	
	public void setChemin_classe(String chemin_classe) { //Setter de chemin_classe
		this.chemin_classe = chemin_classe;}
	public String getChemin_classe() { //Getter de chemin_classe
		return chemin_classe;}
	
	public void setNom_classe(String nom_classe) { //Setter de nom_classe
		this.nom_classe = nom_classe;}
	public String getNom_classe() { //Getter de nom_classe
		return nom_classe;}
	
	public void setIdentificateur(String identificateur) { //Setter de identificateur
		this.identificateur = identificateur;}
	public String getIdentificateur() { //Getter de identificateur
		return identificateur;}
	
	public void setNom_attribut(String nom_attribut) { //Setter de nom_attribut
		this.nom_attribut = nom_attribut;}
	public String getNom_attribut() { //Getter de nom_attribut
		return nom_attribut;}
	
	public void setValeur(String valeur) { //Setter de valeur
		this.valeur = valeur;}
	public String getValeur() { //Getter de valeur
		return valeur;}
	
	public void setNom_fonction(String nom_fonction) { //Setter de nom_fonction
		this.nom_fonction = nom_fonction;}
	public String getNom_fonction() { //Getter de nom_fonction
		return nom_fonction;}
	
	public void setTabpar(String[] tabpar) { //Setter de tabpar
		this.tabpar = tabpar;}
	public String[] getTabpar() { //Getter de tabpar
		return tabpar;}
	
	public void setTabval(String[] tabval) { //Setter de tabval
		this.tabval = tabval;}
	public String[] getTabval() { //Getter de tabval
		return tabval;}
	
	public void setTaille(int taille) { //Setter de taille
		this.taille = taille;}
	public int getTaille() { //Getter de taille
		return taille;}
	
	/*public static void main (String [] arg){
		final String defaut = "fonction#mathilde#inscrisDansCours#ca.uqac.registraire.Cours:ID(8inf853)";
		//System.out.println(defaut);
        Commande test= new Commande(defaut);
        //System.out.println(test.getType_commande());
        //System.out.println(test.getNom_classe());
		//System.out.println(Arrays.toString(test.getChemin_source()));
        //System.out.println(test.getChemin_classe());
        //System.out.println(test.getIdentificateur());
        //System.out.println(test.getNom_attribut());
        //System.out.println(test.getValeur());
        //System.out.println(test.getNom_fonction());
        System.out.println(Arrays.toString(test.getTabpar()));
        System.out.println(Arrays.toString(test.getTabval()));        
   }*/
}