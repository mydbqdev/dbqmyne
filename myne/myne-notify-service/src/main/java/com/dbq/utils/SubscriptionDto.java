package com.dbq.utils;

public class SubscriptionDto {
	private String userId;
	private String registryToken;
	private Long zipCode;
	public String getRegistryToken() {
		return registryToken;
	}
	public void setRegistryToken(String registryToken) {
		this.registryToken = registryToken;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getZipCode() {
		return zipCode;
	}
	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}
	
	
}
