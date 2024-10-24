package com.dbq.postservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.client.S3StorageClient;
import com.dbq.postservice.db.model.AdsCollection;
import com.dbq.postservice.db.model.ListingCollection;
import com.dbq.postservice.db.repository.AdsRepository;
import com.dbq.postservice.db.repository.ListingRepository;
import com.dbq.postservice.dto.ListingBody;
import com.dbq.postservice.dto.ListingResponse;
import com.dbq.postservice.dto.MediaUrlDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListingService {
	 private static final Logger log = LoggerFactory.getLogger(ListingService.class);
	
	private final S3StorageClient s3StorageClient;
	private final AdsRepository adsRepository;

	private final ListingRepository listingRepository;

	public String createListing(MultipartFile[] files, ListingBody model) {
		
		ListingCollection savedCollection = new ListingCollection();
		try {
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = currentDateTime.format(formatter);

			ListingCollection listingCollection = new ListingCollection();
			listingCollection.setCreatorId(model.getCreatorId());
			listingCollection.setCreatedAt(formattedDateTime);
			listingCollection.setUpdatedAt("");
			listingCollection.setCondition(model.getCondition());
			listingCollection.setZipCode(model.getZipCode());
			listingCollection.setDescription(model.getDescription());
			listingCollection.setDiscount(model.isIsDiscount());
			listingCollection.setDiscountAmount(model.getDiscountAmount());
			listingCollection.setFree(model.isIsFree());
			listingCollection.setPickupLocation(model.getPickupLocation());
			listingCollection.setPrice(model.getPrice());
			listingCollection.setTitle(model.getTitle());
			listingCollection.setCategory(model.getCategory());
			
			savedCollection = listingRepository.save(listingCollection);
            String listingId =savedCollection.getListingId();
            
            uploadFilesAsync(files, listingId, savedCollection);
            
            return "Listing has been created successfully";
		} catch (Exception e) {
			// Handle exceptions, e.g., log error
			return "Error creating Listing: " + e.getMessage();
		}
	}

    @Async
    public void uploadFilesAsync(MultipartFile[] files, String postId, ListingCollection savedListing) {
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
        
        synchronized (savedListing) {
            
        	savedListing.setMediaPaths(list);
          
         listingRepository.save(savedListing);
        }
    }
	
	public String deleteListings(String userId, String listingId) {

		ListingCollection list = listingRepository.findById(listingId).orElse(null);
		if (list == null) {
			return "Listing not found";
		}

		if (!list.getListingId().equals(userId)) {
			return "User not authorized to delete this Listing";
		}

		// Delete the Listing
		listingRepository.deleteById(listingId);
		return "Listing  deleted successfully";
	}

	public Object updateListings(String userId, String listingId, ListingBody body) {
		try {
			// Fetch the existing Listing
			ListingCollection post = listingRepository.findById(listingId)
					.orElseThrow(() -> new RuntimeException("Listing not found"));

			// Optional: Check if the userId matches the Listing's userId
			if (!post.getCreatorId().equals(userId)) {
				return "User not authorized to update this listing";
			}

			// Update the post's fields
			post.setDescription(body.getDescription());
			post.setFree(body.getIsFree());
			post.setPrice(body.getPrice());
			post.setDiscount(body.getIsDiscount());
			post.setDiscountAmount(body.getDiscountAmount());
			post.setPickupLocation(body.getPickupLocation());

			// Update timestamps
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = currentDateTime.format(formatter);
			post.setUpdatedAt(formattedDateTime); // Update the updatedAt field

			// Save the updated post
			
			return listingRepository.save(post);
		} catch (Exception e) {
			// Handle exceptions, e.g., log error
			return "Error updating post: " + e.getMessage();
		}
	}

	public ListingCollection getListingById(String listingId) {
		return listingRepository.findByListingId(listingId);
	}

//	public List<ListingResponse> getListings(String filterType, String category, Boolean isFree, Boolean isDiscount,
//			Integer pageIndex, Integer pageSize, String searchTerm) {
//		List<ListingResponse> listings = listingRepository.findListings(category, isFree, isDiscount, searchTerm);
//		return listings;
//	}
	
	public List<ListingResponse> getListings(String filterType, String category, Boolean isFree, Boolean isDiscount,
            Integer pageIndex, Integer pageSize, String searchTerm) {
		List<ListingResponse> listings = new ArrayList<>();
		
	//	List<ListingCollection> allListings = listingRepository.findListings(category, isFree, isDiscount, searchTerm);
		
		List<ListingCollection> allListings = listingRepository.findAll();
		int count = allListings.size();
		pageSize = pageSize > 0 ? pageSize-1 : 0;
		
		int startIndex = pageIndex * pageSize;
		
		if (startIndex >= count) {
		return listings;
		}
		
		int endIndex = Math.min(startIndex + pageSize, count);
		
		List<ListingCollection> paginatedListings = allListings.subList(startIndex, endIndex);
		
		List<String> userIds =paginatedListings.stream().map(d->d.getCreatorId()).toList();
		
		
		for (ListingCollection listing : paginatedListings) {
		ListingResponse response = new ListingResponse();
		response.setListingId(listing.getListingId());
		response.setCreatorId(listing.getCreatorId());
		response.setCreatorName(listing.getCreatorName());
		response.setZipCode(listing.getZipCode());
		response.setTitle(listing.getTitle());
		response.setDescription(listing.getDescription());
		response.setIsFree(listing.isFree());
		response.setPrice(listing.getPrice());
		response.setCondition(listing.getCondition());
		response.setIsDiscount(listing.isDiscount());
		response.setDiscountAmount(listing.getDiscountAmount());
		response.setMediaPaths(listing.getMediaPaths());
		response.setCreatedAt(listing.getCreatedAt());
		response.setPickupLocation(listing.getPickupLocation());
		
		listings.add(response);
		}
		
		List<AdsCollection> ads= adsRepository.findAll();
		ListingResponse adsres = new ListingResponse();
		if(null !=ads && ads.size()>0) {
			   AdsCollection randomAd = ads.stream()
                       .skip(new Random().nextInt(ads.size()))
                       .findFirst()
                       .orElse(null);
		if(null !=randomAd) {
			adsres.setDescription(randomAd.getDescription());
			adsres.createdAt(randomAd.getCreatedAt());
			adsres.setMediaPaths(randomAd.getMediaDetails());
			adsres.adsHyperLink(randomAd.getHyperLink());
			
		}
			   
		}
		if( null != adsres && null != adsres.getCreatedAt() && !"".equals(adsres.getCreatedAt()))
			listings.add(adsres);
		
		Collections.shuffle(listings);
		
		return listings; 
		}

}
