package com.excilys.formation.cbd.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cbd.dto.CompanyDTO;
import com.excilys.formation.cbd.model.Company;

public class CompanyMapper {
	
	private static Company newCompagny;

	/**
	 * S'occupe de la convertion du résultat en entité
	 * @param resultSet résultat de la requête
	 * @return une entité Company correspondante
	 */
	public static Company convert(ResultSet resultSet) {
	        try {
	            newCompagny = new Company(resultSet.getLong("id"), resultSet.getString("name"));
	        } catch (SQLException e) {
	            System.err.println("Erreur -> Mapping company");
	        }
	        return newCompagny;
	}


	public static CompanyDTO companyToCompanyDto(Company company) {
		CompanyDTO companyDto= new CompanyDTO();
		
		String id = String.valueOf(company.getId());
		companyDto.setId(id);
		companyDto.setName(company.getName());
		
		return companyDto;
	}


	public static Company toCompany(CompanyDTO company) {
		// TODO Auto-generated method stub
		return null;
	}
}
