package com.dbq.postservice.service;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dbq.postservice.client.S3StorageClient;
import com.dbq.postservice.db.model.PostCollection;
import com.dbq.postservice.db.repository.PostsRepository;
import com.dbq.postservice.dto.MediaDetailsForRequest;
import com.dbq.postservice.dto.MediaUrlDetails;
import com.dbq.postservice.dto.PostsBody;
import com.dbq.postservice.dto.PostsResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	
	@Autowired
	 private PostsRepository postRepository; 
	 private FileUploadService fileUploadService ;
	 private final S3StorageClient s3StorageClient;
	// private UserRepository userRepository;
	 

	 
	    public String createPosts(String userId, PostsBody body) {
	        try {
	        	
	            PostCollection post = new PostCollection();
	            post.setUserId(userId);
	            post.setZipCode(body.getZipCode());
	            post.setDescription(body.getDescription());
	            post.setLikdUserIds(new String[]{});
	           
	       	    LocalDateTime currentDateTime = LocalDateTime.now();
	            // Format the date and time (optional)
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	            String formattedDateTime = currentDateTime.format(formatter);
	            post.setCreatedAt(formattedDateTime);
	            post.setUpdatedAt("");
//	            List<MediaUrlDetails>  mediaUrlDetails = new ArrayList<>();
	            
//	            if(null != body.getMediaDetails() ) {
//	            	
//	            for (MediaDetailsForRequest bodyMedia : body.getMediaDetails()) {
//	            	 
//	            	MediaUrlDetails entyMedia = new MediaUrlDetails();
//	            	
//	            	entyMedia.setContentType(bodyMedia.getContentType());
//	            	entyMedia.setType(bodyMedia.getType());
//	            	entyMedia.setUrl("");
//	            	
//	               // fileUploadService.uploadFileAsync(userId, zipCode, formattedDateTime, null);
//	            	
//	            	mediaUrlDetails.add(entyMedia);
//				}
//	            }
	            
	            post.setMediaDetails(new ArrayList<>());
	            
	            PostCollection savedPost = postRepository.save(post);
	            
	            CompletableFuture <List <MediaUrlDetails>>  mediaUrl= s3StorageClient.uploadFilesToS3(body.getMediaDetails(), "Posts");
	            
	            if(null != mediaUrl && null != mediaUrl.get() ) {
	            	
	            	List <MediaUrlDetails> updateUrl= mediaUrl.get();
	            	savedPost.setMediaDetails(updateUrl);
	            	
	            	postRepository.save(savedPost);
	            }
	            
//	            if(null != body.getMediaDetails() && !body.getMediaDetails().isEmpty()) {
//	            	
//	            CompletableFuture.runAsync(() -> {
//                    try {
//                    	List<MediaUrlDetails> media = null;//userService.uploadImageToS3(body.getMediaDetails());
//                    	savedPost.setMediaDetails(media);
//                        
//                        postRepository.save(post);
//                    } catch (Exception e) {
//                        // Handle exception (logging, etc.)
//                    }
//                });
//	            }
	            
	            return "Post created successfully";
	        } catch (Exception e) {
	            // Handle exceptions, e.g., log error
	            return "Error creating post: " + e.getMessage();
	        }
	    }

	    public List<PostsResponse> getPosts(String filterType, Integer pageIndex, Integer pageSize, String zipCode, String searchTerm) {
	    			
	    	List<PostsResponse> list = new ArrayList<>();
	    	
	        	PageRequest pageable = PageRequest.of(pageIndex, pageSize);
	        //	Page<PostCollection> posts = postRepository.findPosts(zipCode, searchTerm, pageable);
	        	
	        	List<PostCollection> posts = postRepository.findAll();
	        	for (PostCollection postCollection : posts) {
	        		
	        		PostsResponse responce = new PostsResponse();
	        		
	        		responce.setUserId(postCollection.getUserId());
	        		responce.setZipCode(postCollection.getZipCode());
	        		responce.setPostId(postCollection.getPostId());
	        		//responce.setCreatorName(postCollection.getUserId());
	        		responce.setDescription(postCollection.getDescription());
	        	
	        		responce.setLikeCount(postCollection.getLikdUserIds() != null ? postCollection.getLikdUserIds().length : 0);
	        		responce.setIsLiked(postCollection.getLikdUserIds() != null && Arrays.stream(postCollection.getLikdUserIds())
	        		                          .anyMatch(userId -> userId.equals(postCollection.getUserId())));
	        		responce.setCommentsCount(0);
	        		responce.setCreatedAt(postCollection.getCreatedAt());
	        		responce.setUpdatedAt(postCollection.getUpdatedAt());
	        		
	        		list.add(responce);
				}
	        	
	        	
	         
	            return list; 
	        
	    }

	    public String deletePosts(String userId, String postId) {
	    	
	    	PostCollection post = postRepository.findById(postId).orElse(null);
	         if (post == null) {
	             return "Post not found";
	         }

	         if (!post.getUserId().equals(userId)) {
	             return "User not authorized to delete this post";
	         }

	         // Delete the post
	         postRepository.deleteById(postId);
	         return "Post deleted successfully";
	    }

	    public String updatePosts(String userId, String postId, PostsBody body) {
	    	    try {
	    	        // Fetch the existing post
	    	        PostCollection post = postRepository.findById(postId)
	    	                .orElseThrow(() -> new RuntimeException("Post not found"));

	    	        // Optional: Check if the userId matches the post's userId
	    	        if (!post.getUserId().equals(userId)) {
	    	            return "User not authorized to update this post";
	    	        }

	    	        // Update the post's fields
	    	        post.setDescription(body.getDescription());

	    	        // Update media details if provided
	    	        if (body.getMediaDetails() != null) {
	    	            List<MediaUrlDetails> mediaUrlDetails = new ArrayList<>();
	    	            for (MediaDetailsForRequest bodyMedia : body.getMediaDetails()) {
	    	                MediaUrlDetails entyMedia = new MediaUrlDetails();
	    	                entyMedia.setContentType(bodyMedia.getContentType());
	    	                entyMedia.setType(bodyMedia.getType());
	    	                entyMedia.setUrl(""); // You can set the URL if needed
	    	                mediaUrlDetails.add(entyMedia);
	    	            }
	    	            post.setMediaDetails(mediaUrlDetails);
	    	        }

	    	        // Update timestamps
	    	        LocalDateTime currentDateTime = LocalDateTime.now();
	    	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    	        String formattedDateTime = currentDateTime.format(formatter);
	    	        post.setUpdatedAt(formattedDateTime); // Update the updatedAt field

	    	        // Save the updated post
	    	        postRepository.save(post);
	    	        return "Post updated successfully";
	    	    } catch (Exception e) {
	    	        // Handle exceptions, e.g., log error
	    	        return "Error updating post: " + e.getMessage();
	    	    }
	    	}

	    
	    public String likePost(String userId, String postId) {
	    	 PostCollection post = null;
	        try {
	            // Fetch the post by ID
	             post = postRepository.findById(postId)
	                    .orElseThrow(() -> new RuntimeException("Post not found"));

	            // Get the list of liked user IDs
	            String[] likedUserIds = post.getLikdUserIds();

	            // Check if the user ID is already in the list
	            if (likedUserIds != null && Arrays.asList(likedUserIds).contains(userId)) {
	                // Remove the user ID from the list
	                likedUserIds = Arrays.stream(likedUserIds)
	                        .filter(id -> !id.equals(userId))
	                        .toArray(String[]::new);
	                post.setLikdUserIds(likedUserIds);
	                return "Post unliked successfully";
	            } else {
	                // Add the user ID to the list
	                if (likedUserIds == null) {
	                    likedUserIds = new String[]{userId};
	                } else {
	                    likedUserIds = Arrays.copyOf(likedUserIds, likedUserIds.length + 1);
	                    likedUserIds[likedUserIds.length - 1] = userId;
	                }
	                post.setLikdUserIds(likedUserIds);
	                return "Post liked successfully";
	            }

	        } catch (Exception e) {
	            // Handle exceptions
	            return "Error liking/unliking post: " + e.getMessage();
	        } finally {
	        	if (post != null) { // Check if post is not null before saving
	                postRepository.save(post);
	            }
	        }
	    }

	}
	 
	 
	
