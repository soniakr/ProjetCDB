package service;

import java.util.List;

import model.Company;
import model.Page;
import persistence.CompagnyDAO;

/**
 * Classe de Service qui s'occupe du lien entre le client et le DAO
 * @author sonia
 *
 */
public class CompagnyService {
	
private static CompagnyService companyService;
	
    private CompagnyDAO compagnyDAO = CompagnyDAO.getInstance();

    //synchronized?
    public static CompagnyService getInstance() {
        if (companyService == null) {
            companyService = new CompagnyService();
        }
        return companyService;
    }

    
    public List<Company> getAll() {
        return compagnyDAO.getAll();
    }


	public List<Company> getByPage(Page page) {
        return compagnyDAO.getByPage(page);

	}
	
	public int countAll() {
		return compagnyDAO.countAll();
	}
}
