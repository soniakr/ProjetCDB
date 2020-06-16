package model;

import java.sql.Date;

/**
 * Classe representant l'entité Computer
 * @author sonia
 *
 */

public class Computer {
	
	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Long company_id;
	
	public Computer(long id, String name) {
		this.id=id;
		this.name=name;
	}
	
	public Computer(String name, Date introd, Date dis, Long comp) {
		this.name=name;
		this.introduced=introd;
		this.discontinued=dis;
		this.company_id=comp;
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
	
	public Date getIntroduced() {
		return this.introduced;
	}
	
	public Date getDiscontinued() {
		return this.discontinued;
	}
	
	public void setIntroduced(Date d) {
		this.introduced=d;
	}
	
	public void setDiscontinued(Date d) {
		this.discontinued=d;
	}
	
	public Long getIdCompany() {
		return this.company_id;
	}
	
	public void setIdCompany(Long i) {
		this.company_id=i;
	}
	
	public String toString() {
		return "Computer : " + this.name + " - ID : " + this.id + " - Introduced : " + this.introduced + " - Discontinued : " + this.discontinued + " - Id Company : " + this.company_id;
	}
}
