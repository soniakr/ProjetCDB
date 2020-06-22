package model;

import java.time.LocalDate;

/**
 * Classe representant l'entité Computer
 * @author sonia
 *
 */

public class Computer {
	
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Long company_id;
	private Company company;
	
	public Computer(Long id, String name) {
		this.id=id;
		this.name=name;
	}
	
	public Computer(String name, LocalDate introd, LocalDate dis, Long comp) {
		this.name=name;
		this.introduced=introd;
		this.discontinued=dis;
		this.company_id=comp;
	}
	
	public Computer(Long id, String name, LocalDate introd, LocalDate dis, Long comp) {
		this.id=id;
		this.name=name;
		this.introduced=introd;
		this.discontinued=dis;
		this.company_id=comp;
		this.company=new Company(comp);
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
	
	public LocalDate getIntroduced() {
		return this.introduced;
	}
	
	public LocalDate getDiscontinued() {
		return this.discontinued;
	}
	
	public void setIntroduced(LocalDate newDate) throws Exception {
		
		if(newDate != null && this.discontinued!=null && newDate.compareTo(this.discontinued) > 0) {
			throw new Exception("Discontinued date must be greater");
		}
		this.introduced = newDate;
	}
	
	public void setDiscontinued(LocalDate newDate) throws Exception {
		if(newDate != null && this.introduced!=null && this.introduced.compareTo(newDate) > 0) {
			throw new Exception("Discontinued date must be greater");
		}
		this.discontinued = newDate;
	}
	
	public Long getIdCompany() {
		return this.company_id;
	}
	
	public void setIdCompany(Long i) {
		this.company_id=i;
	}
	
	public Company getCompany() {
	        return company;
	    }

	public void setCompany(Company company) {
	        this.company = company;
	}
	
	public String toString() {
		return "Computer : " + this.name + " - ID : " + this.id + " - Introduced : " + this.introduced + " - Discontinued : " + this.discontinued + " - Company : " + this.company.getId() + " " + this.company.getName();
	}
}
