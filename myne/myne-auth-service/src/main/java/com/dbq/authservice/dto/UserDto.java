package com.dbq.authservice.dto;

import com.dbq.authservice.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String userEmail;
    private String password;
    private Role role;
}
