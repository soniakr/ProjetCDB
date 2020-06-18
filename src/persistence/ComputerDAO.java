package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import mapper.ComputerMapper;
import model.Computer;
import model.Page;

/**
 * Classe qui gére la communication avec la base de données pour la table Computer, l'envoie des requêtes et la réception des réponses
 * @author sonia
 *
 */
public class ComputerDAO {
	
	 private static ComputerDAO computerDAO;
	 
	 private Connection connect = ConnexionBD.getConnection();
	  
	 private static final String SELECT_ALL = "SELECT * FROM computer ORDER BY id";
	   
	 private static final String SELECT_BY_ID = "SELECT * FROM computer WHERE computer.id = ? ";
	 
	 private static final String SELECT_PAGE = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY computer.id LIMIT ? OFFSET ?";
	 
	 private static final String COUNT = "SELECT COUNT(*) AS total FROM computer";

	 private static final String INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";

	 private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";

	 private static final String DELETE = "DELETE FROM computer WHERE id = ?";
	
	
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
	 
	 /**
	  * Tout les ordinateurs de la table Computer
	  * @return Liste de tous le ordinateurs
	  */
	 public List<Computer> getAll() {
		 
	        List<Computer> computerList = new ArrayList<Computer>();

	        try (PreparedStatement statement = connect.prepareStatement(SELECT_ALL)) {
	            ResultSet resultSet = statement.executeQuery();
	            //ResultSet resultat = statement.executeQuery(SELECT_ALL);
	            while (resultSet.next()) {
	                Computer computer = ComputerMapper.convert(resultSet);
	                computerList.add(computer);
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur DAO -> Lister tous les ordinateurs : " + e.getMessage());
	        }
	        return computerList;
	}
	 
	 /**
	  * Trouver un ordinateur à partir de l'id
	  * @param id à rechercher dans la base
	  * @return l'ordinateur correspondant si il existe
	  */
	 public Computer findById(Long id) {
		 
	        Computer result=null;
	        if (id != null) {
	            try (PreparedStatement statement = connect.prepareStatement(SELECT_BY_ID)) {
	                statement.setLong(1, id);
	                ResultSet resultSet = statement.executeQuery();

	                while (resultSet.next()) {
	                    result = ComputerMapper.convert(resultSet);
	                }

	            } catch (SQLException e) {
	              
		            System.err.println("Erreur DAO -> Ordinateur par ID : " + e.getMessage());

	            }
	        }
	        return result;
	}
	 
	 /**
	  * Retourne la liste de tous les ordinateurs dans une page spécifique
	  * @param p on cherche les informations contenues dans la page p
	  * @return la liste d'ordinateurs
	  */
	 public List<Computer> getByPage(Page p) {
		 
	        List<Computer> computerList = new ArrayList<Computer>();

	        try  {
	        	PreparedStatement statement = connect.prepareStatement(SELECT_PAGE);
	        	
                statement.setInt(1, p.getMaxLines());
                statement.setInt(2, p.getFirstLine());

	        	ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                Computer computer = ComputerMapper.convert(resultSet);
	                computerList.add(computer);
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur DAO -> Lister les ordinateurs de la page : " + p.getNumberPage() + e.getMessage());
	        }
	        return computerList;
	}
	 
	 
	 /**
	     * Ajouter un ordinateur à la base de données
	     * @param computer l'entité à insérer dans la table
	     */
	    public void insert(Computer computer) {
	        if (computer != null) {
	            try (PreparedStatement statement = connect.prepareStatement(INSERT)) {
	                statement.setString(1, computer.getName());
	                
	                Date introducedDate = computer.getIntroduced() == null ? null : computer.getIntroduced();
	                statement.setDate(2, introducedDate);
	                
	                Date discontinuedDate = computer.getDiscontinued() == null ? null : computer.getDiscontinued();
	                statement.setDate(3, discontinuedDate);
	                
	                Long idCompany = computer.getIdCompany();
	                if(idCompany!=null) {
		                statement.setLong(4, idCompany);
	                } else {
		                statement.setNull(4, Types.BIGINT);
	                }	                
	                statement.execute();
	            } catch (SQLException e) {
	                e.printStackTrace();
	                System.err.println("Erreur insertion base de données ( Vérifier que l'ID de l'entreprise existe bien) ");
	            }	
	        }
	    }

	    /**
	     * Mise à jour d'un ordinateur de la base de données
	     * @param computer l'entité avec les modifications à apporter
	     */
	    public void update(Computer computer) {
	    	   if (computer != null) {
		            try (PreparedStatement statement = connect.prepareStatement(UPDATE)) {
		                statement.setString(1, computer.getName());
		                
		                Date introducedDate = computer.getIntroduced() == null ? null : computer.getIntroduced();
		                statement.setDate(2, introducedDate);
		                
		                Date discontinuedDate = computer.getDiscontinued() == null ? null : computer.getDiscontinued();
		                statement.setDate(3, discontinuedDate);
		                
		                statement.setLong(4, computer.getIdCompany());
		                statement.setLong(5, computer.getId());
		                statement.execute();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
	    }

	/**
	 * Supprimer un ordinateur de la base de données
	 * @param id identifiant de l'ordinateur à supprimer
	*/
	public void delete(Long id) {
	    try {
	    	PreparedStatement statement = connect.prepareStatement(DELETE);
	        statement.setLong(1, id);
	        statement.execute();
	   } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Erreur supprission de la base de données");
	   }
	}
	
	/**
	 * Compter le nombre d'entrées dans la table Computer
	 * @return le nombre total d'entrées 
	 */
	 public int countAll() {
		 
	        int result=0;
	        try {
	        	PreparedStatement statement = connect.prepareStatement(COUNT);
	            ResultSet resultSet = statement.executeQuery();
	            
	            while (resultSet.next()) {
	                result=resultSet.getInt("total");
	            }
	           System.out.println("Nombre total d'entrées dans la base : " + result);
	            
	        } catch (SQLException e) {
	            System.err.println("Erreur DAO -> CountAll Computer");
	        }
	        return result;
	}

}
