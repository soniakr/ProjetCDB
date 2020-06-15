package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;

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
