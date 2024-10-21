package com.dbq.postservice.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dbq.postservice.db.model.PostCollection;

public interface PostsRepository  extends MongoRepository<PostCollection,String>{
	
	 void deleteById(String postId);
	
	 @Query("{ 'zipCode': ?0, 'description': { $regex: ?1, $options: 'i' }}")
	    Page<PostCollection> findPosts(String zipCode, String searchTerm, Pageable pageable);

}
