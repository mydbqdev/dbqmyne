package com.myne.rest.controller;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myne.entity.UserLoginDetails;
import com.myne.entity.UserMaster;
import com.myne.repository.UserLoginDetailsRepository;
import com.myne.repository.UserMasterRepository;
import com.myne.responsce.model.LoginRequest;
import com.myne.responsce.model.TokenResponse;
import com.myne.rest.model.UserMasterModel;
import com.myne.service.PasswordDecoder;
import com.myne.util.JwtUtil;

@RestController	
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordDecoder passwordDecoder;
	
	@Autowired
	private UserLoginDetailsRepository userLoginDetailsRepository;
	
	@Autowired
	private UserMasterRepository userMasterRepository;
	
	
	@PostMapping("/signin")
	public ResponseEntity<Object> generateJwtToken(@RequestBody LoginRequest request) throws InvalidKeyException, AuthenticationException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Authentication authentication = null;
		TokenResponse response = new TokenResponse();
		try {
			
			Optional<UserLoginDetails> userLogin = userLoginDetailsRepository.findById(request.getUserEmail());
			
			if(userLogin.isEmpty()) {
				return new ResponseEntity<Object>("Invalid Credentials/User Can't Access Myne", HttpStatus.BAD_REQUEST);
			}else {
				
				if(userLogin.get().getLoginPassword().equals(request.getLoginPassword())) {
					
				authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getLoginPassword()));
				//.authenticate(new UsernamePasswordAuthenticationToken(request.getLoginEmail(), passwordDecoder.decryptedText(request.getLoginPassword())));
				String token = jwtUtil.generateToken(authentication);
			
				response.setToken(token);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
				
				}else {
					return new ResponseEntity<Object>("Invalid Password", HttpStatus.BAD_REQUEST);
				}
			}
		}  catch (DisabledException e) {                                                       
			//throw new DisabledUserException("User Inactive");
		//	log.error("Exception occured while separation/signin: User Inactive" +e);
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} catch (BadCredentialsException e) {
			//throw new InvalidUserCredentialsException("Invalid Credentials");
		//	log.error("Exception occured while separation/signin: Invalid Credentials" +e);
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}	
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Object> userSignin(@RequestBody UserMasterModel model) {
		Object response = new Object() ;
		try {
		Optional<UserMaster> userMaster = userMasterRepository.findById(model.getUserEmail());
	
		if ((userMaster.isEmpty())) {
			UserMaster saveUaster = new UserMaster();
			
			saveUaster.setUserEmail(model.getUserEmail());
			saveUaster.setZipCode(model.getZipCode());
			
			userMasterRepository.save(saveUaster);
			
			UserLoginDetails userLogin = new UserLoginDetails();
			
			userLogin.setUserEmail(model.getUserEmail());
			userLogin.setLoginPassword(model.getLoginPassword());
			
			userLoginDetailsRepository.save(userLogin);
			
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.setUserEmail(model.getUserEmail());
			loginRequest.setLoginPassword(model.getLoginPassword());
	       
	        response =  generateJwtToken(loginRequest);
			
		} else {
	       
			response =   model.getUserEmail()+" User alredy exists ";
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
		
		}  catch (Exception e) {                                  
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);

		}
	}
	@PostMapping("/saveUserDetails")
	public ResponseEntity<Object> saveUserDetails(@RequestBody UserMasterModel model) {
		Object response = new Object() ;
		try {
		Optional<UserMaster> userMaster = userMasterRepository.findById(model.getUserEmail());
	
		if ((userMaster.isPresent())) {
			UserMaster saveUaster =userMaster.get();
			
			saveUaster.setUserName(model.getUserName());
			saveUaster.setUserContact(model.getUserContact());
			
			userMasterRepository.save(saveUaster);
			
			response =  "User Details has been saved successfully";
			
		} else {
	       
			response =   model.getUserEmail()+" User not exists ";
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
		
		}  catch (Exception e) {                                  
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);

		}
	}
}