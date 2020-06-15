package service;

import java.util.List;

import model.Compagny;
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

    
    public List<Compagny> getAll() {
        return compagnyDAO.getAll();
    }

}
