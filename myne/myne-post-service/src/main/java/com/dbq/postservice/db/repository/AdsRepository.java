package com.dbq.postservice.db.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dbq.postservice.db.model.AdsCollection;

public interface AdsRepository extends MongoRepository<AdsCollection,String> {
	


}
