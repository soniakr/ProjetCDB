package com.excilys.formation.cbd.service;

import java.util.List;

import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Page;
import com.excilys.formation.cbd.persistence.ComputerDAO;

/**
 * Classe de Service qui s'occupe du lien entre le client et le DAO
 * @author sonia
 *
 */
public class ComputerService {
	
private static ComputerService computerService;
	
    private ComputerDAO computerDAO = ComputerDAO.getInstance();

    //synchronized?
    public static ComputerService getInstance() {
        if (computerService == null) {
            computerService = new ComputerService();
        }
        return computerService;
    }
    
    public List<Computer> getAll() {
        return computerDAO.getAll();
    }
    
    public List<Computer> getAllByName(Page newPage, String name, String orderBy) {
        return computerDAO.getAllByName(newPage, name, orderBy);
    }
    
    public Computer getById(Long id) {
    	return computerDAO.findById(id);
    }
    
	public List<Computer> getByPage(Page page, String orderBy) {
        return computerDAO.getByPage(page, orderBy);

	}
	
	public int countAll(String s) {
		return computerDAO.countAll(s);
	}
    
    public void addComputer(Computer comp) {
    	computerDAO.insert(comp);
    }
    
    public void updateComputer(Computer comp) {
    	computerDAO.update(comp);
    }
    
    public void deleteComputer(Long id) {
    	computerDAO.delete(id);
    }

}
