package com.excilys.formation.cbd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Classe representant l'entité Company
 * @author sonia
 *
 */

@Entity
@Table(name = "company")
public class Company {
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
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
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long i) {
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