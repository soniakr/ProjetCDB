package com.excilys.formation.cbd.dto.mappers;

import com.excilys.formation.cbd.dto.CompanyDTO;
import com.excilys.formation.cbd.model.Company;

public class CompanyDtoMapper {
	
	
	public static CompanyDTO companyToCompanyDto(Company company) {
		CompanyDTO companyDto= new CompanyDTO();
		
		String id = String.valueOf(company.getId());
		companyDto.setidCompany(id);
		companyDto.setName(company.getName());
		
		return companyDto;
	}


	public static Company toCompany(CompanyDTO companyDto) {
		Company company = new Company();
		company.setId(Long.parseLong(companyDto.getidCompany()));
		company.setName(companyDto.getName());
		
		return company;
	}
}
