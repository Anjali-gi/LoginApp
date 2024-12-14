package com.userlogin.entity;


import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;


@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private String gender;
	private String country;
	private String address;
	private String phoneNumber;
	private String dob;

	public User() {
	}

	// Constructor with all fields
	public User(String firstName, String lastName, String username, String password, String email, String profile,
				String gender, String country, String address, String phoneNumber, String dob) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		//this.profile = profile;
		this.gender = gender;
		this.country = country;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.dob = dob;
	}

	// Getters and Setters for all fields

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setImgAddress(String s) {
	}
}