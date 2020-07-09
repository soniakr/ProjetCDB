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
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.cbd.dto.ComputerDTO;
import com.excilys.formation.cbd.dto.mappers.ComputerDtoMapper;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Page;
import com.excilys.formation.cbd.service.ComputerService;

@WebServlet(urlPatterns = "/ListComputers")
@Controller
public class ListComputerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private int pageIterator, pageDemande;
	private int taillePage=10;
	private String orderBy="id";
	
	@Autowired
	public ComputerService computerService;
	
	private static Logger logger = LoggerFactory.getLogger(ListComputerServlet.class);
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
    	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

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
		
		if(request.getParameter("orderby")!=null && !request.getParameter("orderby").equals("")) {
			orderBy=request.getParameter("orderby");
			System.out.println("order by :" + orderBy);
		}
		String toSearch=null;
		if(request.getParameter("search")!=null && !request.getParameter("search").equals("")) {
			toSearch=request.getParameter("search");
		}
		
	    nbComputer = computerService.countAll(toSearch);
		int maxPages=newPage.getTotalPages(nbComputer);
		request.setAttribute("maxPages", maxPages);
		
		if(request.getParameter("pageIterator")!=null && !request.getParameter("pageIterator").equals("")) {
			pageDemande=Integer.parseInt(request.getParameter("pageIterator"));
			if(pageDemande>0 && pageDemande<=maxPages) {
				pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
	    		newPage.setNumberPage(pageIterator);
	    		newPage.calculFirstLine();
			}
		}
		
		if(toSearch != null ) {
			System.out.println("iciii");
			allComputers=computerService.getAllByName(newPage,toSearch,orderBy);
		} else {
		    allComputers = computerService.getByPage(newPage,orderBy);
		}
       
       allComputers.stream().forEach(computer->allComputersDTO.add(ComputerDtoMapper.convertToComputerDTO(computer)));

       request.setAttribute("pageIterator", pageIterator);
       request.setAttribute("computersList", allComputersDTO);
       request.setAttribute("nbComputers", nbComputer);
       request.setAttribute("search", toSearch);
       request.setAttribute("orderby", orderBy);
       request.setAttribute("taillePage", taillePage);
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
