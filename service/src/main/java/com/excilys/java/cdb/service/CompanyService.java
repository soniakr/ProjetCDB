package com.excilys.java.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Page;
import com.excilys.java.cdb.persistence.CompanyDAO;

/**
 * Classe de Service qui s'occupe du lien entre le client et le DAO
 * @author sonia
 *
 */
@Service
public class CompanyService {
		
	@Autowired
    private CompanyDAO companyDAO;

 /*   //synchronized?
    public static CompanyService getInstance() {
        if (companyService == null) {
            companyService = new CompanyService();
        }
        return companyService;
    }
*/

	public List<Company> getAll() {
        return companyDAO.getAll();

	}

	public List<Company> getByPage(Page page) {
        return companyDAO.getByPage(page);

	}
	
	public int countAll() {
		return companyDAO.countAll();
	}


	public void deleteCompany(Long idCompany) {
		companyDAO.deleteCompany(idCompany);		
	}


	public Company getById(Long idCompany) {
		return companyDAO.findById(idCompany);
	}
	
}