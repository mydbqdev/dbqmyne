package com.myne.jwtFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myne.exception.JwtTokenMissingException;
import com.myne.service.UserAuthService;
import com.myne.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserAuthService UserAuthService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
	try {
		System.out.println("header>>"+header);
		if (header == null || !header.startsWith("Bearer ")) {
			throw new JwtTokenMissingException("No JWT token found in the request headers");
		}

		String token = header.replace("Bearer ","");
		if(jwtUtil.validateToken(token)) {
		// Optional - verification
		//jwtUtil.validateToken(token);

		String userName = jwtUtil.getUserName(token);

		UserDetails userDetails = UserAuthService.loadUserByUsername(userName);

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());

		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		//if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		//}
		}else {
			System.out.println("Can't set security context");
		}
	}catch(ExpiredJwtException ex) {
		request.setAttribute("exception",ex);
	}catch(BadCredentialsException ex) {
		request.setAttribute("exception",ex);
	}catch(Exception ex) {
		request.setAttribute("exception",ex);
	}
		filterChain.doFilter(request, response);
	}

}