package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Compagny;

public class CompagnyMapper {

	public static Compagny convert(ResultSet resultSet) {
		 Compagny newCompagny = null;
	        try {
	            newCompagny = new Compagny(resultSet.getLong("id"), resultSet.getString("name"));
	        } catch (SQLException e) {
	            System.err.println("Error -> converting resultSet to Compagny");
	        }
	return newCompagny;
	}

}
