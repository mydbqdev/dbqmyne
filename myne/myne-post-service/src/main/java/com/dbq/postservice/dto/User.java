package com.dbq.postservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
	
	private String id;
	private String userEmail;
	private String userFirstName;
	private String userLastName;
	private String password;
	private String zipCode;
}
