package ui;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import model.Company;
import model.Computer;
import service.CompagnyService;
import service.ComputerService;

public class CLI {
	
	private static Scanner sc;
	
	 private static ComputerService computerService; 
	 private static CompagnyService compagnyService;
	 
	/**
	 * Affiche la liste de tous les ordinateurs dans la base
	 */
	
	public static void listAllComputers() {
		
		 // Page newPage = new Page();
	       
	        List<Computer> computers = computerService.getAll();
	        for(Computer c : computers) {
	        	System.out.println(c.toString());
	        }
	}
	
	/**
	 * Affiche la liste de toutes les entreprises dans la base
	 */
	public static void listAllCompagnies() {
		
        List<Company> allCompagnies = compagnyService.getAll();
        int numberOfCompanies = allCompagnies.size();
        for(Company c : allCompagnies) {
        	System.out.println(c.toString());
        }
	}
	
	/**
	 * Affiche les détails à propos d'un ordinateur
	 */
	public static void detailsComputer() {
		
		Long computerId=getId();
		if(computerId!=null) {
			Computer c = computerService.getById(computerId);
			if(c==null) {
				System.err.println("Aucun ordinateur avec cet ID");
			} else {
				System.out.println(c.toString());
			}
		}else {
			System.err.println("Erreur récuperation de l'ID");
		}
		
	}
	
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
	
	public static Computer getInfos() {
		
		Date dateCont=null;
		Date dateDisc=null;
		Long idComp=null;
		String answer;
		Computer newComp=null;

		System.out.println("Entrez le nom de l'ordinateur : ");
		String name=sc.nextLine();
		
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
		return newComp;	
	}
	
	/**
	 * Mise à jour des informations sur un ordinateur
	 */
	public static void updateComputer() {
		Computer newComp=getInfos();
		if(newComp !=null) {
			computerService.updateComputer(newComp);
		}
	}
	
	
	/**
	 * Supprimer un ordinateur de la base
	 */
	public static void deleteComputer() {
		
	}
	
	/**
	 * Affiche les options disponibles pour le client
	 */
	public static void start() {

		System.out.println("Entrez votre commande: ");
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
					stop=false;
					break;
					
				default:
					System.out.println("Désolé je n'ai pas compris.");
					break;
			}
			
		}
	}

}
