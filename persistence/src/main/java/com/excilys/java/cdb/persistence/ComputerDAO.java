package com.excilys.java.cdb.persistence;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.java.cdb.model.Computer;
import com.excilys.java.cdb.model.Page;

/**
 * Classe qui gére la communication avec la base de données pour la table
 * Computer, l'envoie des requêtes et la réception des réponses
 * 
 * @author sonia
 *
 */
@Repository
public class ComputerDAO {
	

	private static final String SELECT_ALL = "FROM Computer computer ORDER BY ";

	private static final String SELECT_BY_ID = "FROM Computer computer WHERE computer.id = :id";

	private static final String SELECT_BY_NAME = "FROM Computer computer WHERE computer.name LIKE :toSearch OR company.name LIKE :toSearch ORDER BY ";

	private static final String COUNT = "SELECT COUNT(computer) from Computer computer LEFT JOIN computer.company as company";

	private static final String COUNT_BY_NAME = "SELECT COUNT(computer) from Computer computer LEFT JOIN computer.company as company WHERE computer.name LIKE :toSearch OR computer.company.name LIKE :toSearch ";

	private static final String UPDATE = "UPDATE Computer computer SET computer.name = :name, computer.introduced = :introduced, computer.discontinued = :discontinued, company.id = :companyId WHERE computer.id = :computerId";

	private static final String DELETE = "DELETE Computer WHERE id = :id";

	static final String DELETE_COMPUTER_WITH_COMPANY_ID = "DELETE FROM computer WHERE company_id= ? ";
	
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	
    private SessionFactory sessionFactory;
    
	@Autowired
	public ComputerDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<Computer> getAll() {
		
		String request = orderBy("",SELECT_ALL);
        List<Computer> computerList = new ArrayList<Computer>();
        
        try (Session session = sessionFactory.openSession()) {
            computerList = session.createQuery(request, Computer.class).list();
        } catch (HibernateException e) {
            logger.error("error when getting all computers", e);
        }
        return computerList;
    }

	/**
	 * Trouver un ordinateur à partir de l'id
	 * 
	 * @param id à rechercher dans la base
	 * @return l'ordinateur correspondant si il existe
	 */
	public Computer findById(Long id) {
		
		Computer computer = new Computer();
		
		if (id != null) {
            try (Session session = sessionFactory.openSession()) {
                Query<Computer> query = session.createQuery(SELECT_BY_ID, Computer.class);
                query.setParameter("id", id);
                computer = query.uniqueResult();
            } catch (HibernateException e) {
                logger.error("error when finding computer by id", e);
            }
        } else {
            logger.error("company id to find is null");
        }
		return computer;
	}

	/**
	 * Tout les ordinateurs qui ont un nom donné
	 * 
	 * @return Liste de tous le ordinateurs
	 */
	public List<Computer> getAllByName(Page page, String name, String orderBy) {
		String request = orderBy(orderBy,SELECT_BY_NAME);
		 List<Computer> computerList = new ArrayList<Computer>();
	        if (page.getNumberPage() > 0 && name != null && !name.isEmpty() && !name.contains("%") && !name.contains("_")) {
	            try (Session session = sessionFactory.openSession()) {
	                Query<Computer> query = session.createQuery(request, Computer.class);
	                query.setParameter("toSearch", "%".concat(name).concat("%"));
	                query.setFirstResult(page.getFirstLine());
	                query.setMaxResults(page.getMaxLines());
	                computerList = query.list();
	            } catch (HibernateException e) {
	                logger.error("error when get all by page:", e);
	            }
	        }
	        return computerList;
	}

	public String orderBy(String s, String requete) {

		String order;
		if (s != null && !s.equals("")) {
			String[] arguments = s.split("\\.");

			if (arguments.length == 3) {
				order = arguments[0] + "." + arguments[1] + " " + arguments[2];
			} else if (arguments.length == 2) {
				order = arguments[0] + " " + arguments[1];
			} else {
				order = "computer.id desc";
			}
		} else {
			order = "computer.id desc";
		}

		String res = requete + order;
		return res;
	}

