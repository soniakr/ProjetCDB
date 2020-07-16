package com.excilys.formation.cbd.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cbd.dto.CompanyDTO;
import com.excilys.formation.cbd.dto.ComputerDTO;
import com.excilys.formation.cbd.dto.mappers.CompanyDtoMapper;
import com.excilys.formation.cbd.dto.mappers.ComputerDtoMapper;
import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.service.CompanyService;
import com.excilys.formation.cbd.service.ComputerService;
import com.excilys.formation.cbd.validators.ComputerValidator;

@Controller
public class AddComputerController {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	
	private static Logger logger = LoggerFactory.getLogger(AddComputerController.class);
    
	@GetMapping("/addComputer")
	public ModelAndView getListCompanies(ModelMap dataMap) {
		
		List<Company> companyList=companyService.getAll();
		List<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();
        ModelAndView mv = new ModelAndView("addComputer");


		companyList.stream().forEach(company->companyDtoList.add(
						   CompanyDtoMapper.companyToCompanyDto(company)));
		
		mv.getModel().put("companies", companyDtoList);
		
		return mv;

	}

	@PostMapping("/addComputer")
	public ModelAndView addComputer(ComputerDTO computerDto, CompanyDTO companyDTO) {

		try {
			
			if(companyDTO.getidCompany() != null) {
				 computerDto.setCompany(companyDTO);
			}
			
			ComputerDTO computerDTO=new ComputerDTO(computerDto.getName(),computerDto.getIntroduced(),computerDto.getDiscontinued(),companyDTO);
			ComputerValidator validator= new ComputerValidator();
			
			if(validator.validateComputer(computerDTO)) {
				Computer computer = ComputerDtoMapper.toComputer(computerDTO);
				computerService.addComputer(computer);
				logger.info("Success add Computer");		
			} else {
				logger.error("Insert not allowed");		
			}
			
		} catch (NumberFormatException e) {
			logger.error("Insert not allowed ", e);
		} 
		
		return new ModelAndView("redirect:ListComputers");

	}

}
