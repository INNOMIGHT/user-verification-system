package com.mini_assignment.user_verification.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDTO {

	@JsonProperty("country_id")
    private String countryId;

	public CountryDTO(String nationality) {
		this.countryId = nationality;
	}



	@Override
	public int hashCode() {
		return Objects.hash(countryId);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryDTO other = (CountryDTO) obj;
		return Objects.equals(countryId, other.countryId);
	}



	public CountryDTO() {
		super();
	}



	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}



	public static CountryDTO from(String nationality) {
		// TODO Auto-generated method stub
		return new CountryDTO(nationality);
	}


}

