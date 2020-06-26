package com.excilys.formation.cbd.service;

import java.util.List;

import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Page;
import com.excilys.formation.cbd.persistence.CompanyDAO;

/**
 * Classe de Service qui s'occupe du lien entre le client et le DAO
 * @author sonia
 *
 */
public class CompanyService {
	
private static CompanyService companyService;
	
    private CompanyDAO companyDAO = CompanyDAO.getInstance();

    //synchronized?
    public static CompanyService getInstance() {
        if (companyService == null) {
            companyService = new CompanyService();
        }
        return companyService;
    }


	public List<Company> getAll() {
        return companyDAO.getAll();

	}

	public List<Company> getByPage(Page page) {
        return companyDAO.getByPage(page);

	}
	
	public int countAll() {
		return companyDAO.countAll();
	}
}
