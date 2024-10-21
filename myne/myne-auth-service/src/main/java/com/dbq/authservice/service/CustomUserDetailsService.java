package com.dbq.authservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbq.authservice.db.models.User;
import com.dbq.authservice.db.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	 @Autowired
	  UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUserEmail(userEmail)
		        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userEmail));
		 return CustomUserDetails.build(user);
	}
   
}