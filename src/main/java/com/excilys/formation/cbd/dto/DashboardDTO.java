package com.excilys.formation.cbd.dto;

public class DashboardDTO {
	
	private String search="";
    private Integer pageIterator = 1;
    private Integer taillePage=10;
    private String orderby = "";
    
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

    public Integer getTaillePage() {
        return taillePage;
    }

    public void setTaillePage(Integer pageSize) {
        this.taillePage = pageSize;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderBy) {
        this.orderby = orderBy;
    }

    @Override
    public String toString() {
        return "DashboardDTO [search=" + search + ", page=" + pageIterator + ", pageSize=" + taillePage + ", orderBy=" + orderby
                + "]";
    }

}
