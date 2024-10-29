package com.dbq.authservice.service;

import com.dbq.authservice.client.UserServiceClient;
import com.dbq.authservice.db.repository.UserRepository;
import com.dbq.authservice.dto.RegisterDto;
import com.dbq.authservice.dto.TokenDto;
import com.dbq.authservice.exc.WrongCredentialsException;
import com.dbq.authservice.request.LoginRequest;
import com.dbq.authservice.request.RegisterRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	public static final String REFRESH_SECRET = "fe23401e613295e6d99ad6a9b65751c9294bb6d150936dcebfec75ca43d53a02";
    private final AuthenticationManager authenticationManager;
    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenDto login(LoginRequest request) {
    	
    	if(!userRepository.existsByUserEmail(request.getUserEmail()))
    	 throw new WrongCredentialsException("Please Enter the valid Email");
    	
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getPassword()));
        if (authenticate.isAuthenticated())
            return TokenDto
                    .builder()
                    .token(jwtService.generateToken(request.getUserEmail()))
                    .refreshToken(jwtService.generateRefreshToken(request.getUserEmail()))
                    .build();
        else throw new WrongCredentialsException("Please Enter the valid credentials");
    }

    public ResponseEntity<Object> register(RegisterRequest request) {
        return userServiceClient.save(request);
    }
    
    public TokenDto refreshToken(String token) {
    	Claims claims = this.getAllClaimsFromToken(token);
    	if(claims==null)
    		throw new WrongCredentialsException("Invalid TOken");
    	else
    	{
    	   return TokenDto
                  .builder()
                  .token(jwtService.generateToken(claims.getSubject()))
//                  .refreshToken(token)
                  .build();
        	
    	}
    }
    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(REFRESH_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
