package ui;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import model.Company;
import model.Computer;
import model.Page;
import service.CompagnyService;
import service.ComputerService;

/**
 * Classe qui gére la communication avec le client
 * @author sonia
 *
 */
public class CLI {
	
	 private static Scanner sc;
	
	 private static ComputerService computerService; 
	 private static CompagnyService compagnyService;
	 
	 
	/**
	 * Affiche la liste de tous les ordinateurs de la base de données
	 */	
	public static void listAllComputers() {      

		Page newPage = new Page();
		boolean stop=false;
		int nbComputer = computerService.countAll();
        
        while(!stop) {
            List<Company> allComputers = compagnyService.getByPage(newPage);
            allComputers.forEach(cp -> System.out.println(cp.toString()));
            optionsPages(newPage,nbComputer,stop);
        }
	}
	
	/**
	 * Affiche la liste de toutes les entreprises dans la base
	 */
	public static void listAllCompagnies() {
		
		Page newPage = new Page();
		boolean stop=false;
		int nbCompanies = compagnyService.countAll();
        
        while(!stop) {
            List<Company> allCompanies = compagnyService.getByPage(newPage);
            allCompanies.forEach(cp -> System.out.println(cp.toString()));
            optionsPages(newPage,nbCompanies,stop);           
        } 
	}
	
	/**
	 * 
	 * @param newPage
	 * @param nbTotal
	 * @param stop
	 */
	public static void optionsPages(Page newPage, int nbTotal, boolean stop) {
		
		 System.out.println( "Page " + newPage.getNumberPage() + "/" + newPage.getTotalPages(nbTotal));
         System.out.println("Entrez 's' pour Suivant - 'p' pour Précédent - 'page ' et le numero de la page et 'q' pour quitter");

         String input = sc.nextLine();
         
         switch (input.toLowerCase()) {
	            case "p":
	                newPage.getPreviousPage();
	                break;
	            case "s":
	                newPage.getNextPage(nbTotal);
	                break;
	            case "q":
	                stop = true;
	                break;
	            default : 
	            	if(input.toLowerCase().startsWith("page ")) {
	                	String num=input.split(" ")[1];
	                	
	                	int idPage;
						try {
							idPage = Integer.parseInt(num);
							System.out.println("Vous avez demandé la page : " + idPage);
		                	if(idPage>0 && idPage<=newPage.getNbTotalPages()) {
		                		newPage.setNumberPage(idPage);
		                		newPage.calculFirstLine();
		                	} else {
		                		System.out.println("Cette page n'existe pas");
		                		break;
		                	}
						} catch (NumberFormatException e) {
							System.err.println("Vous n'avez pas tapé un id de page valide");
						}
	                	
	                } else {
	                	System.out.println("Commande inconnue.");
		            	break;
	                }
	            
         }
	}
	
	/**
	 * Affiche les détails à propos d'un ordinateur
	 */
	public static void detailsComputer() {
		
		Long computerId=getId();
		if(computerId!=null){
			checkId(computerId);
		} else {
			System.err.println("Erreur récupération de l'ID");
		}
		
	}
	
	/**
	 * Recupère un entier tapé par l'utilisateur 
	 * @return un entier correspondant à une ID
	 */
	public static Long getId() {
		
			System.out.println("Entrez l'identifiant de l'ordinateur svp : ");
			String answer;
			Long computerId=null;
			boolean idOk=false;
			while(!idOk) {
				answer=sc.nextLine();
				try {
					computerId=Long.parseLong(answer);
					idOk=true;
					System.out.println("Vous avez selectionné l'ID : " + computerId);
				} catch (Exception e) {
					System.err.println("Veuillez entrer un nombre svp " + e.getMessage());
				}
			}
			return computerId;
		}
				
	/**
	 * Création d'une nouvelle entité Computer à insérer dans la base
	 */
	public static void createComputer() {
			Computer newComp=getInfos();
			if(newComp !=null) {
				computerService.addComputer(newComp);
			}
	}
	
