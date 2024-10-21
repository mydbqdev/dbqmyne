package com.dbq.authservice.service;

import com.dbq.authservice.client.UserServiceClient;
import com.dbq.authservice.dto.RegisterDto;
import com.dbq.authservice.dto.TokenDto;
import com.dbq.authservice.exc.WrongCredentialsException;
import com.dbq.authservice.request.LoginRequest;
import com.dbq.authservice.request.RegisterRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;

    public TokenDto login(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getPassword()));
        if (authenticate.isAuthenticated())
            return TokenDto
                    .builder()
                    .token(jwtService.generateToken(request.getUserEmail()))
                    .build();
        else throw new WrongCredentialsException("Wrong credentials");
    }

    public ResponseEntity<Object> register(RegisterRequest request) {
        return userServiceClient.save(request);
    }
}
