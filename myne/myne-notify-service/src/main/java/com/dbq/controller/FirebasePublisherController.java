package com.dbq.controller;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.dbq.utils.ConditionMessageRepresentation;
import com.dbq.utils.MulticastMessageRepresentation;
import com.dbq.utils.SubscriptionDto;
import com.dbq.utils.TokenMessageDto;
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
import com.google.firebase.messaging.SendResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/notify")
public class FirebasePublisherController {
	 private final FirebaseMessaging fcm;
	 public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	 private ConcurrentHashMap<String,Set<String>> usersMap = new ConcurrentHashMap<String,Set<String>>();
	 private ConcurrentHashMap<Long,Set<String>> zipcodeUsersMap = new ConcurrentHashMap<Long,Set<String>>();
	 private Gson gson = new GsonBuilder().create();
	    
	    public FirebasePublisherController(FirebaseMessaging fcm) {
	        this.fcm = fcm;        
	    }
	    
	    @PostMapping("/notifications/subscribe")
	    public ResponseEntity<Void> notificationSubscription(HttpServletRequest request, HttpServletResponse response,@RequestBody SubscriptionDto subscriptionDto) throws FirebaseMessagingException {
	    	
	    	String userToken = request.getHeader("Authorization");
	    	if (userToken == null) {
	    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
			} else {
				userToken = userToken.replace("Bearer ", "");
			}	
	    	Map<String, Object> claims = this.getAllClaimsFromToken(userToken);
	    	String userId = claims.get("userid").toString();
	    	Long zipCode = Long.parseLong(claims.get("zipcode").toString());
	    	
	    	System.out.println("Subscribed:" + userId + ":" + subscriptionDto.getRegistryToken() + ":" + zipCode);
	    	if(usersMap.containsKey(userId))
	    	{
	    		Set<String> set = usersMap.get(userId);
	    		set.add(subscriptionDto.getRegistryToken());
	    	}
	    	else
	    	{
	    		Set<String> set = new HashSet<String>();
	    		set.add(subscriptionDto.getRegistryToken());
	    		usersMap.put(userId,set);
	    	}
	    	
	    	if(zipcodeUsersMap.containsKey(zipCode))
	    	{
	    		Set<String> set = zipcodeUsersMap.get(zipCode);
	    		set.add(userId);
	    	}
	    	else
	    	{
	    		Set<String> set = new HashSet<String>();
	    		set.add(userId);
	    		zipcodeUsersMap.put(zipCode, set);
	    	}
	    	
	        return ResponseEntity.ok().build();        
	    }
	    
	    @PostMapping("/notifications/unsubscribe")
	    public ResponseEntity<Void> notificationUnSubscription(HttpServletRequest request, HttpServletResponse response,@RequestBody SubscriptionDto subscriptionDto) throws FirebaseMessagingException {

	    	String userToken = request.getHeader("Authorization");
	    	if (userToken == null) {
	    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
			} else {
				userToken = userToken.replace("Bearer ", "");
			}	
	    	Map<String, Object> claims = this.getAllClaimsFromToken(userToken);
	    	String userId = claims.get("userid").toString();
	    	Long zipCode = Long.parseLong(claims.get("zipcode").toString());
	    	
	    	System.out.println("UnSubscribed:" + userId + ":" + subscriptionDto.getRegistryToken() + ":" + zipCode);
	    	if(usersMap.containsKey(userId))
	    	{
	    		Set<String> set = usersMap.get(userId);
	    		if(set.contains(subscriptionDto.getRegistryToken()))
	    			set.remove(subscriptionDto.getRegistryToken());
	    		if(set.size()==0)
	    		{
	    			usersMap.remove(userId);
	    			if(zipcodeUsersMap.containsKey(zipCode))
	    			{
	    				Set<String> zset = zipcodeUsersMap.get(zipCode);
	    				zset.remove(userId);
	    				if(zset.size()==0)
	    					zipcodeUsersMap.remove(zipCode);
	    			}
	    		}
	    	}
	        return ResponseEntity.ok().build();        
	    }     
	    
	    @PostMapping("/notifications/sendByZipcode/{zipCode}/{message}")
	    public ResponseEntity<Void> sendNotificationByZipcode(@PathVariable Long zipCode,@PathVariable String message) throws FirebaseMessagingException {
	    	if(zipcodeUsersMap.containsKey(zipCode))
	    	{
	    		Set<String> users = zipcodeUsersMap.get(zipCode);
	    		Set<String> tokenSet = new HashSet<String>();
	    		if(users.size()>0)
	    		{
	    			users.stream().forEach(user->{
	    				if(usersMap.containsKey(user))
	    					tokenSet.addAll(usersMap.get(user));
	    			});
	    		}
	    		tokenSet.remove(null);
	    		Notification.Builder builder = Notification.builder();
	            MulticastMessage firebaseMultiCastmsg = MulticastMessage.builder()
	            		.setNotification(builder.build())
	            		.putData("title", "Myne")
				        .putData("body", message)
				        .putData("image","https://static.wixstatic.com/media/991410_996ad37c50a844f6a88e6e651fc8211d~mv2.png/v1/fill/w_44,h_38,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/Logo.png")
	      	          .addAllTokens(tokenSet)
	      	          .build();

	      	    fcm.sendEachForMulticast(firebaseMultiCastmsg);
	    	}
	    	
	    	return ResponseEntity.ok().build();        
	    }
	    
