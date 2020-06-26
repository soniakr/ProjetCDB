package com.excilys.formation.cbd.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Classe qui s'occupe de la connexion à la base de données
 * @author sonia
 *
 */
public class ConnexionBD {
	
	private static Connection connexion;
	private static boolean driverImported = false; 
	
	private static Logger logger = LoggerFactory.getLogger(ConnexionBD.class);

	private static String driverName;
	private static String databaseName;
	private static String databaseUrl;
	private static String extension;
	private static String username;
	private static String password;
	
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
			 logger.error("error when connecting to datasource", e);
		}
	}
	
	/**
	 * Récupérer les Properties à partir du fichier config.properties
	 */
	
	public static void loadProperties() {

	        try {
	           InputStream input = ConnexionBD.class.getClassLoader().getResourceAsStream("config.properties");
	           Properties prop = new Properties();
	           prop.load(input);

	           driverName = prop.getProperty("driver");
	           databaseName=prop.getProperty("databaseName");
	           databaseUrl=prop.getProperty("databaseUrl");
	           extension=prop.getProperty("extension");
	           username = prop.getProperty("username");
	           password = prop.getProperty("password");
	           
	           databaseUrl=databaseUrl+databaseName+extension;	
	           input.close();

	        } catch (IOException e) {
	            e.printStackTrace();
				 logger.error("error loading properties", e);

	        }
	}

	/**
	 * Connexion à la base de données
	 * @return Objet Connection pour réaliser la connexion et l'authentification à la base 
	 */

	public static Connection getConnection() {
		loadProperties();
		if (!driverImported)
			init();
		try {
			if (connexion == null || connexion.isClosed())
				connexion = DriverManager.getConnection(databaseUrl, username, password);
			return connexion;
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			logger.error("Erreur connexion à la BD");

		}
		return null;
	}		
}
