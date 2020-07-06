package com.excilys.formation.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cbd.mapper.CompanyMapper;
import com.excilys.formation.cbd.mapper.ComputerMapper;
import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Page;

/**
 * Classe qui gére la communication avec la base de données pour la table Company, l'envoie des requêtes et la réception des réponses
 * @author sonia
 *
 */
public class CompanyDAO {
	
	 private static CompanyDAO compagnyDAO;
	 
	 private Connection connect;
	  
	 private static final String SELECT_ALL = "SELECT * FROM company ORDER BY id";
	   
	 private static final String SELECT_PAGE = "SELECT * FROM company ORDER BY id LIMIT ? OFFSET ? ";
	 
	 private static final String SELECT_BY_ID = "SELECT * FROM company WHERE company.id = ?  ";

	 private static final String COUNT = "SELECT COUNT(*) AS total FROM company";
	 
	 private static final String DELETE_COMPANY="DELETE FROM company WHERE id= ?";
	 
	 private final static String GET_COMPANY_COMPUTERS = "select id from computer where company_id = ?";
	
	 private final static String DELETE_COMPUTERS = "DELETE FROM computer WHERE id IN (";
	 
	 private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);
	 
	 /**
	     * Instance of the singleton CompanyDAO.
	     * @return the instance of CompanyDAO
	     */
	 public static CompanyDAO getInstance() {
	        if (compagnyDAO == null) {
	            compagnyDAO = new CompanyDAO();
	        }
	        return compagnyDAO;
	}
	 
	 /**
	  * Connection à la base de données 
	  */
	 
	 public void connectBD() {
		 	connect = ConnexionBD.getConnection();

	 }
	 
	 /**
	  * 
	  * @return Liste de toutes les compagnies
	  */
	 public List<Company> getAll() {
		 	
		 	connectBD();
	        List<Company> companyList = new ArrayList<Company>();

	        try (PreparedStatement statement = connect.prepareStatement(SELECT_ALL)){
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                Company company = CompanyMapper.convert(resultSet);
	                companyList.add(company);
	            }
	            connect.close();
	        } catch (SQLException e) {
	            logger.error("Erreur DAO -> Lister toutes les company",e);
	        }

	        return companyList;
	}
	 
	 
	 public Company findById(Long id) {
	    	connectBD();

	        Company result=null;
	        if (id != null) {
	            try (PreparedStatement statement = connect.prepareStatement(SELECT_BY_ID)) {
	                statement.setLong(1, id);
	                ResultSet resultSet = statement.executeQuery();

	                while (resultSet.next()) {
	                    result = CompanyMapper.convert(resultSet);
	                }
	                connect.close();

	            } catch (SQLException e) {
	              
	            	logger.error("Erreur DAO -> Find Company by ID : " + e.getMessage());
	            }
	        }
	        return result;
	}
	 
	 /**
	  * Retourne la liste de tous les ordinateurs dans une page spécifique
	  * @param p on cherche les informations contenues dans la page p
	  * @return
	  */
	 public List<Company> getByPage(Page p) {
	     
		 connectBD();
		 List<Company> companyList = new ArrayList<Company>();

		 if(p!=null) {	 

		        try  {
		        	PreparedStatement statement = connect.prepareStatement(SELECT_PAGE);
		        	
	                statement.setInt(1, p.getMaxLines());
	                statement.setInt(2, p.getFirstLine());

		        	ResultSet resultSet = statement.executeQuery();
		        	while (resultSet.next()) {
		                Company company = CompanyMapper.convert(resultSet);
		                companyList.add(company);
		            }
		            connect.close();

		        } catch (SQLException e) {
		        	logger.error("Erreur DAO -> Lister les company de la page : " + p.getNumberPage() + e.getMessage());
		        }
		 }  else {
			 logger.error("La page demandée est null");
		 }
	     return companyList;
	}
	 
	/**
	 * Compter le nombre d'entrées dans la table Company
	 * @return le nombre total d'entrées 
	 */
	 public int countAll() {
		 	connectBD();
	        int result=0;
	        try (PreparedStatement statement = connect.prepareStatement(COUNT)){
	        	
	            ResultSet resultSet = statement.executeQuery();
	            
	            while (resultSet.next()) {
	                result=resultSet.getInt("total");
	            }
	           System.out.println("Nombre total d'entrées dans la base : " + result);
	            
	           connect.close();

	        } catch (SQLException e) {
	            logger.error("Erreur DAO -> CountAll Company");
	        }
	        return result;
	}
	 
	 /**
	  * Suppression d'une company, on commence par supprimer tous les ordinateurs appartenants à la company
	  * @param id identifiant de la company à supprimer
	  */
	 
	 public void deleteCompany(Long id) {
		 connectBD();
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
