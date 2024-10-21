package com.dbq.userservice.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dbq.userservice.enums.Active;
import com.dbq.userservice.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("myne-users-profile")
public class User {
	@Id
	private  String id;
	private String userEmail;
	private String userFirstName;
	private String userLastName;
	private  String password;
	private String zipCode;
	private Set<Role> roles = new HashSet<>();
	private  Active active;
	private UserDetails userDetails;

    
    
}
