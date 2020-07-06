package com.excilys.formation.cbd.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Page;
import com.excilys.formation.cbd.service.CompanyService;
import com.excilys.formation.cbd.service.ComputerService;
import com.excilys.formation.cbd.model.Computer.ComputerBuilder;


/**
 * Classe qui gére la communication avec le client
 * @author sonia
 *
 */
public class CLI {
		
	 private static Scanner sc;
	 private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 private static ComputerService computerService; 
	 private static CompanyService companyService;
	 
	 private static Logger logger = LoggerFactory.getLogger(CLI.class);
	 
	/**
	 * Affiche la liste de tous les ordinateurs de la base de données
	 */	
	public void listAllComputers() {      

		Page newPage = new Page();
		boolean stop=false;
		String name=null;
		int nbComputer = computerService.countAll(name);
        
        while(!stop) {
            List<Computer> allComputers = computerService.getByPage(newPage, name);
            allComputers.forEach(c -> System.out.println(c.toString()));
            stop=optionsPages(newPage,nbComputer);
        }
	}
	
	/**
	 * Affiche la liste de toutes les entreprises dans la base
	 */
	public void listAllCompagnies() {
		
		Page newPage = new Page();
		boolean stop=false;
		int nbCompanies = companyService.countAll();
        
        while(!stop) {
            List<Company> allCompanies = companyService.getByPage(newPage);
            allCompanies.forEach(cp -> System.out.println(cp.toString()));
            stop=optionsPages(newPage,nbCompanies);           
        } 
	}
	
