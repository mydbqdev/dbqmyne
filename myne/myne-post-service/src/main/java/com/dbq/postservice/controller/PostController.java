package com.dbq.postservice.controller;



import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.dto.MediaDetailsForRequest;
import com.dbq.postservice.dto.PostsBody;
import com.dbq.postservice.dto.PostsResponse;
import com.dbq.postservice.interfeces.PostsApi;
import com.dbq.postservice.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/post")
@RequiredArgsConstructor
public class PostController implements PostsApi {
	  private static final Logger log = LoggerFactory.getLogger(PostController.class);
	
    private final PostService postService;
    private final ModelMapper modelMapper;
    
    
    @PostMapping(value = "/posts1/{userId}/save")
    public ResponseEntity<Object> savePosts1(@PathVariable String userId,@RequestBody PostsBody body) {
        
        try {
        	System.out.println("><><>>>>>>>>>>");
            // Handle media uploads
            if (body.getMediaDetails() != null) {
                for (MediaDetailsForRequest media : body.getMediaDetails()) {
                    MultipartFile file = media.getUploadFile();
                    if (file != null && !file.isEmpty()) {
                        // Logic to upload the file (e.g., to S3 or your file storage)
                    }
                }
            }

            // Logic to save the post
            // ...
            savePosts(userId,body);
            return ResponseEntity.ok("Post created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error creating post: " + e.getMessage());
        }
    }

    
    
	@Override
	public ResponseEntity<Object> savePosts(String userId,PostsBody body) {
		ResponseEntity<Object> entity =null;

	    try {
	    	System.out.println(userId+">>"+"body"+body.toString());
	    	String message =  postService.createPosts(userId,body) ;
	    	
			String listData = "{\"status\":\"" + message + "\"}";
			entity = new ResponseEntity<Object>(listData, HttpStatus.OK) {};
	    	return entity ;
	    } catch (Exception e) {
	        log.error("Couldn't serialize response for content type application/json", e);
	        return  new ResponseEntity<Object>(e,HttpStatus.BAD_REQUEST);

	    }
	}
	@Override
	public ResponseEntity<Object> getPosts(@NotNull @Valid String filterType, @Valid Integer pageIndex,
			@Valid Integer pageSize, @Valid String zipCode, @Valid String searchTerm) {
		ResponseEntity<Object> entity =null;

	    try {
	    	
	    	List<PostsResponse> posts = postService.getPosts(filterType,pageIndex,pageSize,zipCode,searchTerm) ;
	    
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
	public ResponseEntity<Object> updatePosts(String userId, String postId, @Valid PostsBody body) {
		ResponseEntity<Object> entity =null;

	    try {
	    	
	    	String message = postService.updatePosts(userId,postId,body) ;
	    	
			String listData = "{\"status\":\"" + message + "\"}";
			entity = new ResponseEntity<Object>(listData, HttpStatus.OK) {};
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
  
//    @PostMapping("/save")
//    public ResponseEntity<UserDto> save(@Valid @RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(modelMapper.map(userService.saveUser(request), UserDto.class));
//    }

//    @GetMapping("/getAll")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<List<UserDto>> getAll() {
//        return ResponseEntity.ok(userService.getAll().stream()
//                .map(user -> modelMapper.map(user, UserDto.class)).toList());
//    }
	


   
}
