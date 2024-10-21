package com.dbq.userservice.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetails {
    private String userEmail;
    private String userFirstName;
	private String userLastName;
	private String zipCode;
	private String phoneNumber;
	private String country;
	private String city;
	private String address;
	private String aboutMe;
	private String profilePicture;
}
