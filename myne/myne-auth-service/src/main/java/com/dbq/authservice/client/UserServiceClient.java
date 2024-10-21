package com.dbq.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dbq.authservice.dto.UserDto;
import com.dbq.authservice.request.RegisterRequest;

@FeignClient(name = "myne-user-service", path = "/v1/user")
public interface UserServiceClient {
    @PostMapping("/save")
    ResponseEntity<Object> save(@RequestBody RegisterRequest request);

    @GetMapping("/getUserByUserEmail/{userEmail}")
    ResponseEntity<UserDto> getUserByUserEmail(@PathVariable String userEmail);
}
