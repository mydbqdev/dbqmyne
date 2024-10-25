package com.dbq.postservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dbq.postservice.config.FeignConfig;
import com.dbq.postservice.dto.User;


@FeignClient(name = "myne-user-service", 
             path = "/v1/user", 
            		 configuration = FeignConfig.class)
public interface UserStorageClient {

    @GetMapping("/getUserIdsUserDetails")
    ResponseEntity<List<User>> getUserIdsUserDetails(@RequestBody List<String> userIds);

    
}