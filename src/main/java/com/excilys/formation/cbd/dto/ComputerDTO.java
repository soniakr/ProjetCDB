package com.excilys.formation.cbd.dto;

public class ComputerDTO {
	
	private String idComputer;
    private String name;
    private String introducedDate;
    private String discontinuedDate;
    private CompanyDTO company;
    

    public ComputerDTO(String name, String introduced, String discontinued, CompanyDTO company) {
		this.name=name;
		this.introducedDate=introduced;
		this.discontinuedDate=discontinued;
		this.company=company;
	}

	public ComputerDTO() {
		super();
	}

	public String getId() {
        return idComputer;
    }

    public void setId(String idComputer) {
        this.idComputer = idComputer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introducedDate;
    }

    public void setIntroduced(String introducedDate) {
        this.introducedDate = introducedDate;
    }

    public String getDiscontinued() {
        return discontinuedDate;
    }

    public void setDiscontinued(String discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO companyDTO) {
        this.company = companyDTO;
    }
    
    
}
