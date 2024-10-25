package com.dbq.postservice.db.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dbq.postservice.db.model.ListingCollection;

public interface ListingRepository extends MongoRepository<ListingCollection, String> {

	 Page<ListingCollection> findAll(Pageable pageable);
	 
	@Query(value = "{ 'free': true }")
	List<ListingCollection> findAllFreeListings(Pageable pageable);
	 
	@Query(value = "{ 'free': false }")
	List<ListingCollection> findAllNotFreeListings(Pageable pageable);
	
	@Query(value = "{ 'discount': true }")
	List<ListingCollection> findAllDiscountedListings(Pageable pageable);
	
	
	
    @Query(value = "{ 'free': true, 'creatorId': ?0 }")
    List<ListingCollection> findFreeListingsByUserId(String userId, Pageable pageable);

    @Query(value = "{ 'free': false, 'creatorId': ?0 }")
    List<ListingCollection> findNotFreeListingsByUserId(String userId, Pageable pageable);

    @Query(value = "{ 'discount': true, 'creatorId': ?0 }")
    List<ListingCollection> findDiscountedListingsByUserId(String userId, Pageable pageable);
	
	
    
	@Query(value = "{ 'listingId': ?0 }")
	ListingCollection findByListingId(String listingId);
	
	@Query(value = "{ 'title': { $regex: ?0, $options: 'i' } }")
	List<ListingCollection> findByListingtitle(String title);
	
	@Query(value = "{ 'title': { $regex: ?0, $options: 'i' }, 'free': { $eq: true } }")
	List<ListingCollection> findByListingtitleisfree(String title);
	

	@Query(value = "{ 'category': { $regex: ?0, $options: 'i' }, 'free': ?1, 'discount': ?2, " + " 'title': { $regex: ?3, $options: 'i' } }")
	List<ListingCollection> findListings(String category, Boolean isFree, Boolean isDiscount, String searchTerm);
	
	
	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<ListingCollection> searchByTitle(String searchTerm);
	
}
