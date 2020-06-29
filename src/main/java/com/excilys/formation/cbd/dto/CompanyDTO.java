package com.excilys.formation.cbd.dto;

public class CompanyDTO {
	
	 	private String id;
	    private String name;
	    
	    public CompanyDTO(long id) {
			this.id=String.valueOf(id);
	    }
	    
	    public CompanyDTO() {
			super();
	    }
	    
	    public String getId() {
	        return id;
	    }

	    public void setId(String idCompany) {
	        this.id = idCompany;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
	    
		public String toString() {
			return "Company : " + this.name + " - id : " + this.id;
		}
}
