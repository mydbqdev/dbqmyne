package com.dbq.postservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.dbq.postservice.config.FeignConfig;


@FeignClient(name = "MYNE-NOTIFY-SERVICE", 
             path = "/v1/notify", 
            		 configuration = FeignConfig.class)
public interface NotifyClient {
	
	@PostMapping(value = "/notifications/sendByZipcode/{zipCode}/{message}")
    ResponseEntity<Void> sendNotificationByZipcode(@PathVariable Long zipCode,@PathVariable String message);
    
    @PostMapping(value = "/notifications/sendByUser/{userId}/{message}")
    ResponseEntity<Void> sendNotificationToUser(@PathVariable String userId,@PathVariable String message );

}
