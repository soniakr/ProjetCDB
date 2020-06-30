package com.excilys.formation.cbd.model;

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

}