	/**
	 * 
	 * @param newPage
	 * @param nbTotal
	 * @param stop
	 */
	public boolean optionsPages(Page newPage, int nbTotal) {
		
		boolean stop=false;
		
		 System.out.println( "Page " + newPage.getNumberPage() + "/" + newPage.getTotalPages(nbTotal));
         System.out.println("Entrez 's' pour Suivant - 'p' pour Précédent - 'page ' et le numero de la page et 'q' pour quitter");

         String input = sc.nextLine();
         
         switch (input.toLowerCase()) {
	            case "p":
	                newPage.getPreviousPage();
	                break;
	            case "s":
	                newPage.getNextPage();
	                break;
	            case "q":
	                stop = true;
	                return stop;
	               
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
		                		logger.error("Cette page n'existe pas");
		                		break;
		                	}
						} catch (NumberFormatException e) {
							logger.error("Vous n'avez pas tapé un id de page valide");
						}
	                	
	                } else {
	                	logger.error("Commande inconnue.");
		            	break;
	                }
	            
         }
         return stop;
	}
	
	/**
	 * Affiche les détails à propos d'un ordinateur
	 */
	public void detailsComputer() {
		
		Long computerId=getId();
		if(computerId!=null){
			checkId(computerId);
		} else {
			logger.error("Erreur récupération de l'ID");
		}
		
	}
	
	/**
	 * Recupère un entier tapé par l'utilisateur 
	 * @return un entier correspondant à une ID
	 */
	public Long getId() {
		
			System.out.println("Entrez l'identifiant svp : ");
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
					logger.error("Veuillez entrer un nombre svp " + e.getMessage());
				}
			}
			return computerId;
		}
				
	/**
	 * Création d'une nouvelle entité Computer à insérer dans la base
	 */
	public void createComputer() {
			Computer newComp=getInfos();
			if(newComp !=null) {
				computerService.addComputer(newComp);
			}
			logger.info("Création réussie.");
	}
	
	/**
	 * Recupère les informations concernant un ordinateur : nom, dates, id de la compagnie
	 * @return un objet Computer correspondant 
	 */
	public Computer getInfos() {
		
		LocalDate dateIntroduced=null;
		LocalDate dateDisc=null;
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
						dateIntroduced=LocalDate.parse(answer,formatter);
					} 
					
					System.out.println("Entrez la date Discontinued au format YYYY-MM-DD (<Entrer> pour ignorer) : ");
					answer=sc.nextLine();
					if(answer.length()>0) {
						dateDisc=LocalDate.parse(answer,formatter);
					} 
					
					if(dateDisc!=null && dateIntroduced!=null && dateIntroduced.isAfter(dateDisc)) {
						logger.error("Introduced doit être avant Discontinued");
						dateDisc=null;
						dateIntroduced=null;
						throw new DateTimeParseException(name, name, 0);
					}
					System.out.println("Entrez l'ID de la compagnie (<Entrer> pour ignorer) : ");
					
					answer=sc.nextLine();
					if(answer.length()>0) {
						idComp=Long.parseLong(answer);

					} 
					newComp = new ComputerBuilder(name)
										.initializeWithIntroducedDate(dateIntroduced)
										.initializeWithDiscontinuedDate(dateDisc)
										.initializeWithCompanyID(idComp)
			.build(); 
				} catch (NumberFormatException | DateTimeParseException e) {  
					logger.error("Erreur de format : ", e);
				}
				
			
			
		}
		return newComp;	
	}
	
	/**
	 * Vérifie que l'entier en paramètre correspond bien à un ordinateur dans la base de données
	 * @param idComp l'id à chercher
	 * @return true si l'id existe dans la base, false sinon
	 */
	public boolean checkId(Long idComp) {
		boolean exists=false;
		if(idComp!=null) {
			Computer c = computerService.getById(idComp);
			if(c==null) {
				logger.error("Aucun ordinateur avec cet ID");
			} else {
				exists=true;
				logger.info("Cet ID correspond à l'ordinateur suivant : \n" + c.toString());
			}
		}else {
			logger.error("Erreur récuperation de l'ID");
		}
		return exists;
	}
	
	/**
	 * Mise à jour des informations sur un ordinateur
	 */
	public void updateComputer() {
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
	public void deleteComputer() {
		Long idComp=getId();

		if(checkId(idComp)) {
			computerService.deleteComputer(idComp);
			logger.info("Suppression ordinateur réussie.");
		}
	}
	
	/**
	 * Supprimer une entreprise de la base 
	 */
	private void deleteCompany() {
		Long idCompany=getId();

		if(checkIdCompany(idCompany)) {
			companyService.deleteCompany(idCompany);
			logger.info("Suppression company réussie.");
		}
	}

	/**
	 * Verifier que la company avec l'id demandé existe bien
	 * @param idComp
	 * @return true si la company existe, false sinon
	 */
	private boolean checkIdCompany(Long idComp) {
		boolean exists=false;
		if(idComp!=null) {
			Company c = companyService.getById(idComp);
			if(c==null) {
				logger.error("Aucune compagnie avec cet ID");
			} else {
				exists=true;
				logger.info("Cet ID correspond à la compagnie suivant : \n" + c.toString());
			}
		}else {
			logger.error("Erreur récuperation de l'ID");
		}
		return exists;
	}

	/**
	 * Affiche les options disponibles pour le client
	 */
	public void start() {

		System.out.println("Entrez votre commande : ");
		System.out.println("0 - Liste des ordinateurs");
		System.out.println("1 - Liste des entreprises");
		System.out.println("2 - Détails d'un ordinateur");
		System.out.println("3 - Création d'un ordinateur");
		System.out.println("4 - Mise à jour d'un ordinateur");
		System.out.println("5 - Suppression d'un ordinateur");
		System.out.println("6 - Suppression d'une entreprise");
		System.out.println("7 - Quitter ");
		
		companyService = CompanyService.getInstance();
		computerService = ComputerService.getInstance();
		
		select_option();

	}
	
	/**
	 * Traiter les entrées du client pour y répondre
	 */
	public void select_option() {
		
		boolean stop=true;
		sc=new Scanner(System.in);
		String answer;
		logger.info("New client on UI");
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
					System.out.println("Supprimer une entreprise :");
					deleteCompany();
					break;
					
				case ("7"):
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
