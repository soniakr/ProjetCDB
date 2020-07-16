package com.excilys.formation.cbd.dto;

public class ComputerDTO {
	
	private String idComputer;
    private String name;
    private String introduced;
    private String discontinued;
    private CompanyDTO company;
    
	private ComputerDTO(ComputerDTOBuilder computerBuilder) {
		this.idComputer=computerBuilder.id;
		this.name=computerBuilder.name;
		this.introduced=computerBuilder.introduced;
		this.discontinued=computerBuilder.discontinued;
		this.company=computerBuilder.company;
	}
	
	
    public ComputerDTO(String name, String introduced, String discontinued, CompanyDTO company) {
		this.name=name;
		this.introduced=introduced;
		this.discontinued=discontinued;
		this.company=company;
	}

	public ComputerDTO() {
		super();
	}

	public String getIdComputer() {
        return idComputer;
    }

    public void setIdComputer(String idComputer) {
        this.idComputer = idComputer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introducedDate) {
        this.introduced = introducedDate;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinuedDate) {
        this.discontinued = discontinuedDate;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO companyDTO) {
        this.company = companyDTO;
    }
	
	/**
	 * La classe Builder
	 */
	public static class ComputerDTOBuilder{
		private String id;
		private String name;
		private String introduced;
		private String discontinued;
		private CompanyDTO company;
		
		public ComputerDTOBuilder(String name) {
			this.name=name;
		}
		
		public ComputerDTOBuilder initializeWithId(String id) {
			this.id=id;
			return this;
		}
		
		public ComputerDTOBuilder initializeWithIntroducedDate(String introduced) {
			this.introduced=introduced;
			return this;
		}
		
		public ComputerDTOBuilder initializeWithDiscontinuedDate(String discontinued) {
			this.discontinued=discontinued;
			return this;
	    }
		
		public ComputerDTOBuilder initializeWithCompany(CompanyDTO company) {
			this.company=company;
			return this;
		}
		
		public ComputerDTO build() {
			ComputerDTO computer = new ComputerDTO(this);
			return computer;
		}	
}
    
}
