package com.dbq.postservice.db.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dbq.postservice.db.model.ListingCollection;
import com.dbq.postservice.db.model.PostCollection;

public interface PostsRepository  extends MongoRepository<PostCollection,String>{
	
	 void deleteById(String postId);
	 
	  Page<PostCollection> findAll(Pageable pageable);
	 
	  List<PostCollection> findAllByOrderByCreatedAtDesc(Pageable pageable);
	  
	  @Query(value= "{zipCode : ?0}")  
	  List<PostCollection> getPostsNearBy(String zipCode, Pageable pageable);
	 
	  @Query(value = "{ 'description': { $regex: ?0, $options: 'i' } }")
	  List<PostCollection> findByPostsbytitle(String title, Pageable pageable);
	  
	  @Query(value = "{userId : ?0}")
	  List<PostCollection> findByUserId(String userId, Pageable pageable);
}
