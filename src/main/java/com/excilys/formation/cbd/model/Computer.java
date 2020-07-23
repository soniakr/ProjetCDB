package com.excilys.formation.cbd.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe representant l'entité Computer
 * @author sonia
 *
 */

@Entity
@Table(name = "computer")
public class Computer {
	
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
    @Column(name = "introduced")
	private LocalDate introduced;
    
    @Column(name = "discontinued")
	private LocalDate discontinued;
    	
	@ManyToOne
    @JoinColumn(name = "company_id")
	private Company company;
	
	
	private Computer(ComputerBuilder computerBuilder) {
		this.id=computerBuilder.id;
		this.name=computerBuilder.name;
		this.introduced=computerBuilder.introduced;
		this.discontinued=computerBuilder.discontinued;
		this.company=computerBuilder.company;
	}
	
	public Computer() {
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
	
	public Company getCompany() {
	        return company;
	    }

	public void setCompany(Company company) {
	        this.company = company;
	}
	
	public String toString() {
		return "Computer : " + this.name + " - ID : " + this.id + " - Introduced : " + this.introduced + " - Discontinued : " + this.discontinued + " - Company : " + this.company.getId() + " " + this.company.getName();
	}
	
	public boolean equals(Computer comp) {
		
		if (this.getId()==comp.getId() 
				&& this.getName().equals(comp.getName()) 
				&& this.getIntroduced().equals(comp.getIntroduced())
				&& this.getDiscontinued().equals(comp.getDiscontinued())
				&& this.getCompany().getId() == comp.getCompany().getId()) {
			return true;
		}
		return false;
	}
	
	/**
	 * La classe Builder
	 */
	public static class ComputerBuilder{
		private Long id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;
		
		public ComputerBuilder(String name) {
			this.name=name;
		}
		
		public ComputerBuilder initializeWithId(Long id) {
			this.id=id;
			return this;
		}
		
		public ComputerBuilder initializeWithIntroducedDate(LocalDate introduced) {
			this.introduced=introduced;
			return this;
		}
		
		public ComputerBuilder initializeWithDiscontinuedDate(LocalDate discontinued) {
			this.discontinued=discontinued;
			return this;
	    }
		
		public ComputerBuilder initializeWithCompany(Company company) {
			this.company=company;
			return this;
		}
		
		public Computer build() {
			Computer computer = new Computer(this);
			return computer;
		}

		
}
}
