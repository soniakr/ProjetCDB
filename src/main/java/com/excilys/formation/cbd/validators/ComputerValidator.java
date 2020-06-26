package com.excilys.formation.cbd.validators;

import com.excilys.formation.cbd.model.Computer;
import com.excilys.formtion.cbd.exceptions.ValidatorException;

public class ComputerValidator {
	
	
	public  void validateComputer(Computer computer) {
		validateDates(computer);
		validateComputerName(computer);
	}
	
	
	private void validateDates(Computer computer){
		if(computer.getIntroduced()== null || computer.getDiscontinued()==null) {
			//Do nothing
		} else {
			if(!(computer.getDiscontinued().isAfter(computer.getIntroduced()))) {
			
				throw new ValidatorException("ValidatorException: Introduced date should be before discontinuedDate");
			}
		}
	}
	
	private void validateComputerName(Computer computer){
		if(computer.getName()==null || computer.getName().equals("")) {
			throw new ValidatorException("ValidatorException: Computer Name is required");
		}
	}
}
