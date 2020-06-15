package model;

/**
 * Classe representant l'entité Compagny
 * @author sonia
 *
 */
public class Compagny {
	
	private long id;
	private String name;
	
	public Compagny(long id, String name) {
		this.id=id;
		this.name=name;
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
		return "Compagny : " + this.name + " - id : " + this.id;
	}

}
