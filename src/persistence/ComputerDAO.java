package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import mapper.ComputerMapper;
import model.Computer;

public class ComputerDAO {
	
	 private static ComputerDAO computerDAO;
	 
	 private Connection connect = ConnexionBD.getConnection();
	  
	 private static final String SELECT_ALL = "SELECT * FROM computer ORDER BY id";
	   
	 private static final String SELECT_BY_ID = "SELECT * FROM computer WHERE computer.id = ? ";
	 
	 private static final String INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";

	 private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";

	 private static final String DELETE = "DELETE FROM computer WHERE id = ?";
	
	
	 /**
	     * Instance of the singleton CompanyDAO.
	     * @return the instance of CompanyDAO
	     */
	 public static ComputerDAO getInstance() {
	        if (computerDAO == null) {
	            computerDAO = new ComputerDAO();
	        }
	        return computerDAO;
	}
	 
	 /**
	  * 
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
	     * Ajouter un ordinateur à la base de données
	     * @param computer
	     */
	    public void insert(Computer computer) {
	        if (computer != null) {
	            try (PreparedStatement statement = connect.prepareStatement(INSERT)) {
	                statement.setString(1, computer.getName());
	                
	                Date introducedDate = computer.getIntroduced() == null ? null : computer.getIntroduced();
	                statement.setDate(2, introducedDate);
	                
	                Date discontinuedDate = computer.getDiscontinued() == null ? null : computer.getDiscontinued();
	                statement.setDate(3, discontinuedDate);
	                
	                Long idCompany = computer.getIdCompany() == null ? null : computer.getIdCompany();
	                statement.setLong(4, idCompany); // TO DO : Probleme quand on insere null en id company
	                
	                //statement.setNull(4, Types.BIGINT);
	                
	                statement.execute();
	            } catch (SQLException e) {
	                e.printStackTrace();
	                System.err.println("Erreur insertion base de données ( Vérifier que l'ID de l'entreprise existe bien) ");
	            }	
	        }
	    }

	    /**
	     * Mise à jour d'un ordinateur de la base de données
	     * @param computer
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
		                
		                statement.execute();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
	    }

	    /**
	     * Supprimer un ordinateur de la base de données
	     * @param id
	     */
	    public void delete(Long id) {
	        try (PreparedStatement statement = connect.prepareStatement(DELETE)) {
	            statement.setLong(1, id);
	            statement.execute();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}

}
