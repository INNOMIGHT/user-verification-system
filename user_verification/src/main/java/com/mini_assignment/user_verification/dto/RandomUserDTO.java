package com.mini_assignment.user_verification.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomUserDTO {
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        @JsonProperty("name")
        private Name name;
        @JsonProperty("gender")
        private String gender;
        @JsonProperty("dob")
        private Dob dob; // Use a separate DTO class for "dob"
        @JsonProperty("nat")
        private String nationality;

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Dob getDob() {
            return dob;
        }

        public void setDob(Dob dob) {
            this.dob = dob;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }
        
        public String getFullName() {
            return name.getFirstName() + " " + name.getLastName();
        }

        // Custom getter method to parse age as an integer
        public int getAge() {
            return dob.getAge();
        }

        // Custom getter method to parse dob as a Date object
        public Date getDobAsDate() throws ParseException {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return dateFormat.parse(dob.getDate());
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dob {
        @JsonProperty("date")
        private String date;
        @JsonProperty("age")
        private int age;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        @JsonProperty("first")
        private String firstName;
        @JsonProperty("last")
        private String lastName;
        @JsonProperty("title")
        private String title;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

