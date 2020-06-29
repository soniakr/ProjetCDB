package com.excilys.formation.cbd.mapper;

import java.sql.ResultSet;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cbd.dto.ComputerDTO;
import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Computer;

/**
 * Classe s'occupant du Mapping entre la base de données et l'entité Computer
 * @author sonia
 *
 */
public class ComputerMapper {
	
	  private static final String ID_COMPUTER = "id";
	  private static final String NAME_COMPUTER = "name";
	  private static final String INTRODUCED = "introduced";
	  private static final String DISCONTINUED = "discontinued";
	  private static final String COMPANY_ID = "company_id";
	  private static final String COMPANY_NAME = "company_name";
	
		private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

/**
 * S'occupe de la convertion 
 * @param resultSet résultat de la requête
 * @return un objet Computer
 */
	public static Computer convert(ResultSet resultSet) {
		Computer newComputer = null;
	    try {
	        newComputer = new Computer(resultSet.getLong(ID_COMPUTER), resultSet.getString(NAME_COMPUTER));
	        if(resultSet.getDate(INTRODUCED)!=null) {
	           	newComputer.setIntroduced(resultSet.getDate(INTRODUCED).toLocalDate());
	        }
	            
	        if(resultSet.getDate(DISCONTINUED)!=null) {
	          	newComputer.setDiscontinued(resultSet.getDate(DISCONTINUED).toLocalDate());
	        }
	        
	        newComputer.setCompany(
	        		new Company(resultSet.getLong(COMPANY_ID), resultSet.getString(COMPANY_NAME)));	        
	        } catch (Exception e) {
	            System.err.println("Erreur -> Mapping resultSet/Computer");
	        }
	        return newComputer;
	}
	
	/**
	 * Fonction pour convertir un computer en computerDTO
	 * @param computer
	 * @return
	 */
	public static ComputerDTO convertToComputerDTO(Computer computer) {
		ComputerDTO computerDto= new ComputerDTO();
		String id = String.valueOf(computer.getId());
		computerDto.setId(id);
		
		if(computer.getIntroduced()!=null) {
			computerDto.setIntroduced(computer.getIntroduced().toString());
		}
		if(computer.getDiscontinued()!=null) {
			computerDto.setDiscontinued(computer.getDiscontinued().toString());
		}
		computerDto.setName(computer.getName());
		computerDto.setCompany(CompanyMapper.companyToCompanyDto(computer.getCompany()));
	//	System.out.println(computerDto.getCompany().getName());
		return computerDto;
	}

	
	public static Computer toComputer(ComputerDTO computerDTO) {
		        Computer computer = new Computer();
		        try {
		            if (computerDTO.getId() != null) {
		                computer.setId(Long.valueOf(computerDTO.getId()));
		            }
		            computer.setName(computerDTO.getName());
		            if (computerDTO.getIntroduced() != null && !computerDTO.getIntroduced().equals("")) {
		                computer.setIntroduced(LocalDate.parse(computerDTO.getIntroduced()));
		            }
		            if (computerDTO.getDiscontinued() != null && !computerDTO.getDiscontinued().equals("")) {
		                computer.setDiscontinued(LocalDate.parse(computerDTO.getDiscontinued()));
		            }
		            if (computerDTO.getCompany() != null) {
		                computer.setCompany(CompanyMapper.toCompany(computerDTO.getCompany()));
		            }
		        } catch (Exception e) {
		            logger.error("error mapping computerDTO to Computer : " + e.toString());
		        }
		        return computer;	
	}
	
}
