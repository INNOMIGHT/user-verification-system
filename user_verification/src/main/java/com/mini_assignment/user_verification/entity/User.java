package com.mini_assignment.user_verification.entity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

import com.mini_assignment.user_verification.dto.UserGetQuery;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String name;
    private int age;
    private String gender;
    private Date dob;
    private String nationality;
    private String verification_status;
    private LocalDateTime date_created;
    private LocalDateTime date_modified;

    public User() {

    }

	public User(String name, int age, String gender, Date dob, String nationality,
			String verification_status) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.dob = dob;
		this.nationality = nationality;
		this.verification_status = verification_status;
		this.date_created = LocalDateTime.now();
		this.date_modified = LocalDateTime.now();
	}


	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getVerification_status() {
		return verification_status;
	}
	public void setVerification_status(String verification_status) {
		this.verification_status = verification_status;
	}
	public LocalDateTime getDate_created() {
		return date_created;
	}
	public void setDate_created(LocalDateTime date_created) {
		this.date_created = date_created;
	}
	public LocalDateTime getDate_modified() {
		return date_modified;
	}
	public void setDate_modified(LocalDateTime date_modified) {
		this.date_modified = date_modified;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return age == user.age && user_id.equals(user.user_id) && name.equals(user.name) && gender.equals(user.gender) && dob.equals(user.dob) && nationality.equals(user.nationality) && verification_status.equals(user.verification_status) && date_created.equals(user.date_created) && date_modified.equals(user.date_modified);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user_id, name, age, gender, dob, nationality, verification_status, date_created, date_modified);
	}

	@Override
	public String toString() {
		return "User{" +
				"user_id=" + user_id +
				", name='" + name + '\'' +
				", age=" + age +
				", gender='" + gender + '\'' +
				", dob=" + dob +
				", nationality='" + nationality + '\'' +
				", verification_status='" + verification_status + '\'' +
				", date_created=" + date_created +
				", date_modified=" + date_modified +
				'}';
	}

	public static final class AgeComparator implements Comparator<User> {
		private final UserGetQuery userGetQuery;

		public AgeComparator(UserGetQuery userGetQuery) {
			this.userGetQuery = userGetQuery;
		}

		@Override
		public int compare(User o1, User o2) {
			if (userGetQuery.getSortOrder().equals("even")) {
				// TODO Auto-generated method stub

				if (o1.getAge() % 2 == 0 && o2.getAge() % 2 != 0) {
					return -1;
				}
				else if (o1.getAge() % 2 != 0 && o2.getAge() % 2 == 0){
					return 1;
				}
				return 0;
			}
			else {
				// TODO Auto-generated method stub

				if (o1.getAge() % 2 == 0 && o2.getAge() % 2 != 0) {
					return 1;
				}
				else if (o1.getAge() % 2 != 0 && o2.getAge() % 2 == 0){
					return -1;
				}
			}
			return 0;
		}
	}

	public static final class NameComparator implements Comparator<User> {
		private final UserGetQuery userGetQuery;

		public NameComparator(UserGetQuery userGetQuery) {
			this.userGetQuery = userGetQuery;
		}

		@Override
		public int compare(User o1, User o2) {
			// TODO Auto-generated method stub
			if(userGetQuery.getSortOrder().equals("even")) {
				if(o1.getName().length() %2 == 0 && o2.getName().length() %2 == 0) {
					return 0;
				}
				else if (o1.getName().length() % 2 == 0 && o2.getName().length() % 2 != 0) {
					return -1;
				}
				else if (o1.getName().length() % 2 != 0 && o2.getName().length() % 2 == 0){
					return 1;
				}
				return 0;
			}
			else {
				if(o1.getName().length() %2 == 0 && o2.getName().length() %2 == 0) {
					return 0;
				}
				else if (o1.getName().length() % 2 == 0 && o2.getName().length() % 2 != 0) {
					return 1;
				}
				else if (o1.getName().length() % 2 != 0 && o2.getName().length() % 2 == 0){
					return -1;
				}
				return 0;
			}
		}
	}



}

