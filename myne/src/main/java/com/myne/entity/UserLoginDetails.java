package com.myne.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name="user_login")
public class UserLoginDetails{
	
	@Id
	@Column(name="user_email")
	private String userEmail;
	@Column(name="login_password")
	@ColumnTransformer(read ="AES_DECRYPT(login_password, 'myne@2024')",write = "AES_ENCRYPT(?, 'myne@2024')")
	private String loginPassword;
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
	
	
}