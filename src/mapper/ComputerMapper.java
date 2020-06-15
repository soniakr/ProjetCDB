package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Computer;


public class ComputerMapper {
	
	  private static final String ID_COMPUTER = "id";
	  private static final String NAME_COMPUTER = "name";
	  private static final String INTRODUCED = "introduced";
	  private static final String DISCONTINUED = "discontinued";
	  private static final String COMPANY_ID = "company_id";
	

	public static Computer convert(ResultSet resultSet) {
		 Computer newComputer = null;
	        try {
	            newComputer = new Computer(resultSet.getLong(ID_COMPUTER), resultSet.getString(NAME_COMPUTER));
	            if(resultSet.getDate(INTRODUCED)!=null) {
	            	newComputer.setIntroduced(resultSet.getDate(INTRODUCED));
	            }
	            
	            if(resultSet.getDate(DISCONTINUED)!=null) {
	            	newComputer.setIntroduced(resultSet.getDate(DISCONTINUED));
	            }
	        
	         //   if(resultSet.getLong(COMPANY_ID)!=null) {
	            	newComputer.setIdCompagny(resultSet.getLong(COMPANY_ID));
	            
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            System.err.println("Error -> converting resultSet to Computer");
	        }
	return newComputer;
	}
}
