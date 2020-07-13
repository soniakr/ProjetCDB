package com.excilys.formation.cbd.dto;

public class DashboardDTO {
	
	private String search="";
    private Integer pageIterator = 1;
    private Integer pageSize;
    private String orderBy = "";
    
    public DashboardDTO() {
    	super();
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getPageIterator() {
        return pageIterator;
    }

    public void setPageIterator(Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        this.pageIterator = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        System.out.println(orderBy);
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "DashboardDTO [search=" + search + ", page=" + pageIterator + ", pageSize=" + pageSize + ", orderBy=" + orderBy
                + "]";
    }

}
