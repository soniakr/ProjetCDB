package com.excilys.java.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.java.cdb.model.Computer;
import com.excilys.java.cdb.model.Page;
import com.excilys.java.cdb.persistence.ComputerDAO;

/**
 * Classe de Service qui s'occupe du lien entre le client et le DAO
 * @author sonia
 *
 */
@Service
public class ComputerService {
		
	@Autowired
    private ComputerDAO computerDAO;
	
	public List<Computer> getAllByName(Page newPage, String name, String orderBy) {
        return computerDAO.getAllByName(newPage, name, orderBy);
    }
    
    public List<Computer> getAll() {
        return computerDAO.getAll();
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
