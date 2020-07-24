package com.excilys.java.cdb.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Page;

/**
 * Classe qui gére la communication avec la base de données pour la table Company, l'envoie des requêtes et la réception des réponses
 * @author sonia
 *
 */

@Repository
public class CompanyDAO {
	  
     private static final String SELECT_ALL = "FROM Company"; 
	 	 
	 private static final String SELECT_BY_ID = "FROM Company company WHERE company.id = :id";

	 private static final String COUNT = "SELECT COUNT(*) AS total FROM company";
	 
	 private static final String DELETE_COMPANY="DELETE Company WHERE id = :id";
	 	
	 private final static String DELETE_COMPUTERS = "DELETE Computer WHERE company.id = :id";
	 
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
	 public List<Company> getByPage(Page page) {

			List<Company> companiesList = new ArrayList<Company>();

			if (page == null) {
				logger.error("Error listing computers : the page is null");
			} else if (page.getNumberPage() > 0) {
				try (Session session = sessionFactory.openSession()) {
					Query<Company> query = session.createQuery(SELECT_ALL, Company.class);
					query.setFirstResult(page.getFirstLine());
					query.setMaxResults(page.getMaxLines());
					companiesList = query.list();
				} catch (Exception e) {
					logger.error("error when get all by page:", e);
				}
			}
			return companiesList;
	}
	 
	/**
	 * Compter le nombre d'entrées dans la table Company
	 * @return le nombre total d'entrées 
	 */
	 public int countAll() {
		 int total = 0;
			try (Session session = sessionFactory.openSession()){
				
				total = session.createQuery(COUNT, Long.class).getFirstResult();
				
			 } catch (HibernateException e) {
		            logger.error("Error when counting the number of companies",e);
		     }
	        return total; 
	}
	 
	 /**
	  * Suppression d'une company, on commence par supprimer tous les ordinateurs appartenants à la company
	  * @param id identifiant de la company à supprimer
	  */
	 
	 public void deleteCompany(Long id) {
		 if(id!=null) {
			 try (Session session = sessionFactory.openSession()) {
	                Transaction transaction = session.beginTransaction();
	                session.createQuery(DELETE_COMPUTERS).setParameter("id", id).executeUpdate();
	                session.createQuery(DELETE_COMPANY).setParameter("id", id).executeUpdate();
	                transaction.commit();
	            } catch (IllegalStateException | PersistenceException  e) {
	                logger.error("error when deleting computer", e);
	            }
	        } else {
	            logger.error("the computer id to delete is null");
	        }
		 
	}
}
