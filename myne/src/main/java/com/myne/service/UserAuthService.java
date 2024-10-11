package com.myne.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myne.entity.UserLoginDetails;
import com.myne.repository.UserLoginDetailsRepository;

@Service
public class UserAuthService implements UserDetailsService{
	
	@Autowired
	private UserLoginDetailsRepository userLoginDetailsRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		
		Optional<UserLoginDetails> user = userLoginDetailsRepository.findById(userEmail);	

		if ( user == null || user.isEmpty()) {
			throw new UsernameNotFoundException("User not found with username: " + userEmail);
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUserEmail(), user.get().getLoginPassword(),new ArrayList<>());
		
	}
	


}
