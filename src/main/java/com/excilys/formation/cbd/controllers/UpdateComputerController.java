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
public class UpdateComputerController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private ComputerDTO computerDto;
    private Long idComputer;
    
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;
	
	private static Logger logger = LoggerFactory.getLogger(UpdateComputerController.class);
	
	
	@GetMapping("/editComputer")
	public ModelAndView getUpdatePage(ComputerDTO computer) {
        ModelAndView mv = new ModelAndView("editComputer");

		List<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();
		List<Company> companyList=new ArrayList<Company>();
		
		companyList=companyService.getAll();
		companyList.stream()
				   .forEach(company->companyDtoList.add(
						   CompanyDtoMapper.companyToCompanyDto(company)));
		
		if(computer.getIdComputer()!=null) {
			idComputer=	Long.parseLong(computer.getIdComputer());
		}
		
		Computer computerToUpdate=computerService.getById(idComputer);
		
		if(computerToUpdate!=null) {
			computerDto=ComputerDtoMapper.convertToComputerDTO(computerToUpdate);
		} else {
			logger.error("ID Computer n'existe pas");
		}
		
		 mv.getModel().put("companies", companyDtoList);
		 mv.getModel().put("idComputer", computer.getIdComputer());
		 mv.getModel().put("computerToUpdate", computerDto);
		 
		 return mv;
	}
	
	@PostMapping("/editComputer")
	public ModelAndView editComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        ModelAndView mv = new ModelAndView("redirect:dashboard");

    	try {
    		CompanyDTO companyDTO=null;
    		if(request.getParameter("companyId")!="") {
    			companyDTO=new CompanyDTO(Long.parseLong(request.getParameter("companyId")));
    		}
    		ComputerDTO computerDTO=new ComputerDTO(request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),companyDTO);
    		ComputerValidator validator= new ComputerValidator();
    		if(validator.validateComputer(computerDTO)) {
    			Computer computer = ComputerDtoMapper.toComputer(computerDTO);
    			computer.setId(idComputer);
    			computerService.updateComputer(computer);
    			logger.info("Success update");		
    		} else {
    			logger.error("Update not allowed");		
    		}
    		
    	} catch (NumberFormatException e) {
    		logger.error("Update not allowed",e.getMessage());
    	} finally {
    		//doGet(request, response);
    	}
		
		return mv;

	}

}
