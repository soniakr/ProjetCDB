package com.excilys.formation.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Page;
import com.excilys.formation.cbd.persistence.mapper.CompanyMapper;

/**
 * Classe qui gére la communication avec la base de données pour la table Company, l'envoie des requêtes et la réception des réponses
 * @author sonia
 *
 */

@Repository
public class CompanyDAO {
	
	 
	 @Autowired
	 private JdbcTemplate jdbcTemplate; 
	 
	 private Connection connect;
	  
	// private static final String SELECT_ALL = "SELECT company.id, company.name FROM company ORDER BY id";
     private static final String SELECT_ALL = "FROM Company"; 
	 
     private static final String SELECT_PAGE = "SELECT company.id, company.name FROM company ORDER BY id LIMIT ? OFFSET ? ";
	 
	 private static final String SELECT_BY_ID = "FROM Company company WHERE company.id = :id";

	 private static final String COUNT = "SELECT COUNT(*) AS total FROM company";
	 
	 private static final String DELETE_COMPANY="DELETE FROM company WHERE id= ?";
	 
	 private final static String GET_COMPANY_COMPUTERS = "select id from computer where company_id = ?";
	
	 private final static String DELETE_COMPUTERS = "DELETE FROM computer WHERE id IN (";
	 
	 private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	 
	 private SessionFactory sessionFactory;
	 
	 @Autowired
	 public CompanyDAO (SessionFactory sessionFactory) {
	        this.sessionFactory = sessionFactory;
	 }
	    
	 /**
	  * @return Liste de toutes les compagnies
	  */
	 public List<Company> getAll() {
		 	
		 List<Company> companies = new ArrayList<Company>();
		 try (Session session = sessionFactory.openSession()) {
	            companies = session.createQuery(SELECT_ALL, Company.class).list();
	        } catch (HibernateException e) {
	            logger.error("error when getting all companies", e);
	        }
			return companies;	
	}
	 
	 
	 public Company findById(Long id) {		 
		 Company company = new Company();
		 if (id != null) {
	            try (Session session = sessionFactory.openSession()) {
	                Query<Company> query = session.createQuery(SELECT_BY_ID, Company.class);
	                query.setParameter("id", id);
	                company = query.uniqueResult();
	            } catch (HibernateException e) {
	                logger.error("error when finding computer by id", e);
	            }
			}
			return company;
	}
	 
	 /**
	  * Retourne la liste de tous les ordinateurs dans une page spécifique
	  * @param p on cherche les informations contenues dans la page p
	  * @return
	  */
	 public List<Company> getByPage(Page p) {
	     
		 List<Company> companiesList = new ArrayList<Company>();

			try (Connection connect = ConnexionHikari.getConnection()){
				
				companiesList = jdbcTemplate.query(SELECT_PAGE, new CompanyMapper(), p.getMaxLines(), p.getFirstLine());
		
			} catch (SQLException e) {
				logger.error("Erreur DAO -> Lister les ordinateurs de la page : " + p.getNumberPage() + e.getMessage());
			}
			return companiesList;
	}
	 
	/**
	 * Compter le nombre d'entrées dans la table Company
	 * @return le nombre total d'entrées 
	 */
	 public int countAll() {
		 int total = 0;
			try (Connection connect = ConnexionHikari.getConnection()){
				
				total = jdbcTemplate.queryForObject(COUNT, Integer.class);
			
			 } catch (SQLException e) {
		            logger.error("Error when counting the number of computers",e);
		     }
	        return total; 
	}
	 
	 /**
	  * Suppression d'une company, on commence par supprimer tous les ordinateurs appartenants à la company
	  * @param id identifiant de la company à supprimer
	  */
	 
	 public void deleteCompany(Long id) {
		 connect = ConnexionHikari.getConnection();
		 try (PreparedStatement computers = connect.prepareStatement(GET_COMPANY_COMPUTERS)){
				connect.setAutoCommit(false);
				computers.setLong(1, id);
	            ResultSet result= computers.executeQuery();
	            
	            String id_computers = result.next() ? String.valueOf(result.getLong("id"))  :"";
				while(result.next()) {
					id_computers+= "," + result.getLong("id");
				}
				
				PreparedStatement deleteComputers = connect.prepareStatement(DELETE_COMPUTERS+id_computers+");");
				deleteComputers.executeUpdate();

				PreparedStatement deleteCompany = connect.prepareStatement(DELETE_COMPANY);
				deleteCompany.setLong(1, id);
				deleteCompany.executeUpdate();				
				
				connect.commit();			
	          
	        } catch (SQLException e) {
	        	logger.error("Error DAO -> Delete a company ",e);

	        }
	}
}
