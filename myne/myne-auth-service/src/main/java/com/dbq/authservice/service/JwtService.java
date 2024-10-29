package com.dbq.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.dbq.utils.MyneUserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtService {
    private final CustomUserDetailsService customUserDetailsService;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public static final String REFRESH_SECRET = "fe23401e613295e6d99ad6a9b65751c9294bb6d150936dcebfec75ca43d53a02";
    
    public String generateToken(String username) {
    	MyneUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userid",userDetails.getId() );
        claims.put("zipcode",userDetails.getZipcode());
        return createToken(claims, userDetails);
        
    }
    
    public String generateRefreshToken(String username) {
    	MyneUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userid",userDetails.getId() );
        claims.put("zipcode",userDetails.getZipcode());
        return createRefreshToken(claims, userDetails);
        
    }
    

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuer(userDetails.getAuthorities().iterator().next().getAuthority())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    
    private String createRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuer(userDetails.getAuthorities().iterator().next().getAuthority())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 hour
                .signWith(getRefreshSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Key getRefreshSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(REFRESH_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
