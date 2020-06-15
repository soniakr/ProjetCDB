package model;

import java.util.Date;

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
	private long compagny_id;
	
	public Computer(String name, Date introd, Date dis, long comp) {
		this.name=name;
		this.introduced=introd;
		this.discontinued=dis;
		this.compagny_id=comp;
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
	
	public long getIdCompagny() {
		return this.compagny_id;
	}
	
	public void setIdCompagny(long i) {
		this.compagny_id=i;
	}
	
	public String toString() {
		return "Computer :" + this.name + " - id : " + this.id + " Introduced " + this.introduced + " - Discontinued : " + this.discontinued + " - Id Compagny : " + this.compagny_id;
	}
}
