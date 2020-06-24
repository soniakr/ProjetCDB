package com.excilys.formation.cbd.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Page;
import com.excilys.formation.cbd.service.ComputerService;

@WebServlet(urlPatterns = "")
public class ListComputerServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	
	public ComputerService computerService=ComputerService.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int nbComputer = computerService.countAll();
		int currentPage=1;
		
		Page newPage = new Page();
		
        List<Computer> allComputers = computerService.getByPage(newPage);
        
       request.setAttribute("currentPage", currentPage);
       request.setAttribute("computersList", allComputers);
       request.setAttribute("nbComputers", nbComputer);

		
		request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}
	 
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	}
	
}
