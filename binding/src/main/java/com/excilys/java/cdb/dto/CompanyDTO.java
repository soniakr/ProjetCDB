package com.excilys.java.cdb.dto;

public class CompanyDTO {
	
	 	private String idCompany;
	    private String nameCompany;
	    
		private CompanyDTO(CompanyDTOBuilder companyBuilder) {
			this.idCompany=companyBuilder.idCompany;
			this.nameCompany=companyBuilder.name;
		}
	    
	    public CompanyDTO(long idCompanyCompany) {
			this.idCompany=String.valueOf(idCompany);
	    }
	    
	    public CompanyDTO() {
			super();
	    }
	    
	    public String getidCompany() {
	        return idCompany;
	    }

	    public void setidCompany(String idCompany) {
	        this.idCompany = idCompany;
	    }

	    public String getNameCompany() {
	        return nameCompany;
	    }

	    public void setNameCompany(String name) {
	        this.nameCompany = name;
	    }
	    
		public String toString() {
			return "Company : " + this.nameCompany + " - idCompany : " + this.idCompany;
		}
		
		/**
		 * Builder Company DTO class
		 *
		 */
		public static class CompanyDTOBuilder{
			private String idCompany;
			private String name;

			
			public CompanyDTOBuilder(String name) {
				this.name=name;
			}
			
			public CompanyDTOBuilder initializeWithidCompany(String idCompany) {
				this.idCompany=idCompany;
				return this;
			}
			
			
			public CompanyDTO build() {
				CompanyDTO company = new CompanyDTO(this);
				return company;
			}			
	}
}
