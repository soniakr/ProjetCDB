package com.excilys.formation.cbd.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Servlet implementation class EditComputerServlet
 */
@WebServlet(urlPatterns = "/editComputer")
public class EditComputerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ComputerDTO computerDto;
    private String companyName;
    private Long idComputer;
   
    private CompanyService companyService=CompanyService.getInstance();
	private ComputerService computerService=ComputerService.getInstance();
	
       
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
		
		if(request.getParameter("id")!=null) {
			idComputer=	Long.parseLong(request.getParameter("id"));
			System.out.println();
		}
		Computer computerToUpdate=computerService.getById(idComputer);
		computerDto=ComputerMapper.convertToComputerDTO(computerToUpdate);

		List<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();
		List<Company> companyList=new ArrayList<Company>();
		
		companyList=companyService.getAll();
		companyList.stream()
				   .forEach(company->companyDtoList.add(
						   CompanyMapper.companyToCompanyDto(company)));
		request.setAttribute("companies", companyDtoList);
		request.setAttribute("computerToUpdate", computerToUpdate);
		
		request.getRequestDispatcher("views/editComputer.jsp").forward(request, response);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CompanyDTO companyDTO=null;
		if(request.getParameter("companyId")!=null) {
			companyDTO=new CompanyDTO(Long.parseLong(request.getParameter("companyId")));
		}
		ComputerDTO computerDTO=new ComputerDTO(request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),companyDTO);
		ComputerValidator validator= new ComputerValidator();
		
		doGet(request, response);
	}

}