	/**
	 * Retourne la liste de tous les ordinateurs dans une page spécifique
	 * 
	 * @param p       on cherche les informations contenues dans la page p
	 * @param orderBy
	 * @return la liste d'ordinateurs
	 */
	public List<Computer> getByPage(Page page, String orderBy) {

		List<Computer> computerList = new ArrayList<Computer>();
		String request = orderBy(orderBy,SELECT_ALL);

        if (page == null) {
            logger.error("Error listing computers : the page is null");
        } else if (page.getNumberPage() > 0) {
            try (Session session = sessionFactory.openSession()) {
                Query<Computer> query = session.createQuery(request, Computer.class);
                query.setFirstResult(page.getFirstLine());
                query.setMaxResults(page.getMaxLines());
                computerList = query.list();
            } catch (Exception e) {
                logger.error("error when get all by page:", e);
            }
        }
        return computerList;
	}

	/**
	 * Ajouter un ordinateur à la base de données
	 * 
	 * @param computer l'entité à insérer dans la table
	 */
	public void insert(Computer computer) {
		
	    if (computer != null) {
			try (Session session = sessionFactory.openSession()) {
	            session.save(computer);
	        } catch (HibernateException e) {
	            logger.error("Error creating a computer", e);
	        }
	    } else {
	        logger.error("Error creating computer : the computer is null");
	    }
	    	logger.info(computer.toString());
	}

	/**
	 * Mise à jour d'un ordinateur de la base de données
	 * 
	 * @param computer l'entité avec les modifications à apporter
	 */
	public void update(Computer computer) {
        int nbResults = 0;
		if (computer != null) {
 
			 try (Session session = sessionFactory.openSession()) {
	                Transaction transaction = session.beginTransaction();
	                nbResults = session.createQuery(UPDATE)
	                        .setParameter("name", computer.getName())
	                        .setParameter("introduced", computer.getIntroduced())
	                        .setParameter("discontinued", computer.getDiscontinued())
	                        .setParameter("companyId", computer.getCompany() == null ? null : computer.getCompany().getId())
	                        .setParameter("computerId", computer.getId()).executeUpdate();
	                transaction.commit();
	                if (nbResults != 1) {
	                    logger.error("%d rows affected when updating computer", nbResults);
	                }
			} catch (IllegalStateException | RollbackException | HibernateException e) {
				logger.error("ErrorDAO : Updating computer ");
			}
		}
	}

	/**
	 * Supprimer un ordinateur de la base de données
	 * 
	 * @param id identifiant de l'ordinateur à supprimer
	 */
	public void delete(Long id) {
		int nbRows = 0;
		if (id != null) {
			try (Session session = sessionFactory.openSession()) {
				Transaction transaction = session.beginTransaction();
				nbRows = session.createQuery(DELETE).setParameter("id", id).executeUpdate();
				transaction.commit();
				if (nbRows != 1) {
					logger.error("%d rows affected when deleting computer", nbRows);
				}
			} catch (IllegalStateException | PersistenceException e) {
				logger.error("error when deleting computer", e);
			}
		} else {
			logger.error("the id of the computer to delete is null");
		}
	}

	/**
	 * Compter le nombre d'entrées dans la table Computer
	 * @param si il y'a un filtre
	 * @return le nombre total d'entrées
	 */
	public int countAll(String name) {
		
		int total = 0;
		try (Session session = sessionFactory.openSession()){
			if(name==null) {
				total = session.createQuery(COUNT, Long.class).getFirstResult();
			}	else {
				System.out.println("ici");
				total = session.createQuery(COUNT_BY_NAME, Long.class).setParameter("toSearch", ("%".concat(name).concat("%"))).uniqueResult().intValue();
			}
		 } catch (HibernateException e) {
	            logger.error("Error when counting the number of computers",e);
	     }
        return total; 
	}

}
