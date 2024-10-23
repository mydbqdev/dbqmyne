package com.dbq.postservice.db.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dbq.postservice.db.model.ListingCollection;

public interface ListingRepository extends MongoRepository<ListingCollection, String> {

	@Query(value = "{ 'listingId': ?0 }")
	ListingCollection findByListingId(String listingId);

	@Query(value = "{ 'category': { $regex: ?0, $options: 'i' }, 'isFree': ?1, 'isDiscount': ?2, " + " 'title': { $regex: ?3, $options: 'i' } }")
	List<ListingCollection> findListings(String category, Boolean isFree, Boolean isDiscount, String searchTerm);

}
