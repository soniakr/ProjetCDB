package com.excilys.java.cdb.dto.mappers;

import com.excilys.java.cdb.dto.CompanyDTO;
import com.excilys.java.cdb.model.Company;

public class CompanyDtoMapper {
	
	
	public static CompanyDTO companyToCompanyDto(Company company) {
		if(company==null) {
			return null;
		}
		CompanyDTO companyDto= new CompanyDTO();
		
		String id = String.valueOf(company.getId());
		companyDto.setidCompany(id);
		companyDto.setNameCompany(company.getName());
		
		return companyDto;
	}


	public static Company toCompany(CompanyDTO companyDto) {
		Company company = new Company();
		company.setId(Long.parseLong(companyDto.getidCompany()));
		company.setName(companyDto.getNameCompany());
		
		return company;
	}
}
