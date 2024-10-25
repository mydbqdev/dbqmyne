package com.dbq.postservice.service;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.client.S3StorageClient;
import com.dbq.postservice.client.UserStorageClient;
import com.dbq.postservice.db.model.AdsCollection;
import com.dbq.postservice.db.model.PostCollection;
import com.dbq.postservice.db.repository.AdsRepository;
import com.dbq.postservice.db.repository.PostsRepository;
import com.dbq.postservice.dto.MediaUrlDetails;
import com.dbq.postservice.dto.PostsBody;
import com.dbq.postservice.dto.PostsFilterDto;
import com.dbq.postservice.dto.PostsResponse;
import com.dbq.postservice.dto.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	 private static final Logger log = LoggerFactory.getLogger(PostService.class);
	
	@Autowired
	 private PostsRepository postRepository; 
	 private final S3StorageClient s3StorageClient;
	 private final AdsRepository adsRepository;
	 private final UserStorageClient userStorageClient;
	 
	    public String createPosts(MultipartFile[] files, PostsBody body) {
	    	
	    	   PostCollection savedPost = new PostCollection();
	        try {
	        	
	            PostCollection post = new PostCollection();
	            post.setUserId(body.getUserId());
	            post.setZipCode(body.getZipCode());
	            post.setDescription(body.getDescription());
	            post.setLikdUserIds(new String[]{});
	           
	       	    LocalDateTime currentDateTime = LocalDateTime.now();
	            // Format the date and time (optional)
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	            String formattedDateTime = currentDateTime.format(formatter);
	            post.setCreatedAt(formattedDateTime);
	            post.setUpdatedAt("");
	            
	            savedPost = postRepository.save(post);
	            String postId =savedPost.getPostId();           
	            
	            uploadFilesAsync(files, postId, savedPost);
	            
	            return "Post has been created successfully";
	        } catch (Exception e) {
	            // Handle exceptions, e.g., log error
	        	
	            return "Error creating post: " + e.getMessage();
	        }
	    }

	    @Async
	    public void uploadFilesAsync(MultipartFile[] files, String postId, PostCollection savedPost) {
	        List<MediaUrlDetails> list = new ArrayList<>();
	        for (MultipartFile file : files) {
	        	  if (file == null || file.isEmpty()) {
	        		  log.error("File is null or empty: Skipping.");
	                  continue; 
	              }
	            try {
	                ResponseEntity<MediaUrlDetails> respEntity = s3StorageClient.uploadFile(postId, file);
	                if (respEntity.getBody() != null) {
	                    list.add(respEntity.getBody());
	                }
	            } catch (Exception e) {
	                System.err.println("Error uploading file: " + e.getMessage());
	            }
	        }
	        
	        synchronized (savedPost) {
	            
	         savedPost.setMediaDetails(list);
	          
	            postRepository.save(savedPost);
	        }
	    }
	    
	    
	    
	    
	    public List<PostsResponse> getPostsbysearch(PostsFilterDto postsFilter) {
	        List<PostsResponse> list = new ArrayList<>();
	        List<PostCollection> posts = new ArrayList<>();

	        Pageable pageable = PageRequest.of(postsFilter.getPageIndex(), postsFilter.getPageSize());

	        switch (postsFilter.getFilterType()) {
	            case "posts":
	            	if (postsFilter.getSearchContent() == null || postsFilter.getSearchContent().trim().isEmpty()) {
	       			 
	    	            throw new IllegalArgumentException("Search term cannot be null or empty");
	    	            
	    	        }
	    		 String titlefinal = Pattern.quote(postsFilter.getSearchContent().trim());
	    		 
	                posts = postRepository.findByPostsbytitle(titlefinal, pageable);
	                break;
	            default:
	                posts = postRepository.findAll(pageable).getContent();
	                break;
	        }

	        // Check if posts are not empty before processing
	        if (!posts.isEmpty()) {
	            list = getPostsAds(posts);
	        }

	        return list;
	    }
	    
	    
	    public List<PostsResponse> getPosts(PostsFilterDto postsFilter) {
	    	
	    	List<PostsResponse> list = new ArrayList<PostsResponse>();
	    	List<PostCollection> posts = new ArrayList<PostCollection>();
	    	
	    	Pageable pageable = PageRequest.of(postsFilter.getPageIndex(), postsFilter.getPageSize());
	    	
	    	switch (postsFilter.getFilterType()) {
			
	    	case "forYou":
	    		Page<PostCollection> page  = postRepository.findAll(pageable) ;	
	    		posts = page.getContent();
				break;
//			case "myPost":
//				 posts = postRepository.getPostsMyPost(pageIndex,pageSize,zipCode,) ;		
//				break;
			case "nearBy":
				 posts = postRepository.getPostsNearBy(postsFilter.getZipCode(),pageable) ;		
				break;
			case "recent":
				 posts = postRepository.findAllByOrderByCreatedAtDesc(pageable) ;		
				break;
			default:
				
	    		posts = postRepository.findAll(pageable).getContent();		
				break;
			}
	    	
	    	list=getPostsAds(posts);

            return list; 
	    }
	    
	    public 	List<PostsResponse> getPostsAds(List<PostCollection> posts) {
	    	List<PostsResponse> list = new ArrayList<>();
	    	
	    	List<String>userIds= posts.stream().map(d->d.getUserId()).toList();
	    	
	    	 ResponseEntity<List<User>> usersDetails= userStorageClient.getUserIdsUserDetails(userIds);
	    	 
	    	 List<User>user =usersDetails.getBody();
	    	 
        	
        	for (PostCollection postCollection : posts) {
        		
        		PostsResponse responce = new PostsResponse();
        		
        		responce.setUserId(postCollection.getUserId());
        		responce.setZipCode(postCollection.getZipCode());
        		responce.setPostId(postCollection.getPostId());
        		
        		String userName = user.stream()
        			    .filter(d -> d.getId().equals(postCollection.getUserId()))
        			    .map(m -> String.join(" ", m.getUserFirstName(), m.getUserLastName()))
        			    .findFirst()  
        			    .orElse("");
        		
        		responce.setCreatorName(userName);
        		
        		responce.setDescription(postCollection.getDescription());
        		responce.setLikeCount(postCollection.getLikdUserIds() != null ? postCollection.getLikdUserIds().length : 0);
        		responce.setIsLiked(postCollection.getLikdUserIds() != null && Arrays.stream(postCollection.getLikdUserIds())
        		                          .anyMatch(userId -> userId.equals(postCollection.getUserId())));
        		responce.setCommentsCount(0);
        		responce.setCreatedAt(postCollection.getCreatedAt());
        		responce.setUpdatedAt(postCollection.getUpdatedAt());
        		responce.setMediaDetails(postCollection.getMediaDetails());
        		
        		list.add(responce);
			}
        	
        	List<AdsCollection> ads= adsRepository.findAll();
        	PostsResponse adsres = new PostsResponse();
    		if(null !=ads && ads.size()>0) {
    			   AdsCollection randomAd = ads.stream()
                           .skip(new Random().nextInt(ads.size()))
                           .findFirst()
                           .orElse(null);
    		if(null !=randomAd) {
    			adsres.setDescription(randomAd.getDescription());
    			adsres.createdAt(randomAd.getCreatedAt());
    			adsres.setMediaDetails(randomAd.getMediaDetails());
    			adsres.adsHyperLink(randomAd.getHyperLink());
    			
    			}
    		}
    		if( null != adsres && null != adsres.getCreatedAt() && !"".equals(adsres.getCreatedAt())) 
    			list.add(adsres);
        	
        	Collections.shuffle(list);
         
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

	    public String updatePosts(String userId, String postId, PostsBody body,MultipartFile[] files) {
	    	    try {
	
	    	        PostCollection post = postRepository.findById(postId)
	    	                .orElseThrow(() -> new RuntimeException("Post not found"));

	    	        if (!post.getUserId().equals(userId)) {
	    	            return "User not authorized to update this post";
	    	        }

	    	        post.setDescription(body.getDescription());		          

	    	        LocalDateTime currentDateTime = LocalDateTime.now();
	    	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    	        String formattedDateTime = currentDateTime.format(formatter);
	    	        post.setUpdatedAt(formattedDateTime); 

	    	   
	    	        
	    	        uploadFilesAsync(files, postId, postRepository.save(post));
	    	        
	    	        return "Post has been updated successfully";
	    	    } catch (Exception e) {
	    	        // Handle exceptions, e.g., log error
	    	        return "Error updating post: " + e.getMessage();
	    	    }
	    	}

	    
	    public String likePost(String userId, String postId) {
	    	 PostCollection post = null;
	        try {
	             post = postRepository.findById(postId)
	                    .orElseThrow(() -> new RuntimeException("Post not found"));

	            String[] likedUserIds = post.getLikdUserIds();

	            if (likedUserIds != null && Arrays.asList(likedUserIds).contains(userId)) {
	                likedUserIds = Arrays.stream(likedUserIds)
	                        .filter(id -> !id.equals(userId))
	                        .toArray(String[]::new);
	                post.setLikdUserIds(likedUserIds);
	                return "Post unliked successfully";
	            } else {
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
	            return "Error liking/unliking post: " + e.getMessage();
	        } finally {
	        	if (post != null) { 
	                postRepository.save(post);
	            }
	        }
	    }

	}
	 
	 
	
