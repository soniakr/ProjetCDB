package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mapper.ComputerMapper;
import model.Computer;

public class ComputerDAO {
	
	 private static ComputerDAO computerDAO;
	 
	 private Connection connect = ConnexionBD.getConnection();
	  
	 private static final String SELECT_ALL = "SELECT * FROM computer ORDER BY id";
	    
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
	  * @return List of all compagnies 
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
	            System.out.println("Error -> listing all computers");
	        }
	        return computerList;
	}

}
