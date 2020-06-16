package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
	
	private static Connection connexion;
	private static boolean driverImported = false; 

	private static final String driverName = "com.mysql.cj.jdbc.Driver";
	private static final String databaseName = "computer-database-db";
	private static final String databaseUrl = "jdbc:mysql://localhost:3306/"+ databaseName +"?serverTimezone=UTC";
	
	private static final String username = "admincdb";
	private static final String password = "qwerty1234";
	
	public ConnexionBD() {}

	/**
	 * Import du pilote JDBC
	 */
	private static void init() {
		try {
			Class.forName(driverName).newInstance();
			driverImported = true;
			return;
		} catch (Exception e ) { 
			e.printStackTrace();
			System.err.println("Erreur import du driver");
		}
	}

	/**
	 * Connexion à la base de données
	 * @return Objet Connection pour réaliser la connexion et l'authentification à la base 
	 */

	public static Connection getConnection() {
		if (!driverImported)
			init();
		try {
			if (connexion == null || connexion.isClosed())
				connexion = DriverManager.getConnection(databaseUrl, username, password);
			return connexion;
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.err.println("Erreur connexion à la BD");

		}
		return null;
	}		
}