	/**
	 * Recupère les informations concernant un ordinateur : nom, dates, id de la compagnie
	 * @return un objet Computer correspondant 
	 */
	public static Computer getInfos() {
		
		Date dateCont=null;
		Date dateDisc=null;
		Long idComp=null;
		Computer newComp=null;
		
		String answer;
		System.out.println("Entrez le nom de l'ordinateur : ");
		String name=sc.nextLine();
		
		if(name.length()==0) {
			System.err.print("Le nom ne peut pas etre vide");
		} else {
		
			try {
				System.out.println("Entrez la date Introduced au format YYYY-MM-DD (<Entrer> pour ignorer) : ");
				answer=sc.nextLine();
				if(answer.length()>0) {
					dateCont=Date.valueOf(answer);
				} 
				
				System.out.println("Entrez la date Discontinued au format YYYY-MM-DD (<Entrer> pour ignorer) : ");
				answer=sc.nextLine();
				if(answer.length()>0) {
					dateDisc=Date.valueOf(answer);
				} 		
				System.out.println("Entrez l'ID de la compagnie (<Entrer> pour ignorer) : ");
				
				answer=sc.nextLine();
				if(answer.length()>0) {
					idComp=Long.parseLong(answer);
		
				} 	
				
				newComp=new Computer(name,dateCont, dateDisc, idComp);
		
				System.out.println("Nouvel ordinateur : " + newComp.toString());
				
			
			} catch(Exception e) {
				//e.printStackTrace();
				System.err.println("Erreur de format ");
			}
		}
		return newComp;	
	}
	
	/**
	 * Vérifie que l'entier en paramètre correspond bien à un ordinateur dans la base de données
	 * @param idComp l'id à chercher
	 * @return true si l'id existe dans la base, false sinon
	 */
	public static boolean checkId(Long idComp) {
		boolean exists=false;
		if(idComp!=null) {
			Computer c = computerService.getById(idComp);
			if(c==null) {
				System.err.println("Aucun ordinateur avec cet ID");
			} else {
				exists=true;
				System.out.println("Cet ID correspond à l'ordinateur suivant : \n" + c.toString());
			}
		}else {
			System.err.println("Erreur récuperation de l'ID");
		}
		return exists;
	}
	
	/**
	 * Mise à jour des informations sur un ordinateur
	 */
	public static void updateComputer() {
		Long idComp=getId();

		if(checkId(idComp)) {
			Computer newComp=getInfos();
			if(newComp!=null) {
				newComp.setId(idComp);
				computerService.updateComputer(newComp);
			}
		}
	}
	
	
	/**
	 * Supprimer un ordinateur de la base
	 */
	public static void deleteComputer() {
		Long idComp=getId();

		if(checkId(idComp)) {
			computerService.deleteComputer(idComp);
			System.out.println("Suppression réussie.");
		}
	}
	
	/**
	 * Affiche les options disponibles pour le client
	 */
	public static void start() {

		System.out.println("Entrez votre commande : ");
		System.out.println("0 - Liste des ordinateurs");
		System.out.println("1 - Liste des entreprises");
		System.out.println("2 - Détails d'un ordinateur");
		System.out.println("3 - Création d'un ordinateur");
		System.out.println("4 - Mise à jour d'un ordinateur");
		System.out.println("5 - Suppression d'un ordinateur");
		System.out.println("6 - Quitter ");
		
		compagnyService = CompagnyService.getInstance();
		computerService = ComputerService.getInstance();
		
		select_option();

	}
	
	/**
	 * Traiter les entrées du client pour y répondre
	 */
	public static void select_option() {
		
		boolean stop=true;
		sc=new Scanner(System.in);
		String answer;
		
		while(stop) {
			answer=sc.nextLine();
			switch(answer) {
				case ("0"):
					System.out.println("Liste des ordinateurs :");
					listAllComputers();
					break;
					
				case ("1"):
					System.out.println("Liste des entreprises :");
					listAllCompagnies();
					break;
					
				case ("2"):
					System.out.println("Détails d'un ordinateur :");
				detailsComputer();
					break;
					
				case ("3"):
					System.out.println(" Création ordinateur :");
					createComputer();
					break;
					
				case ("4"):
					System.out.println(" Mise à jour d'un ordinateur :");
					updateComputer();
					break;
					
				case ("5"):
					System.out.println("Supprimer un ordinateur :");
					deleteComputer();
					break;
					
				case ("6"):
					System.out.println("Au revoir !");
					stop=false; //TO DO close connexion
					break;
					
				default:
					System.out.println("Désolé je n'ai pas compris.");
					break;
			}
			
		}
	}

}
