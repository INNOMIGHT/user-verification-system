package com.mini_assignment.user_verification.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class NationalityResponse {

	private List<CountryDTO> country;

	public List<CountryDTO> getCountry() {
		return country;
	}

	public void setCountry(List<CountryDTO> country) {
		this.country = country;
	}

	public boolean contains(CountryDTO checkedCountry) {
		// TODO Auto-generated method stub
		return country.contains(checkedCountry);
	}


}
