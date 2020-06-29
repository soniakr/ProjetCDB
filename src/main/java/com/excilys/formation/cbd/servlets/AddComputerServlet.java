package com.excilys.formation.cbd.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cbd.dto.CompanyDTO;
import com.excilys.formation.cbd.dto.ComputerDTO;
import com.excilys.formation.cbd.mapper.CompanyMapper;
import com.excilys.formation.cbd.mapper.ComputerMapper;
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
       
	public CompanyService companyService=CompanyService.getInstance();
	public ComputerService computerService=ComputerService.getInstance();
	
	private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

    public AddComputerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Company> companyList=companyService.getAll();
		List<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();

		companyList.stream().forEach(company->companyDtoList.add(
						   CompanyMapper.companyToCompanyDto(company)));
		
		request.setAttribute("companies", companyDtoList);
		request.getRequestDispatcher("views/addComputer.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			CompanyDTO companyDTO=null;
			if(request.getParameter("companyId")!=null) {
				companyDTO=new CompanyDTO(Long.parseLong(request.getParameter("companyId")));
			}
			ComputerDTO computerDTO=new ComputerDTO(request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),companyDTO);
			
			System.out.println("id company : " + request.getParameter("companyId"));
			System.out.println("Name : " + request.getParameter("computerName") + "   " + request.getParameter("introduced") + "  " + request.getParameter("discontinued"));
			
			ComputerValidator validator= new ComputerValidator();
			if(validator.validateComputer(computerDTO)) {
				Computer computer = ComputerMapper.toComputer(computerDTO);
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
