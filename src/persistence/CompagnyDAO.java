package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mapper.CompagnyMapper;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;
import model.Page;

public class CompagnyDAO {
	
	 private static CompagnyDAO compagnyDAO;
	 
	 private Connection connect = ConnexionBD.getConnection();
	  
	 private static final String SELECT_ALL = "SELECT * FROM company ORDER BY id";
	   
	 private static final String SELECT_BY_ID = "SELECT * FROM company ORDER BY id LIMIT ? OFFSET ? ";

	 
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
	  * @return List of all compagnies 
	  */
	 public List<Company> getAll() {
		 
	        List<Company> companyList = new ArrayList<Company>();

	        try {
	        	PreparedStatement statement = connect.prepareStatement(SELECT_ALL);
	            ResultSet resultSet = statement.executeQuery();
	            //ResultSet resultat = statement.executeQuery(SELECT_ALL);
	            while (resultSet.next()) {
	                Company company = CompagnyMapper.convert(resultSet);
	                companyList.add(company);
	            }
	        } catch (SQLException e) {
	            System.out.println("Error -> listing all companies");
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
	            System.err.println("Erreur DAO -> Lister les ordinateurs de la page : " + p.getCurrentPage() + e.getMessage());
	        }
	        return computerList;
	}
}
