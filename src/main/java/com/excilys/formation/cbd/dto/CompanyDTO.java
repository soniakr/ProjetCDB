package com.excilys.formation.cbd.dto;

public class CompanyDTO {
	
	 	private String idCompany;
	    private String name;
	    
		private CompanyDTO(CompanyDTOBuilder companyBuilder) {
			this.idCompany=companyBuilder.idCompany;
			this.name=companyBuilder.name;
		}
	    
	    public CompanyDTO(long idCompanyCompany) {
			this.idCompany=String.valueOf(idCompanyCompany);
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

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
	    
		public String toString() {
			return "Company : " + this.name + " - idCompany : " + this.idCompany;
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
