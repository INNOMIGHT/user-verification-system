package com.mini_assignment.user_verification.dto;

public class UserGetQuery {
	
	private String sortType;
    private String sortOrder;
    private Integer limit;
    private Integer offset;
    
    
    
	public UserGetQuery(String sortType, String sortOrder, Integer limit, Integer offset) {
		super();
		this.sortType = sortType;
		this.sortOrder = sortOrder;
		this.limit = limit;
		this.offset = offset;
	}
	
	
	public UserGetQuery() {
		super();
	}


	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

    
    
}
