package service;

import java.util.List;

import model.Company;
import persistence.CompagnyDAO;

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

}
