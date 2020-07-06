package com.excilys.formation.cbd.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cbd.mapper.ComputerMapper;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Page;

/**
 * Classe qui gére la communication avec la base de données pour la table Computer, l'envoie des requêtes et la réception des réponses
 * @author sonia
 *
 */
public class ComputerDAO {
	
	 private static ComputerDAO computerDAO;
	 
	 private Connection connect;
	 
	 private static Logger logger = LoggerFactory.getLogger(ConnexionBD.class);
	  
	 private static final String SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY computer.id";
	   
	 private static final String SELECT_BY_ID = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.id = ?  ";
	 
	 private static final String SELECT_BY_NAME = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY %s LIMIT ? OFFSET ? ";

	 private static final String SELECT_PAGE = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY %s LIMIT ? OFFSET ?";
	 
	 private static final String COUNT = "SELECT COUNT(*) AS total FROM computer ";
	 
	 private static final String COUNT_BY_NAME = "SELECT COUNT(*) AS total FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?";

	 private static final String INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";

	 private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";

	 private static final String DELETE = "DELETE FROM computer WHERE id = ?";
	 
	 static final String DELETE_COMPUTER_WITH_COMPANY_ID="DELETE FROM computer WHERE company_id= ? ";
	
	
	/**
	 * Instance du singleton
	 * @return
	 */
	 public static ComputerDAO getInstance() {
	        if (computerDAO == null) {
	            computerDAO = new ComputerDAO();
	        }
	        return computerDAO;
	}
	 
	 public void connectBD() {
		 	connect = ConnexionHikari.getConnection();

	 }
	 
