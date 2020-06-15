package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mapper.CompagnyMapper;
import model.Company;

public class CompagnyDAO {
	
	 private static CompagnyDAO compagnyDAO;
	 
	 private Connection connect = ConnexionBD.getConnection();
	  
	 private static final String SELECT_ALL = "SELECT id, name FROM company ORDER BY id";
	    
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

	        try (PreparedStatement statement = connect.prepareStatement(SELECT_ALL)) {
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
}
