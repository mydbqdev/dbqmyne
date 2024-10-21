package com.dbq.authservice.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String userEmail;
    private String password;
}
