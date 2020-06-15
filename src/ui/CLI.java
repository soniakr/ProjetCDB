package ui;

import java.util.List;
import java.util.Scanner;

import model.Compagny;
import service.CompagnyService;
import service.ComputerService;

public class CLI {
	
	Scanner sc;
	
	 private static ComputerService computerService; 
	 private static CompagnyService compagnyService;
	
	public static void listAllComputers() {
		
		 // Page newPage = new Page();
	       
	        List<Compagny> allCompagnies = compagnyService.getAll();
	        int numberOfCompanies = allCompagnies.size();
	        for(Compagny c : allCompagnies) {
	        	System.out.println(c.toString());
	        }
	}
	
	public void listAllCompagnies() {
		
	}
	
	public void detailsComputer() {
		
	}
	
	public void createComputer() {
		
	}
	
	public void updateCompter() {
		
	}
	
	public void deleteComputer() {
		
	}
	
	
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
	//	computerService = ComputerService.getInstance();
		
		select_option();

	}
	
	public static void select_option() {
		
		boolean stop=true;
		Scanner sc=new Scanner(System.in);
		String answer;
		
		while(stop) {
			answer=sc.nextLine();
			switch(answer) {
				case ("0"):
					System.out.println("Liste des ordinateurs :");
					break;
					
				case ("1"):
					System.out.println("Liste des entreprises :");
					listAllComputers();
					break;
					
				case ("2"):
					System.out.println("Détails d'un ordinateur :");
					break;
					
				case ("3"):
					System.out.println(" Création ordinateur : :");
					break;
					
				case ("4"):
					System.out.println(" Mise à jour d'un ordinateur :");
					break;
					
				case ("5"):
					System.out.println("Supprimer un ordinateur :");
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
