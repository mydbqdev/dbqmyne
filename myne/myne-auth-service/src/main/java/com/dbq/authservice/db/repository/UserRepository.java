package com.dbq.authservice.db.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dbq.authservice.db.models.User;




public interface UserRepository extends MongoRepository<User, String> {
	
	Optional<User> findByUserEmail(String userEmail);
	
	Boolean existsByUserEmail(String userEmail);
  
}
