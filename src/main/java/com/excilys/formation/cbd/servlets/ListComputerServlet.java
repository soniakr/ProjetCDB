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

import com.excilys.formation.cbd.dto.ComputerDTO;
import com.excilys.formation.cbd.dto.mappers.ComputerDtoMapper;
import com.excilys.formation.cbd.mapper.CompanyMapper;
import com.excilys.formation.cbd.mapper.ComputerMapper;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Page;
import com.excilys.formation.cbd.service.ComputerService;

@WebServlet(urlPatterns = "/ListComputers")
public class ListComputerServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	private int pageIterator, pageDemande;
	private int taillePage=10;
	
	public ComputerService computerService=ComputerService.getInstance();
	
	private static Logger logger = LoggerFactory.getLogger(ListComputerServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<ComputerDTO>allComputersDTO=new ArrayList<ComputerDTO>();
	    List<Computer> allComputers;
		
		int nbComputer;
		pageIterator=1;
		pageDemande=0;
		
		Page newPage = new Page();
	
		if(request.getParameter("taillePage")!=null) {
			taillePage=Integer.parseInt(request.getParameter("taillePage"));
		} 
		newPage.setMaxLines(taillePage);
		
		String search =(request.getParameter("search")!=null)?request.getParameter("search"):null;
		
		if(search != null) {
			allComputers=computerService.getAllByName(newPage,search);
		} else {
		    allComputers = computerService.getByPage(newPage);
		}
	    nbComputer = computerService.countAll(search);
	    System.out.println("nombre de comp trouvées : " + nbComputer);

		int maxPages=newPage.getTotalPages(nbComputer);
		request.setAttribute("maxPages", maxPages);
		
		if(request.getParameter("pageIterator")!=null) {
			pageDemande=Integer.parseInt(request.getParameter("pageIterator"));
			if(pageDemande>0 && pageDemande<=maxPages) {
				pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
	    		newPage.setNumberPage(pageIterator);
	    		newPage.calculFirstLine();
			}

		}
       
       allComputers.stream().forEach(computer->allComputersDTO.add(ComputerDtoMapper.convertToComputerDTO(computer)));

       request.setAttribute("pageIterator", pageIterator);
       request.setAttribute("computersList", allComputersDTO);
       request.setAttribute("nbComputers", nbComputer);

		
		request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}
	 
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("dans le post : " + request.getParameter("selection"));
		String[] computerIdsAsListString=request.getParameter("selection").split(",");
		
		for(String idString:computerIdsAsListString) {
			computerService.deleteComputer(Long.parseLong(idString));
		}

		doGet(request, response);
	}
	
}
