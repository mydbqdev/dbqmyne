package com.dbq.authservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbq.authservice.dto.TokenDto;
import com.dbq.authservice.request.LoginRequest;
import com.dbq.authservice.request.RegisterRequest;
import com.dbq.authservice.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    
    @PostMapping("/refreshToken")
    public ResponseEntity<Object> renewToken(HttpServletRequest request, HttpServletResponse response) {

    	String userToken = request.getHeader("Authorization");
    	if (userToken == null) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
		} else {
			userToken = userToken.replace("Bearer ", "");
		}	
    	
        return ResponseEntity.ok(authService.refreshToken(userToken));
    }
}
