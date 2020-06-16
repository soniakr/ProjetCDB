package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;

/**
 * S'occupe de la convertion 
 * @param resultSet résultat de la requête
 * @return un objet Computer
 */
public class CompagnyMapper {

	public static Company convert(ResultSet resultSet) {
		 Company newCompagny = null;
	        try {
	            newCompagny = new Company(resultSet.getLong("id"), resultSet.getString("name"));
	        } catch (SQLException e) {
	            System.err.println("Error -> convertion resultSet to computer");
	        }
	return newCompagny;
	}

}