	 /**
	  * Tout les ordinateurs de la table Computer
	  * @return Liste de tous le ordinateurs
	  */
	 public List<Computer> getAll() {
		 	connectBD();
	        List<Computer> computerList = new ArrayList<Computer>();

	        try (PreparedStatement statement = connect.prepareStatement(SELECT_ALL)) {
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                Computer computer = ComputerMapper.convert(resultSet);
	                computerList.add(computer);
	            }
	            connect.close();
	        } catch (SQLException e) {
	        	logger.error("Erreur DAO -> Lister tous les ordinateurs : " + e.getMessage());
	        }
	        return computerList;
	}
	 
	 /**
	  * Trouver un ordinateur à partir de l'id
	  * @param id à rechercher dans la base
	  * @return l'ordinateur correspondant si il existe
	  */
	 public Computer findById(Long id) {
	    	connectBD();

	        Computer result=null;
	        if (id != null) {
	            try (PreparedStatement statement = connect.prepareStatement(SELECT_BY_ID)) {
	                statement.setLong(1, id);
	                ResultSet resultSet = statement.executeQuery();

	                while (resultSet.next()) {
	                    result = ComputerMapper.convert(resultSet);
	                }
	                connect.close();

	            } catch (SQLException e) {
	              
	            	logger.error("Erreur DAO -> Ordinateur par ID : " + e.getMessage());
	            }
	        }
	        return result;
	}
	 
	 
	 /**
	  * Tout les ordinateurs qui ont un nom donné
	  * @return Liste de tous le ordinateurs
	  */
	 public List<Computer> getAllByName(Page p, String name, String orderBy) {
		 	connectBD();
	        List<Computer> computerList = new ArrayList<Computer>();
	        String request = orderBy(orderBy,SELECT_BY_NAME);

	        try (PreparedStatement statement = connect.prepareStatement(request)) {
	        	
                statement.setString(1, "%" + name + "%");
                statement.setString(2, "%" + name + "%");
                statement.setInt(3, p.getMaxLines());
                statement.setInt(4, p.getFirstLine());
                
	        	ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                Computer computer = ComputerMapper.convert(resultSet);
	                computerList.add(computer);
	            }
	            
	            connect.close();
	        } catch (SQLException e) {
	        	logger.error("Erreur DAO -> Lister tous les ordinateurs par nom : " + e.getMessage());
	        }
	        return computerList;
	}
	 
	 public String orderBy(String s, String requete) {
		 	
		 	System.out.println("la requete qui arrive : " +s);
	        String order;
	        String[] arguments = s.split("\\.");
		 	System.out.println("taille de mon tab : " + arguments.length);

	        if(arguments.length==3) {
		        System.out.println("ici");

	        	order=arguments[0]+"."+arguments[1]+ " " + arguments[2];
	        } else if (arguments.length==2){
		        System.out.println("ici2");

	        	order=arguments[0]+ " " + arguments[1];
	        } else {
		        System.out.println("ici3");

	        	order="computer.id";
	        }
	        System.out.println("----- ORDER : "+ order);

	        String res = String.format(requete,order);
	        System.out.println(res);
	        return res;
	 }
	 
	 /**
	  * Retourne la liste de tous les ordinateurs dans une page spécifique
	  * @param p on cherche les informations contenues dans la page p
	 * @param orderBy 
	  * @return la liste d'ordinateurs
	  */
	 public List<Computer> getByPage(Page p, String orderBy) {
		 
	        List<Computer> computerList = new ArrayList<Computer>();
	    	connectBD();
	        String res = orderBy(orderBy,SELECT_PAGE);

	        try  {
	        	PreparedStatement statement = connect.prepareStatement(res);

                statement.setInt(1, p.getMaxLines());
                statement.setInt(2, p.getFirstLine());
                
	        	ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                Computer computer = ComputerMapper.convert(resultSet);
	                computerList.add(computer);
	                System.out.println(computer.toString());
	            }
	            connect.close();
	        } catch (SQLException e) {
	        	logger.error("Erreur DAO -> Lister les ordinateurs de la page : " + p.getNumberPage() + e.getMessage());
	        }
	        return computerList;
	}
	 
	 
	 /**
	     * Ajouter un ordinateur à la base de données
	     * @param computer l'entité à insérer dans la table
	     */
	    public void insert(Computer computer) {
	    	
	        if (computer != null) {
		    	connectBD();
	            try (PreparedStatement statement = connect.prepareStatement(INSERT)) {
	                statement.setString(1, computer.getName());
	                Date introducedDate=null;
	                if(computer.getIntroduced()!=null) {
	                	introducedDate = Date.valueOf(computer.getIntroduced());
	                }
	                statement.setDate(2, introducedDate);
	                
	                Date discontinuedDate=null;
	                if(computer.getDiscontinued()!=null) {
	                	discontinuedDate = Date.valueOf(computer.getDiscontinued());
	                }	                
	                statement.setDate(3, discontinuedDate);
	                
	                Long idCompany = computer.getIdCompany();
	                if(idCompany!=null) {
		                statement.setLong(4, idCompany);
	                } else {
		                statement.setNull(4, Types.BIGINT);
	                }	                
	                statement.execute();
	                connect.close();
	            } catch (SQLException e) {
	                logger.error("Erreur insertion base de données (Vérifier que l'ID de l'entreprise existe bien) ");
	            }	
	        }
	    }

	    /**
	     * Mise à jour d'un ordinateur de la base de données
	     * @param computer l'entité avec les modifications à apporter
	     */
	    public void update(Computer computer) {
	    	   if (computer != null) {
	   	    		connectBD();
		            try (PreparedStatement statement = connect.prepareStatement(UPDATE)) {
		                statement.setString(1, computer.getName());
		                
		                Date introducedDate = Date.valueOf(computer.getIntroduced()) == null ? null : Date.valueOf(computer.getIntroduced());
		                statement.setDate(2, introducedDate);
		                
		                Date discontinuedDate = Date.valueOf(computer.getDiscontinued()) == null ? null : Date.valueOf(computer.getDiscontinued());
		                statement.setDate(3, discontinuedDate);
		                
		                statement.setLong(4, computer.getIdCompany());
		                statement.setLong(5, computer.getId());
		                statement.execute();
		                connect.close();
		            } catch (SQLException e) {
		                logger.error("ErreurDAO : update ");
		            }
		        }
	    }

	/**
	 * Supprimer un ordinateur de la base de données
	 * @param id identifiant de l'ordinateur à supprimer
	*/
	public void delete(Long id) {
    	connectBD();
	    try (PreparedStatement statement = connect.prepareStatement(DELETE)) {
	        statement.setLong(1, id);
	        statement.execute();
	        connect.close();
	   } catch (SQLException e) {
	        logger.error("Erreur suppression de la base de données");
	   }
	}
	
	/**
	 * Compter le nombre d'entrées dans la table Computer
	 * @return le nombre total d'entrées 
	 */
	 public int countAll(String s) {
		 connectBD();
	        int result=0;
	        PreparedStatement statement;
	        try {
	        	if(s==null) {
		        	statement = connect.prepareStatement(COUNT);
	        	} else {
		        	statement = connect.prepareStatement(COUNT_BY_NAME);
	                statement.setString(1, "%" + s + "%");
	                statement.setString(2, "%" + s + "%");

	        	}
	            ResultSet resultSet = statement.executeQuery();
	            
	            while (resultSet.next()) {
	                result=resultSet.getInt("total");
	            }
	           logger.info("Nombre total d'entrées dans la base : " + result);
	            
	           connect.close();
	        } catch (SQLException e) {
	        	logger.error("Erreur DAO -> CountAll Computer");
	        }
        	System.out.println("res : " + result);

	        return result;
	}

}
