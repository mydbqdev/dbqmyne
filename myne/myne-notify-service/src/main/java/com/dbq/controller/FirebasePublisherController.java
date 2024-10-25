package com.dbq.controller;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.dbq.utils.ConditionMessageRepresentation;
import com.dbq.utils.MulticastMessageRepresentation;
import com.dbq.utils.SubscriptionDto;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidFcmOptions;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FcmOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/notify")
public class FirebasePublisherController {
	 private final FirebaseMessaging fcm;
	 
	 private ConcurrentHashMap<String,Set<String>> usersMap = new ConcurrentHashMap<String,Set<String>>();
	 private ConcurrentHashMap<Long,Set<String>> zipcodeUsersMap = new ConcurrentHashMap<Long,Set<String>>();
	 
	    
	    public FirebasePublisherController(FirebaseMessaging fcm) {
	        this.fcm = fcm;        
	    }
	    
	    @PostMapping("/notifications/subscribe")
	    public ResponseEntity<Void> notificationSubscription(@RequestBody SubscriptionDto subscriptionDto) throws FirebaseMessagingException {
	    	if(usersMap.containsKey(subscriptionDto.getUserId()))
	    	{
	    		Set<String> set = usersMap.get(subscriptionDto.getUserId());
	    		set.add(subscriptionDto.getRegistryToken());
	    	}
	    	else
	    	{
	    		Set<String> set = new HashSet<String>();
	    		set.add(subscriptionDto.getRegistryToken());
	    		usersMap.put(subscriptionDto.getUserId(),set);
	    	}
	    	
	    	if(zipcodeUsersMap.containsKey(subscriptionDto.getZipCode()))
	    	{
	    		Set<String> set = zipcodeUsersMap.get(subscriptionDto.getZipCode());
	    		set.add(subscriptionDto.getUserId());
	    	}
	    	else
	    	{
	    		Set<String> set = new HashSet<String>();
	    		set.add(subscriptionDto.getUserId());
	    		zipcodeUsersMap.put(subscriptionDto.getZipCode(), set);
	    	}
	    	
	        return ResponseEntity.ok().build();        
	    }
	    
	    @PostMapping("/notifications/unsubscribe")
	    public ResponseEntity<Void> notificationUnSubscription(@RequestBody SubscriptionDto subscriptionDto) throws FirebaseMessagingException {        
	    	if(usersMap.containsKey(subscriptionDto.getUserId()))
	    	{
	    		Set<String> set = usersMap.get(subscriptionDto.getUserId());
	    		if(set.contains(subscriptionDto.getRegistryToken()))
	    			set.remove(subscriptionDto.getRegistryToken());
	    		if(set.size()==0)
	    		{
	    			usersMap.remove(subscriptionDto.getUserId());
	    			if(zipcodeUsersMap.contains(subscriptionDto.getZipCode()))
	    			{
	    				Set<String> zset = zipcodeUsersMap.get(subscriptionDto.getZipCode());
	    				zset.remove(subscriptionDto.getUserId());
	    				if(zset.size()==0)
	    					zipcodeUsersMap.remove(subscriptionDto.getZipCode());
	    			}
	    		}
	    	}
	        return ResponseEntity.ok().build();        
	    }     
	    
	    @PostMapping("/notifications/sendByZipcode/{zipCode}/{message}")
	    public ResponseEntity<Void> sendNotificationByZipcode(@PathVariable Long zipCode,@PathVariable String message) throws FirebaseMessagingException {
	    	if(zipcodeUsersMap.contains(zipCode))
	    	{
	    		Set<String> users = zipcodeUsersMap.get(zipCode);
	    		Set<String> tokenSet = new HashSet<String>();
	    		if(users.size()>0)
	    		{
	    			users.stream().forEach(user->{
	    				if(usersMap.contains(user))
	    					tokenSet.addAll(usersMap.get(user));
	    			});
	    		}
	    		
	            MulticastMessage msg = MulticastMessage.builder()
	      	          .addAllTokens(tokenSet)
	      	          .putData("body", message)
	      	          .build();

	      	    fcm.sendMulticast(msg);
	    	}
	    	
	    	return ResponseEntity.ok().build();        
	    }
	    
	    @PostMapping("/notifications/sendByUser/{userId}/{message}")
	    public ResponseEntity<Void> sendNotificationToUser(@PathVariable String userId,@PathVariable String message ) throws FirebaseMessagingException {
	    	if(usersMap.contains(userId)) {
	    		Set<String> tokens = usersMap.get(userId);
	    		List<Message> list = new ArrayList<Message>();
	    		tokens.stream().forEach(token-> {
	    			Message msg = Message.builder()
	    		   	          .setToken(token)
	    		   	          .putData("body", message)
	    		   	          .build();
	    			list.add(msg);
	    		});
	    		fcm.sendAll(list);
	    	}
	    	return ResponseEntity.ok().build();        
	    }
}
