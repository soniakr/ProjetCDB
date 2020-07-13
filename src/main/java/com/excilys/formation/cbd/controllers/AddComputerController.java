package com.excilys.formation.cbd.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.cbd.dto.CompanyDTO;
import com.excilys.formation.cbd.dto.ComputerDTO;
import com.excilys.formation.cbd.dto.mappers.CompanyDtoMapper;
import com.excilys.formation.cbd.dto.mappers.ComputerDtoMapper;
import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.service.CompanyService;
import com.excilys.formation.cbd.service.ComputerService;
import com.excilys.formation.cbd.servlets.AddComputerServlet;
import com.excilys.formation.cbd.validators.ComputerValidator;

@Controller
public class AddComputerController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	
	private static Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
    
	@GetMapping("/addComputer")

	public String getListCompanies(ModelMap dataMap) throws ServletException, IOException {
		
		List<Company> companyList=companyService.getAll();
		List<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();

		companyList.stream().forEach(company->companyDtoList.add(
						   CompanyDtoMapper.companyToCompanyDto(company)));
		
		dataMap.put("companies", companyDtoList);
		
		return "addComputer";

	}

	@PostMapping("/addComputer")
	public String addComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			CompanyDTO companyDTO=null;
			if(request.getParameter("companyId")!="") {
				companyDTO=new CompanyDTO(Long.parseLong(request.getParameter("companyId")));
			}
			
			ComputerDTO computerDTO=new ComputerDTO(request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),companyDTO);
			ComputerValidator validator= new ComputerValidator();
			if(validator.validateComputer(computerDTO)) {
				Computer computer = ComputerDtoMapper.toComputer(computerDTO);
				computerService.addComputer(computer);
				logger.info("Success");		
			} else {
				logger.error("Insert not allowed");		
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error("Insert not allowed");
		} finally {
			doGet(request, response);
		}
		
		return "redirect:dashboard";

	}

}
