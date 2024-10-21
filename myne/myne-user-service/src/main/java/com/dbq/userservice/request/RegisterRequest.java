package com.dbq.userservice.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegisterRequest {
	@Email(message = "userEmail should be valid")
    private String userEmail;
	
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must be at least 8 characters and contain at least one letter and one number")
    @NotNull(message = "Password is required")
    private String password;
    
    @NotNull(message = "userFirstName should be valid")
    private String userFirstName;
	
    @NotNull(message = "userLastName should be valid")
    private String userLastName;
    
    @NotNull(message = "ZipCode is required")
    private String zipCode;
}
