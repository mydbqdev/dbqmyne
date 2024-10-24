package com.dbq.userservice.service;

import com.dbq.userservice.client.FileStorageClient;
import com.dbq.userservice.enums.Active;
import com.dbq.userservice.enums.Role;
import com.dbq.userservice.exc.NotFoundException;
import com.dbq.userservice.model.User;
import com.dbq.userservice.model.UserDetails;
import com.dbq.userservice.repository.UserRepository;
import com.dbq.userservice.request.RegisterRequest;
import com.dbq.userservice.request.UserUpdateRequest;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	 private static final Logger log = LoggerFactory.getLogger(UserService.class);
		
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageClient fileStorageClient;
    private final ModelMapper modelMapper;

    public ResponseEntity<Object> saveUser(RegisterRequest request) {
    	
    	 ResponseEntity<Object>	entity = null;
    	try {
    	 if(userRepository.findByUserEmail(request.getUserEmail()).isPresent()) {
    		
    		 
    		 String listData = "{\"error\":\"User already exists with the provided email address" + "\"}";
    		 
    		 return  new ResponseEntity<Object>(listData,HttpStatus.BAD_REQUEST);
    	}else {
    	
         User toSave = User.builder()
                .userEmail(request.getUserEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(new HashSet<Role>(Arrays.asList(Role.USER)))
                .zipCode(request.getZipCode())
                .userFirstName(request.getUserFirstName())
                .userLastName(request.getUserLastName())
                .active(Active.ACTIVE).build();

         User user=  userRepository.save(toSave);

         entity = new ResponseEntity<Object>(user, HttpStatus.OK) {};
	     return entity ;
    	}
     } catch (Exception e) {
        log.error("Couldn't serialize response for content type application/json", e);
        return  new ResponseEntity<Object>(e,HttpStatus.BAD_REQUEST);

    }
         
    }

    public List<User> getAll() {
        return userRepository.findAllByActive(Active.ACTIVE);
    }

    public User getUserById(String id) {
        return findUserById(id);
    }

  
    public User getUserByUserEmail(String userEmail) {
        return findUserByUserEmail(userEmail);
    }


	public User updateUserById(UserUpdateRequest request, MultipartFile file) {
        User toUpdate = findUserById(request.getId());

        request.setUserDetails(updateUserDetails(toUpdate.getUserDetails(), request.getUserDetails(), file));
        modelMapper.map(request, toUpdate);

        return userRepository.save(toUpdate);
    }

    public void deleteUserById(String id) {
        User toDelete = findUserById(id);
        toDelete.setActive(Active.INACTIVE);
        userRepository.save(toDelete);
    }

    protected User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

   

    protected User findUserByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private UserDetails updateUserDetails(UserDetails toUpdate, UserDetails request, MultipartFile file) {
        toUpdate = toUpdate == null ? new UserDetails() : toUpdate;

        if (file != null) {
            String profilePicture = fileStorageClient.uploadImageToFIleSystem(file).getBody();
            if (profilePicture != null) {
                fileStorageClient.deleteImageFromFileSystem(toUpdate.getProfilePicture());
                toUpdate.setProfilePicture(profilePicture);
            }
        }

        modelMapper.map(request, toUpdate);

        return toUpdate;
    }
}