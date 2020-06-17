package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mapper.CompagnyMapper;
import model.Company;
import model.Page;

/**
 * Classe qui gére la communication avec la base de données pour la table Company, l'envoie des requêtes et la réception des réponses
 * @author sonia
 *
 */
public class CompagnyDAO {
	
	 private static CompagnyDAO compagnyDAO;
	 
	 private Connection connect = ConnexionBD.getConnection();
	  
	 private static final String SELECT_ALL = "SELECT * FROM company ORDER BY id";
	   
	 private static final String SELECT_PAGE = "SELECT * FROM company ORDER BY id LIMIT ? OFFSET ? ";

	 private static final String COUNT = "SELECT COUNT(*) AS total FROM company";

	 
	 /**
	     * Instance of the singleton CompanyDAO.
	     * @return the instance of CompanyDAO
	     */
	 public static CompagnyDAO getInstance() {
	        if (compagnyDAO == null) {
	            compagnyDAO = new CompagnyDAO();
	        }
	        return compagnyDAO;
	}
	 
	 /**
	  * 
	  * @return Liste de toutes les compagnies
	  */
	 public List<Company> getAll() {
		 
	        List<Company> companyList = new ArrayList<Company>();

	        try {
	        	PreparedStatement statement = connect.prepareStatement(SELECT_ALL);
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                Company company = CompagnyMapper.convert(resultSet);
	                companyList.add(company);
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur DAO -> Lister toutes les company");
	        }
	        return companyList;
	}
	 
	 /**
	  * Retourne la liste de tous les ordinateurs dans une page spécifique
	  * @param p on cherche les informations contenues dans la page p
	  * @return
	  */
	 public List<Company> getByPage(Page p) {
		 
	        List<Company> companyList = new ArrayList<Company>();

	        try  {
	        	PreparedStatement statement = connect.prepareStatement(SELECT_PAGE);
	        	
                statement.setInt(1, p.getMaxLines());
                statement.setInt(2, p.getFirstLine());

	        	ResultSet resultSet = statement.executeQuery();
	        	while (resultSet.next()) {
	                Company company = CompagnyMapper.convert(resultSet);
	                companyList.add(company);
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur DAO -> Lister les company de la page : " + p.getNumberPage() + e.getMessage());
	        }
	        return companyList;
	}
	 
	/**
	 * Compter le nombre d'entrées dans la table Company
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
	            System.err.println("Erreur DAO -> CountAll Company");
	        }
	        return result;
	}
}
