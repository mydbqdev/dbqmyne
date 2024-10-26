package com.dbq.userservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String userEmail;
    private String userFirstName;
	private String userLastName;
	private String phoneNumber;
	private String country;
	private String city;
	private String address;
	private String zipCode;
	private String aboutMe;
	private String profilePicture;
}
