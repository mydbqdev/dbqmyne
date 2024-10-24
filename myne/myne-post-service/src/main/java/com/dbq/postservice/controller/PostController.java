package com.dbq.postservice.controller;



import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.dto.PostsBody;
import com.dbq.postservice.dto.PostsFilterDto;
import com.dbq.postservice.dto.PostsResponse;
import com.dbq.postservice.interfeces.PostsApi;
import com.dbq.postservice.service.PostService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/post")
@RequiredArgsConstructor
public class PostController implements PostsApi {
	
	  private static final Logger log = LoggerFactory.getLogger(PostController.class);
	  private Gson gson = new GsonBuilder().create();
//				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private final PostService postService;
    private final ModelMapper modelMapper;
    
    
	@Override
	public ResponseEntity<Object> savePosts(MultipartFile[] files, String body) {

	try {
	    	PostsBody pbody = gson.fromJson(body,PostsBody.class);
	    	return new ResponseEntity<Object>(postService.createPosts(files,pbody), HttpStatus.OK) {};

    } catch (IllegalArgumentException e) { 
        log.error("Bad Request: Invalid arguments provided", e);
        return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (UnsupportedOperationException e) { 
        log.error("Not Implemented: This operation is not supported", e);
        return new ResponseEntity<>("This operation is not supported", HttpStatus.NOT_IMPLEMENTED);
    } catch (Exception e) { 
        log.error("An unexpected error occurred", e);
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
	}
	
	@Override
	public ResponseEntity<Object> getPosts(@RequestBody PostsFilterDto postsFilter) {
		ResponseEntity<Object> entity =null;

	    try {
	    	
	    	List<PostsResponse> posts = postService.getPosts(postsFilter);
	   
			entity = new ResponseEntity<Object>(posts, HttpStatus.OK) {};
	    	return entity ;
	    } catch (Exception e) {
	        log.error("Couldn't serialize response for content type application/json", e);
	        return  new ResponseEntity<Object>(e,HttpStatus.BAD_REQUEST);

	    }
	   
	}
	@Override
	public ResponseEntity<Object> deletePosts(String userId, String postId) {
		ResponseEntity<Object> entity =null;

	    try {
	    	
	    	String message =  postService.deletePosts(userId,postId) ;
	    	
			String listData = "{\"status\":\"" + message + "\"}";
			entity = new ResponseEntity<Object>(listData, HttpStatus.OK) {};
	    	return entity ;
	    } catch (Exception e) {
	        log.error("Couldn't serialize response for content type application/json", e);
	        return  new ResponseEntity<Object>(e,HttpStatus.BAD_REQUEST);

	    }
	  }
	
	@Override
	public ResponseEntity<Object> updatePosts(String userId, String postId, MultipartFile[] files, @Valid String body) {
		ResponseEntity<Object> entity =null;

	    try {
	    	PostsBody pbody = gson.fromJson(body,PostsBody.class);
			entity = new ResponseEntity<Object>(postService.updatePosts(userId,postId,pbody,files) , HttpStatus.OK) {};
	    	return entity ;
	    } catch (Exception e) {
	        log.error("Couldn't serialize response for content type application/json", e);
	        return  new ResponseEntity<Object>(e,HttpStatus.BAD_REQUEST);

	    }
	   }
	
	@Override
	public ResponseEntity<Object> likePost(String userId, String postId) {
		ResponseEntity<Object> entity =null;

	    try {
	    	
	    	String message = postService.likePost(userId,postId) ;
	    	
			String listData = "{\"status\":\"" + message + "\"}";
			entity = new ResponseEntity<Object>(listData, HttpStatus.OK) {};
	    	return entity ;
	    } catch (Exception e) {
	        log.error("Couldn't serialize response for content type application/json", e);
	        return  new ResponseEntity<Object>(e,HttpStatus.BAD_REQUEST);

	    }
	   }

	public ResponseEntity<List<PostsResponse>> searchPosts(@RequestBody PostsFilterDto postsFilter) {
        try {
            List<PostsResponse> response = postService.getPostsbysearch(postsFilter);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	

   
}
