package uqac_8INF957;
import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Benjamin Bourgeaux et Lucas H�laine
 */
public class applicationServer{
	
	/*******************************
	 * ATTRIBUTS
	 ******************************/
	public ServerSocket socketserver; // Static pour �tre disponible partout pour toutes les instances.
	private Class classe;
	private int tailletab = 2;
	Etudiant nouvel_etudiant; //Objet Etudiant
	Cours nouveau_cours; //Objet Cours
	private Cours[] tabcours = new Cours[tailletab]; //Tableau des cours
	private Etudiant[] tabetudiant = new Etudiant[tailletab]; //Tableau des �tudiants
	static int compt_cours = 0; //Compteur de cours
	static int compt_etud = 0; //Compteur d'�tudiants
	
	/*******************************
	 * CONSTRUCTEUR
	 ******************************/
	public applicationServer(int port){ //prend le num�ro de port, cr�e un SocketServer sur le port
		try {
	    	socketserver = new ServerSocket(port); //Instanciation du socket
	    	System.out.println("Server : Socket cr��");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*******************************
	 * METHODES
	 ******************************/
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
		
    /** On essaye d'accepter les connexions et de recevoir, traiter, renvoyer les donn�es. **/
        try {
        /** Boucle permettant d'ouvrir une nouvelle socket pour chaque commande du client.  **/
			do{
				// On accepte les connexions entrantes. 
				socketduserveur = this.socketserver.accept();
				System.out.println("Serveur a accepte connexion: " + socketduserveur);
				
			/** On g�re le flux d'entr�es provenant du client. **/
				in = new ObjectInputStream(socketduserveur.getInputStream());
				objetRecu = in.readObject();				
				Commande commandeRecu = (Commande) objetRecu;
				// Affichage du type de commande pour contr�le r�ception.
				System.out.println("Serveur recoit: " + commandeRecu.getType_commande());
				
			/** On g�re le flux de sortie du server qui sert aux confirmations. 
			 *  C'est dans traiteCommande que l'on fait appel � la proc�dure de 
			 *  traitement de la commande.
			 * **/
				out = new ObjectOutputStream(socketduserveur.getOutputStream());
				out.flush();
				String accuser = traiteCommande(commandeRecu);
				out.writeObject(accuser);
				out.flush();

				/** On ferme les flux et la socket. **/
				in.close();
				out.close();
				socketduserveur.close();
			}while(objetRecu!=null);
        } catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
    }
    
    /**
    * prend une Commande d�ment formatt�e, et la traite. D�pendant du type de commande, elle appelle la m�thode sp�cialis�e
    */
    public String traiteCommande(Commande uneCommande) {
    	String resultat = "";
    	String nom_commande;
    	nom_commande = uneCommande.getType_commande();
    	/** Appel de la compilation **/
    	if(nom_commande.equals("compilation")){
    		/*System.out.println("Je suis rentre dans compilation");
    		for(int i = 0; i < uneCommande.getTaille();i++){
    			String cheminsource = Arrays.toString(uneCommande.getChemin_source());
    			System.out.println(cheminsource);
    			resultat = traiterCompilation(cheminsource);
    		}*/
    		System.out.println("La compilation ne marche pas");
    	}
    	/** Appel du chargement **/
    	else if(nom_commande.equals("chargement")){
    		System.out.println("Je suis rentre dans chargement");
    		resultat = traiterChargement(uneCommande.getNom_classe());
    	}
    	/** Appel de la creation **/
    	else if(nom_commande.equals("creation")){
    		System.out.println("Je suis rentre dans creation");
    		if(uneCommande.getNom_classe().equals("uqac_8INF957.Cours")){ //Teste l'objet � cr�er
    			resultat = traiterCreation(Cours.class, uneCommande.getIdentificateur());
    		}
    		else if(uneCommande.getNom_classe().equals("uqac_8INF957.Etudiant")){
    			resultat = traiterCreation(Etudiant.class, uneCommande.getIdentificateur());
    		}
    	}
    	/** Appel de la lecture **/
    	else if(nom_commande.equals("lecture")){
    		System.out.println("Je suis rentre dans lecture");
    		if(uneCommande.getIdentificateur().matches("[0-9]+[a-zA-Z]+[0-9]+")){ //Teste si c'est un cours
    			resultat = traiterLecture(tabcours[compt_cours-1], uneCommande.getNom_attribut());
    		}
    		else{
    			resultat = traiterLecture(tabetudiant[compt_etud-1], uneCommande.getNom_attribut());
    		}
    	}
    	/** Appel de l'�criture **/
    	else if(nom_commande.equals("ecriture")){
    		System.out.println("Je suis rentre dans ecriture");
    		if(uneCommande.getIdentificateur().matches("[0-9]+[a-zA-Z]+[0-9]+")){ //Teste si c'est un cours
    			resultat = traiterEcriture(tabcours[compt_cours-1], uneCommande.getNom_attribut(), uneCommande.getValeur());
    		}
    		else{
    			resultat = traiterEcriture(tabetudiant[compt_etud-1], uneCommande.getNom_attribut(), uneCommande.getValeur());
    		}
    	}
    	/** Appel du lancement de fonction **/
    	else if(nom_commande.equals("fonction")){
    		System.out.println("Je suis rentre dans fonction");
    		if(uneCommande.getIdentificateur().matches("[0-9]+[a-zA-Z]+[0-9]+")){ //Teste si c'est un cours
    			resultat = traiterAppel(tabcours[compt_cours-1], uneCommande.getNom_fonction(), uneCommande.getTabpar(), uneCommande.getTabval());
    		}
    		else{
    			resultat = traiterAppel(tabetudiant[compt_etud-1], uneCommande.getNom_fonction(), uneCommande.getTabpar(), uneCommande.getTabval());
    		}
    	}
    	else{
    		System.out.println("Erreur dans la commande");
    	}
    	return resultat;
    }
    /**
    * traiterLecture : traite la lecture d�un attribut. Renvoies le r�sultat par le socket
    */
    public String traiterLecture(Object pointeurObjet, String attribut) {
    	String res = "";
    	if(attribut.equals("titre")){
    		res = ((Cours) pointeurObjet).getTitre(); //R�cup�re le titre
    		System.out.println(res);
    	}
    	else if(attribut.equals("prenom")){
    		res = ((Etudiant) pointeurObjet).getNom(); //R�cup�re le nom
    		System.out.println(res);
    	}
    	return res;
    }
    /**
    * traiterEcriture : traite l��criture d�un attribut. Confirmes au client que l��criture s�est faite correctement.
    */
    public String traiterEcriture(Object pointeurObjet, String attribut, String valeur) {
    	String res = "";
    	if(attribut.equals("titre")){
    		((Cours) pointeurObjet).setTitre(valeur);
    		System.out.println("La valeur titre a �t� mis � jour");
    		res = "La valeur titre a �t� mis � jour";
    	}
       	else if((attribut.equals("prenom")||attribut.equals("nom"))){
       		((Etudiant) pointeurObjet).setNom(valeur);
    		System.out.println("La valeur nom a �t� mis � jour");
    		res = "La valeur nom a �t� mis � jour";
       	}
    	return res;
    }
    /**
    * traiterCreation : traite la cr�ation d�un objet. Confirme au client que la cr�ation s�est faite correctement.
    */
    public String traiterCreation(Class classeDeLobjet, String identificateur) {
    	String res = "";
    	if(compt_etud > tailletab){
    		System.out.println("Il y a trop d'etudiants");
    		res = "Il y a trop d'etudiants";
		}
    	else if(compt_cours > tailletab){
    		System.out.println("Il y a trop de cours");
    		res = "Il y a trop de cours";
    	}
    	else{
    		if(classeDeLobjet == Etudiant.class){
        		nouvel_etudiant = new Etudiant(identificateur);//mettre en attribut puis mettre � jour
        		tabetudiant[compt_etud] = nouvel_etudiant;
        		System.out.println("objet Etudiant bien cr��");
        		nouvel_etudiant = null;
        		compt_etud++;
        		res = "L'objet Etudiant a �t� cr��";
        	}
        	else if(classeDeLobjet == Cours.class){
        		nouveau_cours = new Cours(identificateur);
        		tabcours[compt_cours] = nouveau_cours;
        		System.out.println("objet Cours bien cr��");
        		nouveau_cours = null;
        		compt_cours++;
        		res = "L'objet Cours a �t� cr��";
        	}
    	}
    	return res;
    }
    /**
    * traiterChargement : traite le chargement d�une classe. Confirmes au client que la cr�ation s�est faite correctement.
    */
    public String traiterChargement(String nomQualifie) {
    	String res = "";
    	try {
    		classe = Class.forName(nomQualifie);
    		System.out.println("Classe charg�e : " + nomQualifie);
    		res = "La classe a �t� charg�e";
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return res;
    }
    /**
    * traiterCompilation : traite la compilation d�un fichier source java. Confirme au client
	* que la compilation s�est faite correctement. Le fichier source est donn� par son chemin
	* relatif par rapport au chemin des fichiers sources.
    */
    public String traiterCompilation(String cheminRelatifFichierSource) {
    	String res = "";
    	String command = "javac " + cheminRelatifFichierSource;
    	try {
			Process pro = Runtime.getRuntime().exec(command);
			res = "La compilation a r�ussie";
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
    	return res;
    }
    /**
    * traiterAppel : traite l�appel d�une m�thode, en prenant comme argument l�objet
	* sur lequel on effectue l�appel, le nom de la fonction � appeler, un tableau de nom de 
	* types des arguments, et un tableau d�arguments pour la fonction. Le r�sultat de la 
	* fonction est renvoy� par le serveur au client (ou le message que tout s�est bien 
	* pass�)
    /**/
    public String traiterAppel(Object pointeurObjet, String nomFonction, String[] types, String[] valeurs) {

    	String res = "";
    	if(nomFonction.equals("getNote")){
    		String param = valeurs[0];
    		System.out.println(param);
    		Pattern p = Pattern.compile("ID(.(.*).)");
    		Matcher m = p.matcher(param);
    		String nometud = "";
    		float test = 0;
    		while(m.find()){
    			nometud = m.group(2);
    		}
    		int i = 0;
    		boolean flag = false;
    		while((flag != true) && (i < compt_etud)){
    			if(tabetudiant[i].getNom().equals(nometud)){
    				flag = true;
    			}
    			else{
    				i++;
    			}
    		}
    		try{
    			test = ((Cours) pointeurObjet).getNote(tabetudiant[i]);
    			System.out.println("La note a �t� r�cup�r�e : " + test);
    			res = "La note a �t� r�cup�r�e : " + test;
    		}
    		catch (Exception e){
    			System.out.println("Pas de notes");
    			res = "La note n'a pas �t� r�cup�r�e";
    		}
    	}
    	
    	else if(nomFonction.equals("attributeNote")){
    		String param = "";
    		float val = 0;
    		if(types[0].equals("uqac_8INF957.Etudiant")){
    			param = valeurs[0];
    		}
    		val = Float.parseFloat(valeurs[1]);
    		System.out.println(param);
    		System.out.println(val);
    		Pattern p = Pattern.compile("ID(.(.*).)");
    		Matcher m = p.matcher(param);
    		String nometud = "";
    		while(m.find()){
    			nometud = m.group(2);
    		}
    		int i = 0;
    		System.out.println(nometud);
    		boolean flag = false;
    		System.out.println(tabetudiant[i].getNom());
    		while((flag != true) && (i < compt_etud)){
    			if(tabetudiant[i].getNom().equals(nometud)){
    				flag = true;
    			}
    			else{
    				i++;
    			}
    		}
    		try{
    			((Cours) pointeurObjet).attributeNote(tabetudiant[i], val);
    			System.out.println("La note a �t� attribu�e : " + val + " � " + tabetudiant[i].getNom());
    			res = "La note a �t� attribu�e";
    		}
    		catch (Exception e){
    			System.out.println("Erreur de notes");
    		}
    		
    	}
    	else if(nomFonction.equals("toString")){
    		System.out.println("Voici le cours :" + ((Cours) pointeurObjet).toString());
    		res = ((Cours) pointeurObjet).toString();
    	}
       	else if(nomFonction.equals("inscrisDansCours")){
       		//((Etudiant) pointeurObjet).inscrisDansCours(valeur); //MEGA CASSEGUEULE
    		System.out.println("L'�tudiant a �t� inscrit");
    		res = "L'�tudiant a �t� inscrit";
       	}
    	else if(nomFonction.equals("getMoyenne")){
    		System.out.println("La moyenne est de : " + ((Etudiant) pointeurObjet).getMoyenne());
    		res = Float.toString(((Etudiant) pointeurObjet).getMoyenne());
    	}
    	
    	return res;
    }

	public static void main(String[] args) {
		applicationServer serv = new applicationServer(2009);
		try {
			serv.aVosOrdres();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

