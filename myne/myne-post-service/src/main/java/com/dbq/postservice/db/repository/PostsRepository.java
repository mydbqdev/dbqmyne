package com.dbq.postservice.db.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dbq.postservice.db.model.PostCollection;

public interface PostsRepository  extends MongoRepository<PostCollection,String>{
	
	 void deleteById(String postId);
	 
	  Page<PostCollection> findAll(Pageable pageable);
	 
	  List<PostCollection> findAllByOrderByCreatedAtDesc(Pageable pageable);
	  
	  @Query(value= "{zipCode : ?0}")  
	  List<PostCollection> getPostsNearBy(String zipCode, Pageable pageable);
	 
}
