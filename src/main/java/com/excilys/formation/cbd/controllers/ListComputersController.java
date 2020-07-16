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
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cbd.dto.ComputerDTO;
import com.excilys.formation.cbd.dto.DashboardDTO;
import com.excilys.formation.cbd.dto.mappers.ComputerDtoMapper;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Page;
import com.excilys.formation.cbd.service.ComputerService;

@Controller
public class ListComputersController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private int pageNumber, pageDemande;

	
	@Autowired
	public ComputerService computerService;
	
	private static Logger logger = LoggerFactory.getLogger(ListComputersController.class);
	
	@GetMapping("/ListComputers")
	public ModelAndView getListComputers(DashboardDTO dashboard) throws ServletException, IOException {
		
        ModelAndView mv = new ModelAndView("dashboard");
		List<ComputerDTO>allComputersDTO=new ArrayList<ComputerDTO>();
	    List<Computer> allComputers;
		
		int nbComputer;
		Page newPage = new Page();
	
		newPage.setMaxLines(dashboard.getTaillePage());
		
	    nbComputer = computerService.countAll(dashboard.getSearch());
		int maxPages=newPage.getTotalPages(nbComputer);
		
		if(dashboard.getPageIterator()!=0 ) {

			pageDemande=dashboard.getPageIterator();
			if(pageDemande>0 && pageDemande<=maxPages) {
				pageNumber=dashboard.getPageIterator();
	    		newPage.setNumberPage(pageNumber);
	    		newPage.calculFirstLine();
			}
		}
		
		if(dashboard.getSearch() != null && !dashboard.getSearch().equals("")) {
			allComputers=computerService.getAllByName(newPage,dashboard.getSearch(),dashboard.getOrderby());
		} else {

			allComputers = computerService.getByPage(newPage,dashboard.getOrderby());
		}
		
       allComputers.stream().forEach(computer->allComputersDTO.add(ComputerDtoMapper.convertToComputerDTO(computer)));
       
       mv.getModel().put("maxPages", maxPages);
       mv.getModel().put("pageIterator", pageNumber);
       mv.getModel().put("computersList", allComputersDTO);
       mv.getModel().put("nbComputers", nbComputer);
       mv.getModel().put("search", dashboard.getSearch());
       mv.getModel().put("orderby", dashboard.getOrderby());
       mv.getModel().put("taillePage", dashboard.getTaillePage());
       
       return mv;

	}
	
	@PostMapping("/ListComputers")
	public ModelAndView deleteComputer(@RequestParam List<Long> selection) {
		
        ModelAndView mv = new ModelAndView("redirect:ListComputers");

        for (Long computerId : selection) {
            computerService.deleteComputer(computerId);
        }
		logger.info("Delete complete");
		
		return mv;

	}

}
