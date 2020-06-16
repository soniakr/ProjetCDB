package service;

import java.util.List;

import model.Computer;
import persistence.ComputerDAO;

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
    
    public Computer getById(Long id) {
    	return computerDAO.findById(id);
    }
    
    public void addComputer(Computer comp) {
    	computerDAO.insert(comp);
    }
    
    public void updateComputer(Computer comp) {
    	computerDAO.update(comp);
    }
    
    public void deleteCompter(Long id) {
    	computerDAO.delete(id);
    }

}