	    @PostMapping("/notifications/sendByUser/{userId}/{message}")
	    public ResponseEntity<Void> sendNotificationToUser(@PathVariable String userId,@PathVariable String message ) throws FirebaseMessagingException {
	    	System.out.println("userId:" + userId + "::" + "message:" + message);
	    	if(usersMap.containsKey(userId)) {
	    		Set<String> tokens = usersMap.get(userId);
	    		tokens.remove(null);
	    		System.out.println("tokens size::" + tokens.size());
//	    		List<Message> list = new ArrayList<Message>();
	    		if(tokens.size()>1)
	    		{
	    			System.out.println("for tokens::" + gson.toJson(tokens));
	    			Notification.Builder builder = Notification.builder();
		            MulticastMessage firebaseMultiCastmsg = MulticastMessage.builder()
		            		.setNotification(builder.build())
		            		.putData("title", "Myne")
					        .putData("body", message)
					        .putData("image","https://static.wixstatic.com/media/991410_996ad37c50a844f6a88e6e651fc8211d~mv2.png/v1/fill/w_44,h_38,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/Logo.png")
		      	          .addAllTokens(tokens)
		      	          .build();
	    			  BatchResponse result = fcm.sendEachForMulticast(firebaseMultiCastmsg);
	    			  System.out.println(result.getSuccessCount() + "::" + result.getFailureCount());
	    			  for(SendResponse sendResponse : result.getResponses())
	    			  {
	    				  System.out.println(sendResponse.getMessageId() + "::" + sendResponse.isSuccessful());
	    			  }
	    		}
	    		else if(tokens.size()==1)
	    		{
	    			String token = tokens.iterator().next();
	    			System.out.println("Triggering single token::" + token);
	    			Notification.Builder builder = Notification.builder();
	    				Message firebasemsg = Message.builder()
	    						.setNotification(builder.build())
			            		.putData("title", "Myne")
						        .putData("body", message)
						        .putData("image","https://static.wixstatic.com/media/991410_996ad37c50a844f6a88e6e651fc8211d~mv2.png/v1/fill/w_44,h_38,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/Logo.png")
						        .setToken(token)
		    		   	        .build();
	    				String result = fcm.send(firebasemsg);
	    				System.out.println(result);
	    		}
	    	
	    		
	    	}
	    	return ResponseEntity.ok().build();        
	    }
	    
	    @PostMapping("/notifications/testMessage")
	    public ResponseEntity<Void> sendByToken(@RequestBody TokenMessageDto dto ) throws FirebaseMessagingException {
	    				Notification.Builder builder = Notification.builder();
	    				Message firebasemsg = Message.builder()
	    						.setNotification(builder.build())
	    						.putData("title", "Myne")
	    				        .putData("body", dto.getMessage())
	    				        .putData("image","https://static.wixstatic.com/media/991410_996ad37c50a844f6a88e6e651fc8211d~mv2.png/v1/fill/w_44,h_38,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/Logo.png")
 	    		   	            .setToken(dto.getToken())
		    		   	        .build();
	    				String result = fcm.send(firebasemsg);
	    				System.out.println(result);
	    	return ResponseEntity.ok().build();        
	    }
	    @PostMapping("/notifications/testMulticastMessage")
	    public ResponseEntity<Void> sendByMulticastToken(@RequestBody TokenMessageDto dto ) throws FirebaseMessagingException {
	    	
	    	  List<String> tokens = new ArrayList<String>();
	    	  tokens.add(dto.getToken());
	    	  Notification.Builder builder = Notification.builder();
	            MulticastMessage firebaseMultiCastmsg = MulticastMessage.builder()
	            		.setNotification(builder.build())
	            		.putData("title", "Myne")
				        .putData("body", dto.getMessage())
				        .putData("image","https://static.wixstatic.com/media/991410_996ad37c50a844f6a88e6e651fc8211d~mv2.png/v1/fill/w_44,h_38,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/Logo.png")
	      	          .addAllTokens(tokens)
	      	          .build();
			  BatchResponse result = fcm.sendEachForMulticast(firebaseMultiCastmsg);
			  System.out.println(result.getSuccessCount() + "::" + result.getFailureCount());
			  for(SendResponse sendResponse : result.getResponses())
			  {
				  System.out.println(sendResponse.getMessageId() + "::" + sendResponse.isSuccessful());
			  }
	    	return ResponseEntity.ok().build();        
	    }
	    
	    @GetMapping("/notifications/clearUserCache")
	    public ResponseEntity<Void> clearUserCache() throws FirebaseMessagingException {
	    	usersMap.clear();
	    	return ResponseEntity.ok().build();        
	    }
	    @GetMapping("/notifications/clearZipcodeUserCache")
	    public ResponseEntity<Void> clearZipcodeUserCache() throws FirebaseMessagingException {
	    	zipcodeUsersMap.clear();
	    	return ResponseEntity.ok().build();        
	    }
	    @GetMapping("/notifications/clearAllCache")
	    public ResponseEntity<Void> clearAllCache() throws FirebaseMessagingException {
	    	usersMap.clear();
	    	zipcodeUsersMap.clear();
	    	return ResponseEntity.ok().build();        
	    }

	    
	    private Claims getAllClaimsFromToken(String token) {
	        Claims claims;
	        try {
	            claims = Jwts.parser()
	                    .setSigningKey(SECRET)
	                    .parseClaimsJws(token)
	                    .getBody();
	        } catch (Exception e) {
	            claims = null;
	        }
	        return claims;
	    }
}
