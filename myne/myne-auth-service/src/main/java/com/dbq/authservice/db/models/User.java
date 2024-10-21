package com.dbq.authservice.db.models;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "myne-users-profile")
public class User {
	@Id
	private String id;

	@NotBlank
	@Size(max = 120)
	@Email
	private String userEmail;
	
	@NotBlank
	@Size(max = 120)
	@Email
	private String userFirstName;
	
	@NotBlank
	@Size(max = 120)
	@Email
	private String userLastName;

	@NotBlank
	@Size(max = 120)
	private String password;

	@NotBlank
	@Size(max = 10)
	private String zipCode;

	@DBRef
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String id, @NotBlank @Size(max = 120) @Email String userEmail,
			@NotBlank @Size(max = 120) @Email String userFirstName,
			@NotBlank @Size(max = 120) @Email String userLastName, @NotBlank @Size(max = 120) String password,
			@NotBlank @Size(max = 120) String zipCode, Set<Role> roles) {
		super();
		this.id = id;
		this.userEmail = userEmail;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.password = password;
		this.zipCode = zipCode;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	
	
}
