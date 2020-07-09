package com.excilys.formation.cbd.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.cbd.dto.CompanyDTO;
import com.excilys.formation.cbd.dto.ComputerDTO;
import com.excilys.formation.cbd.dto.mappers.CompanyDtoMapper;
import com.excilys.formation.cbd.dto.mappers.ComputerDtoMapper;
import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.service.CompanyService;
import com.excilys.formation.cbd.service.ComputerService;
import com.excilys.formation.cbd.validators.ComputerValidator;

/**
 * Servlet implementation class AddComputerServlet
 */

@WebServlet(urlPatterns = "/addComputer")

public class AddComputerServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
    
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	
	private static Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
    	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Company> companyList=companyService.getAll();
		List<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();

		companyList.stream().forEach(company->companyDtoList.add(
						   CompanyDtoMapper.companyToCompanyDto(company)));
		
		request.setAttribute("companies", companyDtoList);
		request.getRequestDispatcher("views/addComputer.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
	}

}
