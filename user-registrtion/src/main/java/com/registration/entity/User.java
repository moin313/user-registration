package com.registration.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email_id", unique = true)
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name = "phone_number", unique = true)
	private String phoneNumber;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "roles")
	private String roles;
	
	@Column(name = "created_on")
	@CreationTimestamp
	private LocalDate dateOfCreation;

	@Column(name = "updated_on")
	@UpdateTimestamp
	private LocalDate dateOfUpdate;
	
	@Column(name = "pwd_updated_on")
	@CreationTimestamp	
	private LocalDate pwdUpdatedOn;

	
	public User(String firstName, String lastName, String phoneNumber, String status, String password, String email, String roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = phoneNumber;
		this.status = status;
		this.password = password;
		this.email = email;
		this.roles = roles;
		this.dateOfUpdate = LocalDate.now();
	}
	
	public User(String password, String email, String roles, int userId) {
		this.password = password;
		this.email = email;
		this.roles = roles;
		this.userId = userId;
	}
}
