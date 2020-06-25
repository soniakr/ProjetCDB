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

@WebServlet(urlPatterns = "/ListComputers")
public class ListComputerServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	private int pageIterator, pageDemande;
	private int taillePage=10;
	
	public ComputerService computerService=ComputerService.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int nbComputer = computerService.countAll();
		pageIterator=0;
		pageDemande=0;
		
		Page newPage = new Page();
	
		if(request.getParameter("taillePage")!=null) {
			taillePage=Integer.parseInt(request.getParameter("taillePage"));
		} 
		newPage.setMaxLines(taillePage);
		System.out.println("les pages sont mnt de taille : " + newPage.getMaxLines());

		
		int maxPages=newPage.getTotalPages(nbComputer);
		System.out.println("nombre de pages max :"+ maxPages);
		request.setAttribute("maxPages", maxPages);
		
		if(request.getParameter("pageIterator")!=null) {
			pageDemande=Integer.parseInt(request.getParameter("pageIterator"));
			if(pageDemande>0 && pageDemande<=maxPages) {
				pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
	    		newPage.setNumberPage(pageIterator);
	    		newPage.calculFirstLine();
			}

		}
        List<Computer> allComputers = computerService.getByPage(newPage);
        
       request.setAttribute("pageIterator", pageIterator);
       request.setAttribute("computersList", allComputers);
       request.setAttribute("nbComputers", nbComputer);

		
		request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}
	 
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
