package com.excilys.formation.cbd.dto.mappers;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cbd.dto.ComputerDTO;
import com.excilys.formation.cbd.mapper.CompanyMapper;
import com.excilys.formation.cbd.model.Computer;

public class ComputerDtoMapper {
	
	  private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

	/**
	 * Fonction pour convertir un computer en computerDTO
	 * @param computer
	 * @return
	 */
	public static ComputerDTO convertToComputerDTO(Computer computer) {
		ComputerDTO computerDto= new ComputerDTO();
		if(computer.getId()!=null) { //Peut être nul si on insert
			String id = String.valueOf(computer.getId());
			computerDto.setIdComputer(id);
		}
		
		if(computer.getIntroduced()!=null) {
			computerDto.setIntroduced(computer.getIntroduced().toString());
		}
		if(computer.getDiscontinued()!=null) {
			computerDto.setDiscontinued(computer.getDiscontinued().toString());
		}
		computerDto.setName(computer.getName());
		computerDto.setCompany(CompanyDtoMapper.companyToCompanyDto(computer.getCompany()));
		return computerDto;
	}

	
	/**
	 * Convertir un computerDTO en Computer
	 * On ne procède à aucune vérification des champs car ils sont validés par le validator
	 * @param un computerDTO
	 * @return l'objet Computer correspondant
	 */
	public static Computer toComputer(ComputerDTO computerDTO) {
		        Computer computer = new Computer();
		        try {
		            if (computerDTO.getIdComputer() != null) {
		                computer.setId(Long.valueOf(computerDTO.getIdComputer()));
		            }
		            computer.setName(computerDTO.getName());
		            if (computerDTO.getIntroduced() != null && !computerDTO.getIntroduced().equals("")) {
		                computer.setIntroduced(LocalDate.parse(computerDTO.getIntroduced()));
		            }
		            if (computerDTO.getDiscontinued() != null && !computerDTO.getDiscontinued().equals("")) {
		                computer.setDiscontinued(LocalDate.parse(computerDTO.getDiscontinued()));
		            }
		            if (computerDTO.getCompany() != null) {
		                computer.setCompany(CompanyDtoMapper.toCompany(computerDTO.getCompany()));
		               // computer.setIdCompany(Long.valueOf(computerDTO.getCompany().getidCompany()));
		            }
		        } catch (Exception e) {
		            logger.error("error mapping computerDTO to Computer : " + e.toString());
		        }
		        return computer;	
	}
}
