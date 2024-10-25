package com.dbq.userservice.controller;

import com.dbq.userservice.dto.AuthUserDto;
import com.dbq.userservice.dto.UserDto;
import com.dbq.userservice.model.User;
import com.dbq.userservice.request.RegisterRequest;
import com.dbq.userservice.request.UserUpdateRequest;
import com.dbq.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<Object> save(@Valid @RequestBody RegisterRequest request) {
        return userService.saveUser(request);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class)).toList());
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserDto.class));
    }

   
    @RequestMapping(value = "/getUserByUserEmail", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<User> getUserByUserEmail(@RequestParam String userEmail) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserByUserEmail(userEmail), User.class));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#request.id).username == principal")
    public ResponseEntity<UserDto> updateUserById(@Valid @RequestPart UserUpdateRequest request,
                                                  @RequestPart(required = false) MultipartFile file) {
        return ResponseEntity.ok(modelMapper.map(userService.updateUserById(request, file), UserDto.class));
    }

    @DeleteMapping("/deleteUserById/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).username == principal")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
    
    @RequestMapping(value = "/getUserIdsUserDetails", method = {RequestMethod.POST, RequestMethod.GET})
    ResponseEntity<List<User>> getUserIdsUserDetails(@RequestBody List<String> userIds){
		
	 return ResponseEntity.ok(userService.getUserIdsUserDetails(userIds));
    }

}
