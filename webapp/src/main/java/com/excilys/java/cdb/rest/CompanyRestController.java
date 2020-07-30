package com.excilys.java.cdb.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.excilys.java.cdb.dto.CompanyDTO;
import com.excilys.java.cdb.dto.mappers.CompanyDtoMapper;
import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.service.CompanyService;

@RestController
@RequestMapping("ListCompanies")
public class CompanyRestController {
	
	 @Autowired
	    private CompanyService companyService;

	    @GetMapping(value = {"", "/"})
	    public List<CompanyDTO> listCompanies() {
	        List<Company> allCompanies = companyService.getAll();
	        return allCompanies.stream().map(c -> CompanyDtoMapper.companyToCompanyDto(c)).collect(Collectors.toList());
	    }
	    
	    @GetMapping("/{id}")
	    public CompanyDTO getCompany (@PathVariable Long id) {
	        try {
	            return CompanyDtoMapper.companyToCompanyDto(companyService.getById(id));
	        } catch (Exception e) {
	            throw new ResponseStatusException (HttpStatus.NOT_FOUND, "The company is not found is the database");
	        }
	    }

	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	        try {
	            companyService.deleteCompany(id);
	        } catch (Exception e) {
	            throw new ResponseStatusException (HttpStatus.NOT_FOUND, "The Company is not found is the database");
	        }
	    }

}
