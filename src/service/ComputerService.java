package service;

import java.util.List;

import model.Computer;
import model.Page;
import persistence.ComputerDAO;

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
    
    public Computer getById(Long id) {
    	return computerDAO.findById(id);
    }
    
	public List<Computer> getByPage(Page page) {
        return computerDAO.getByPage(page);

	}
	
	public int countAll() {
		return computerDAO.countAll();
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
