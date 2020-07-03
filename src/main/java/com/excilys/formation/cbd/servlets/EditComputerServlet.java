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
import com.excilys.formation.cbd.dto.mappers.CompanyDtoMapper;
import com.excilys.formation.cbd.dto.mappers.ComputerDtoMapper;
import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.service.CompanyService;
import com.excilys.formation.cbd.service.ComputerService;
import com.excilys.formation.cbd.validators.ComputerValidator;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet(urlPatterns = "/editComputer")
public class EditComputerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ComputerDTO computerDto;
    private Long idComputer;
   
    private CompanyService companyService=CompanyService.getInstance();
	private ComputerService computerService=ComputerService.getInstance();
	
	private static Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();
		List<Company> companyList=new ArrayList<Company>();
		
		companyList=companyService.getAll();
		companyList.stream()
				   .forEach(company->companyDtoList.add(
						   CompanyDtoMapper.companyToCompanyDto(company)));
		
		if(request.getParameter("idComputer")!=null) {
			idComputer=	Long.parseLong(request.getParameter("idComputer"));
			System.out.println("id du comp à update : " + idComputer);
		}
		
		Computer computerToUpdate=computerService.getById(idComputer);
		
		if(computerToUpdate!=null) {
			computerDto=ComputerDtoMapper.convertToComputerDTO(computerToUpdate);
		} else {
			logger.error("ID Computer n'existe pas");
		}
		
		request.setAttribute("companies", companyDtoList);
		request.setAttribute("idComp", idComputer);
		request.setAttribute("computerToUpdate", computerDto);
		
		request.getRequestDispatcher("views/editComputer.jsp").forward(request, response);	}

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
			computer.setId(idComputer);
			computerService.updateComputer(computer);
			logger.info("Success");		
		} else {
			logger.error("Update not allowed");		
		}
		
	} catch (NumberFormatException e) {
		logger.error("Update not allowed",e.getMessage());
	} finally {
		doGet(request, response);
	}
	}

}
