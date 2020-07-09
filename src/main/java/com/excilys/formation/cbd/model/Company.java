package com.excilys.formation.cbd.model;

import com.excilys.formation.cbd.dto.CompanyDTO;
import com.excilys.formation.cbd.dto.CompanyDTO.CompanyDTOBuilder;

/**
 * Classe representant l'entité Company
 * @author sonia
 *
 */
public class Company {
	
	private long id;
	private String name;
	
	public Company(long id, String name) {
		this.id=id;
		this.name=name;
	}
	
	public Company(Long id) {
		this.id=id;
	}
	
	private Company(CompanyBuilder companyBuilder) {
		this.id=companyBuilder.id;
		this.name=companyBuilder.name;
	}

	public Company() {
		super();
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String n) {
		this.name=n;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long i) {
		this.id=i;
	}
	
	public String toString() {
		return "Company : " + this.name + " - id : " + this.id;
	}
	
	/**
	 * Classe Builder 
	 *
	 */
	
	public static class CompanyBuilder{
		private Long id;
		private String name;

		
		public CompanyBuilder(String name) {
			this.name=name;
		}
		
		public CompanyBuilder initializeWithId(Long id) {
			this.id=id;
			return this;
		}
		
		
		public Company build() {
			Company company = new Company(this);
			return company;
		}			
}

}