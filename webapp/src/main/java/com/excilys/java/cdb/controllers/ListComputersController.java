package com.excilys.java.cdb.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.java.cdb.dto.ComputerDTO;
import com.excilys.java.cdb.dto.DashboardDTO;
import com.excilys.java.cdb.dto.mappers.ComputerDtoMapper;
import com.excilys.java.cdb.model.Computer;
import com.excilys.java.cdb.model.Page;
import com.excilys.java.cdb.service.ComputerService;

@Controller
@RequestMapping("/ListComputers")
public class ListComputersController {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private int pageNumber, pageDemande;

	
	@Autowired
	public ComputerService computerService;
	
	private static Logger logger = LoggerFactory.getLogger(ListComputersController.class);
	
	@GetMapping
	public ModelAndView getListComputers(DashboardDTO dashboard) throws ServletException, IOException {
		
        ModelAndView mv = new ModelAndView("dashboard");
		List<ComputerDTO>allComputersDTO=new ArrayList<ComputerDTO>();
	    List<Computer> allComputers;
		System.out.println("hello");
		
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
		System.out.println("taille comp :" + allComputers.size());
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
	
	@PostMapping
	public ModelAndView deleteComputer(@RequestParam List<Long> selection) {
		
        ModelAndView mv = new ModelAndView("redirect:ListComputers");

        for (Long computerId : selection) {
            computerService.deleteComputer(computerId);
        }
		logger.info("Delete complete");
		
		return mv;

	}

}
