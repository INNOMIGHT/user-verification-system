package com.mini_assignment.user_verification.dto;

public class PageInfo {
	
	boolean hasNextPage;
	boolean hasPreviousPage;
	long total;
	
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long size) {
		this.total = size;
	}
	
	

}
