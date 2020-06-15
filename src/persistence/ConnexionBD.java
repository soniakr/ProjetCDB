package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
	
	private static Connection connexion;
	private static boolean driverImported = false; 

	private static final String driverName = "com.mysql.cj.jdbc.Driver";
	private static final String name = "computer-database-db";
	private static final String url = "jdbc:mysql://localhost:3306/"+ name +"?serverTimezone=UTC";
	
	private static final String username = "admincdb";
	private static final String password = "qwerty1234";
	
	public ConnexionBD() {}

	/**
	 * Import du driver à faire que la 1ere fois
	 */
	private static void init() {
		try {
			Class.forName(driverName).newInstance();
			driverImported = true;
			return;
		} catch (Exception e ) { 
			e.printStackTrace();
			System.err.println("Erreur import driver : " + e.getMessage());
		}
	}

	/**
	 * Connexion à la BD
	 * @return Connection 
	 */

	public static Connection getConnection() {
		if (!driverImported)
			init();
		try {
			if (connexion == null || connexion.isClosed())
				connexion = DriverManager.getConnection(url, username, password);
			return connexion;
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}
		return null;
	}		
}
