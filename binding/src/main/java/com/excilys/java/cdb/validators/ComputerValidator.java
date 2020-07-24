package com.excilys.java.cdb.validators;

import java.time.LocalDate;

import com.excilys.java.cdb.dto.ComputerDTO;


public class ComputerValidator {
	
	
	public boolean validateComputer(ComputerDTO computerDTO) {
		if(validateDates(computerDTO) && validateComputerName(computerDTO)){
			return true;
		}
		return false;
	}
	
	
	private boolean validateDates(ComputerDTO computerDTO){
		if(computerDTO.getIntroduced()!= null && !computerDTO.getIntroduced().equals("") && !computerDTO.getDiscontinued().equals("") &&computerDTO.getDiscontinued()!=null) {
			LocalDate intro = LocalDate.parse(computerDTO.getIntroduced());
			LocalDate disc = LocalDate.parse(computerDTO.getDiscontinued());

			if(!(disc.isAfter(intro))) {
				//throw new ValidatorException("ValidatorException: Introduced date should be before discontinuedDate");
			}
		}
		return true;
	}
	
	private boolean validateComputerName(ComputerDTO computerDTO){
		if(computerDTO.getName()==null || computerDTO.getName().equals("")) {
			//throw new ValidatorException("ValidatorException: Computer Name is required");
		}
		return true;
	}
}
