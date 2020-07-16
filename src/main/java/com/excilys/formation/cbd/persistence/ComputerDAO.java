package com.excilys.formation.cbd.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cbd.persistence.mapper.ComputerMapper;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Page;

/**
 * Classe qui gére la communication avec la base de données pour la table
 * Computer, l'envoie des requêtes et la réception des réponses
 * 
 * @author sonia
 *
 */
@Repository
public class ComputerDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private static final String SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY computer.id";

	private static final String SELECT_BY_ID = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.id = ?  ";

	private static final String SELECT_BY_NAME = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY %s LIMIT ? OFFSET ? ";

	private static final String SELECT_PAGE = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY %s LIMIT ? OFFSET ?";

	private static final String COUNT = "SELECT COUNT(*) AS total FROM computer ";

	private static final String COUNT_BY_NAME = "SELECT COUNT(*) AS total FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?";

	private static final String INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";

	private static final String DELETE = "DELETE FROM computer WHERE id = ?";

	static final String DELETE_COMPUTER_WITH_COMPANY_ID = "DELETE FROM computer WHERE company_id= ? ";

	/**
	 * Tout les ordinateurs de la table Computer
	 * 
	 * @return Liste de tous le ordinateurs
	 */
	
	public List<Computer> getAll() {
		
		List<Computer> computers = new ArrayList<Computer>();
		try ( Connection connect = ConnexionHikari.getConnection()){
			computers=jdbcTemplate.query(SELECT_ALL, new ComputerMapper());
        } catch (SQLException e) {
            logger.error("Error when listing all computers",e);
        }
		return computers;	
	}

	/**
	 * Trouver un ordinateur à partir de l'id
	 * 
	 * @param id à rechercher dans la base
	 * @return l'ordinateur correspondant si il existe
	 */
	public Computer findById(Long id) {
		
		Computer computer = new Computer();
		if(id!=null) {
			try ( Connection connect = ConnexionHikari.getConnection()){
				computer = jdbcTemplate.queryForObject(SELECT_BY_ID, new ComputerMapper(), id);
	        } catch (SQLException e) {
	           logger.error("Error when finding a computer with its ID",e);
	        }
		}
		return computer;
	}

	/**
	 * Tout les ordinateurs qui ont un nom donné
	 * 
	 * @return Liste de tous le ordinateurs
	 */
	public List<Computer> getAllByName(Page p, String name, String orderBy) {
		List<Computer> computersList = new ArrayList<Computer>();
		String request = orderBy(orderBy, SELECT_BY_NAME);
		String nameInRequest= "%" + name + "%";
		
		try (Connection connect = ConnexionHikari.getConnection()){
			
			computersList = jdbcTemplate.query(request, new ComputerMapper(), nameInRequest, nameInRequest, p.getMaxLines(), p.getFirstLine());
		
		} catch (SQLException e) {
			logger.error("Erreur DAO -> Lister les ordinateurs par nom : " + p.getNumberPage() + e.getMessage());
		}
		return computersList;
	}

	public String orderBy(String s, String requete) {

		String order;
		if (s != null) {
			String[] arguments = s.split("\\.");

			if (arguments.length == 3) {
				order = arguments[0] + "." + arguments[1] + " " + arguments[2];
			} else if (arguments.length == 2) {
				order = arguments[0] + " " + arguments[1];
			} else {
				order = "computer.id";
			}
		} else {
			order = "computer.id";
		}

		String res = String.format(requete, order);
		return res;
	}

	/**
	 * Retourne la liste de tous les ordinateurs dans une page spécifique
	 * 
	 * @param p       on cherche les informations contenues dans la page p
	 * @param orderBy
	 * @return la liste d'ordinateurs
	 */
	public List<Computer> getByPage(Page p, String orderBy) {

		List<Computer> computersList = new ArrayList<Computer>();
		String order = orderBy(orderBy, SELECT_PAGE);

		try (Connection connect = ConnexionHikari.getConnection()){
			
			computersList = jdbcTemplate.query(order, new ComputerMapper(), p.getMaxLines(), p.getFirstLine());
	
		} catch (SQLException e) {
			logger.error("Erreur DAO -> Lister les ordinateurs de la page : " + p.getNumberPage() + e.getMessage());
		}
		return computersList;
	}

	/**
	 * Ajouter un ordinateur à la base de données
	 * 
	 * @param computer l'entité à insérer dans la table
	 */
	public void insert(Computer computer) {

		if (computer != null) {
			
			try (Connection connect = ConnexionHikari.getConnection()) {

				Date introducedDate = null;
				if (computer.getIntroduced() != null) {
					introducedDate = Date.valueOf(computer.getIntroduced());
				}

				Date discontinuedDate = null;
				if (computer.getDiscontinued() != null) {
					discontinuedDate = Date.valueOf(computer.getDiscontinued());
				}

				Long idCompany = computer.getIdCompany()== null ? null : computer.getCompany().getId();
				
				jdbcTemplate.update(INSERT, 
	            		computer.getName(), 
	            		introducedDate,
	            		discontinuedDate, 
	            		idCompany);  
			} catch (SQLException e) {
				logger.error("Erreur insertion base de données (Vérifier que l'ID de l'entreprise existe bien) ");
			}
		}
	}

	/**
	 * Mise à jour d'un ordinateur de la base de données
	 * 
	 * @param computer l'entité avec les modifications à apporter
	 */
	public void update(Computer computer) {
		if (computer != null) {
 
			try (Connection connect = ConnexionHikari.getConnection()) {
				Date introducedDate = null;
				if (computer.getIntroduced() != null) {
					introducedDate = Date.valueOf(computer.getIntroduced());
				}

				Date discontinuedDate = null;
				if (computer.getDiscontinued() != null) {
					discontinuedDate = Date.valueOf(computer.getDiscontinued());
				}

				Long idCompany = computer.getIdCompany()== null ? null : computer.getCompany().getId();
				
				jdbcTemplate.update(UPDATE, 
	            		computer.getName(), 
	            		introducedDate,
	            		discontinuedDate, 
	            		idCompany,
	            		computer.getId());  
			} catch (SQLException e) {
				logger.error("ErreurDAO : update ");
			}
		}
	}

	/**
	 * Supprimer un ordinateur de la base de données
	 * 
	 * @param id identifiant de l'ordinateur à supprimer
	 */
	public void delete(Long id) {
		try (Connection connect = ConnexionHikari.getConnection()) {
            jdbcTemplate.update(DELETE, id);
        } catch (SQLException e) {
            logger.error("Error when deleting a Computer",e);
        }
	}

	/**
	 * Compter le nombre d'entrées dans la table Computer
	 * @param si il y'a un filtre
	 * @return le nombre total d'entrées
	 */
	public int countAll(String search) {
		
		int total = 0;
		try (Connection connect = ConnexionHikari.getConnection()){
			if(search==null) {
				total = jdbcTemplate.queryForObject(COUNT, Integer.class);
			}else {
				total = jdbcTemplate.queryForObject(COUNT_BY_NAME, Integer.class , "%"+search+"%" , "%"+search+"%");
			}
		 } catch (SQLException e) {
	            logger.error("Error when counting the number of computers",e);
	     }
        return total; 
	}

}
