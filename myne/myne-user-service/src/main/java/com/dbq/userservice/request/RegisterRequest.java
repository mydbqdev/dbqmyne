package com.dbq.userservice.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegisterRequest {
	@Email(message = "Email should be valid")
    private String userEmail;
	
    @Pattern(regexp = 
    		"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+=-]{8,}$",
    		// "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must be at least 8 characters and contain at least one letter and one number")
    @NotNull(message = "Password is required")
    private String password;
    
    @NotNull(message = "First name is required")
    private String userFirstName;
	
    @NotNull(message = "Last Name is required")
    private String userLastName;
    
    @NotNull(message = "Zip code is required")
    private String zipCode;
}
