package com.excilys.formation.cbd.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class ConnexionHikari {
	
	private static Connection connect;

	private static HikariConfig hikariConfig; 
    private static HikariDataSource dataSource;
    
	private static Logger logger = LoggerFactory.getLogger(ConnexionHikari.class);
    
    static {
    	hikariConfig=new HikariConfig("/datasource.properties");
    	dataSource= new HikariDataSource(hikariConfig);
    }

	public static Connection getConnection() {
		try {
			connect = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("Problème connexion Hikari : " + e);
		}
		return connect;
	}
/*
	public static ConnexionHikari getInstance() {
		if (instance == null) {
			instance = new ConnexionHikari();
		}
		return instance;
	}
*/
	public static Connection disconnect() {
		if (connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
				logger.error("Probleme deconnexion Hikari : " + e);
			}
		}
		return connect;
	}
}
