package com.mini_assignment.user_verification.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalGenderDTO {

	public String gender;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public ExternalGenderDTO(String gender) {
		super();
		this.gender = gender;
	}

	public ExternalGenderDTO() {
		super();
	}
	
	
}
