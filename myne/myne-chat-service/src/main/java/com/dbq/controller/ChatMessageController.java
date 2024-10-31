package com.dbq.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.dbq.model.ChatChannel;
import com.dbq.model.ChatMessage;
import com.dbq.model.ChatMessageDTO;
import com.dbq.repository.ChatChannelRepository;
import com.dbq.repository.ChatMessageRepository;

import reactor.core.publisher.Flux;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
//@CrossOrigin(value = { "*" },
//maxAge = 900
//)
@RestController
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
public class ChatMessageController {
//	@Autowired
//	private ReactiveMongoTemplate reactiveMongoTemplate;
	
	  @Autowired
	    private ChatMessageRepository chatMessageRepo;
	  
	  @Autowired
	    private ChatChannelRepository chatChannelRepo;
	  

	    @Autowired
	    private ModelMapper modelMapper;

	    @PostMapping("/chats")
	    @ResponseStatus(HttpStatus.CREATED)
	    public ResponseEntity<String> postChat(@Valid @RequestBody ChatMessageDTO chatMessageDTO) {
	    	try
	    	{
	    		String channelId = "";
	    		Set<String> set = new HashSet<String>();
        		set.add(chatMessageDTO.getFrom());
        		set.add(chatMessageDTO.getTo());
        		
	    		ChatChannel chatChannel= chatChannelRepo.findByUsers(set).block();
	    		if(chatChannel==null)
	    		{
	    			ChatChannel channel2 = new ChatChannel();
	        		channel2.setUsers(set);
	        		chatChannelRepo.save(channel2).subscribe();
	        		channelId = channel2.getId();
	    		}
	    		else
	    			channelId = chatChannel.getId();
	    		
	    		chatMessageDTO.setChannelId(channelId);	
	    		ChatMessage chatMessage = modelMapper.map(chatMessageDTO, ChatMessage.class);
	    		chatMessageRepo.save(chatMessage).subscribe();
	    		return new ResponseEntity<>("{\"message\":\"" + channelId + "\"}", HttpStatus.OK);
	    	}catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    		return new ResponseEntity<>("{\"message\":\"" + ex.toString() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	    }

	    @GetMapping(value = "/chats/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	    public Flux<ChatMessage> streamMessages(@RequestParam String channelId) {
	        return chatMessageRepo.findWithTailableCursorByChannelId(channelId);
	    }
	    
//	    @PostMapping("/test")
//	    @ResponseStatus(HttpStatus.CREATED)
//	    public void postChat() {
//	    	reactiveMongoTemplate.createCollection(
//	    			ChatMessage.class,
//	    		    CollectionOptions.empty().capped().size(10_000_000)
//	    		            .maxDocuments(20)
//	    		);
//	    }
}
