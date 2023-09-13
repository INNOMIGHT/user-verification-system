package com.mini_assignment.user_verification.dto;

import java.util.List;

import com.mini_assignment.user_verification.entity.User;

public class UsersGetResponse {
	
	List<User> usersList;
	PageInfo pageInfo;
	
	public UsersGetResponse() {
		super();
	}
	public UsersGetResponse(List<User> usersList, PageInfo pageInfo) {
		super();
		this.usersList = usersList;
		this.pageInfo = pageInfo;
	}
	public List<User> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	
	
}
