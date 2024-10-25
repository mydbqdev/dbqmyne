package com.dbq.userservice.repository;

import com.dbq.userservice.enums.Active;
import com.dbq.userservice.model.User;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserEmail(String username);

    List<User> findAllByActive(Active active);
    
    @Query("{'userdetails.zipCode': ?0}")
    List<User> findAllByZipCode(String zipCode);
    
    List<User> findByIdIn(List<String> userIds);
}
