package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;


public class CompagnyMapper {
	
	private static Company newCompagny;

	/**
	 * S'occupe de la convertion du résultat en entité
	 * @param resultSet résultat de la requête
	 * @return une entité Company correspondante
	 */
	public static Company convert(ResultSet resultSet) {
	        try {
	            newCompagny = new Company(resultSet.getLong("id"), resultSet.getString("name"));
	        } catch (SQLException e) {
	            System.err.println("Erreur -> Mapping Computer");
	        }
	return newCompagny;
	}

}
