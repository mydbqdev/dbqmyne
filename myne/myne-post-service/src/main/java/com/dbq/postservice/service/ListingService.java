package com.dbq.postservice.service;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dbq.postservice.client.S3StorageClient;
import com.dbq.postservice.db.model.ListingCollection;
import com.dbq.postservice.db.repository.ListingRepository;
import com.dbq.postservice.dto.ListingBody;
import com.dbq.postservice.dto.ListingResponse;
import com.dbq.postservice.dto.MediaDetailsForRequest;
import com.dbq.postservice.dto.MediaUrlDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListingService {
	@Autowired
	 private FileUploadService fileUploadService ;
	 private final S3StorageClient s3StorageClient;

	  private final ListingRepository listingRepository;

	
	public String createListing(String userId,ListingBody model) {
        try {
         LocalDateTime currentDateTime = LocalDateTime.now();
   	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   	     String formattedDateTime = currentDateTime.format(formatter);
   	     
		 ListingCollection listingCollection = new ListingCollection(); 	 
	     listingCollection.setCreatedAt(formattedDateTime);
	     listingCollection.setUpdatedAt("");
	     listingCollection.setCondition(model.getCondition());
	     listingCollection.setZipCode(model.getZipCode());     
	     listingCollection.setDescription(formattedDateTime);
	     listingCollection.setDiscount(model.isIsDiscount());
	     listingCollection.setDiscountAmount(model.getDiscountAmount());
	     listingCollection.setFree(model.isIsFree());
	     listingCollection.setPickupLocation(model.getPickupLocation());
	     listingCollection.setPrice(model.getPrice());
	     listingCollection.setTitle(model.getTitle());
//         List<MediaUrlDetails>  mediaUrlDetails = new ArrayList<>();
//         for (MediaDetailsForRequest bodyMedia : model.getMediaPaths()) {	 
//         	MediaUrlDetails entyMedia = new MediaUrlDetails();
//         	entyMedia.setContentType(bodyMedia.getContentType());
//        	entyMedia.setType(bodyMedia.getType());
//        	entyMedia.setUrl("");
//		 }
//		 listingCollection.setMediaPaths(mediaUrlDetails);
//		  System.out.println("save>>>");
	     listingCollection.setMediaPaths(new ArrayList<>());
		  ListingCollection savedList= listingRepository.save(listingCollection);
		  
          CompletableFuture <List <MediaUrlDetails>>  mediaUrl= s3StorageClient.uploadFilesToS3(model.getMediaPaths(), "Listings");
          
          if(null != mediaUrl && null != mediaUrl.get() ) {
          	
          	List <MediaUrlDetails> updateUrl= mediaUrl.get();
          	savedList.setMediaPaths(updateUrl);
          	
          	listingRepository.save(savedList);
          }
         return "Listing created successfully";
    } catch (Exception e) {
        // Handle exceptions, e.g., log error
        return "Error creating Listing: " + e.getMessage();
    }
}
  
	 public String deleteListings(String userId,String listingId) {
	    	
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
 
	    public String updateListings(String userId, String listingId, ListingBody body) {
	    	try {
    	        // Fetch the existing Listing
    	        ListingCollection post = listingRepository.findById(listingId)
    	                .orElseThrow(() -> new RuntimeException("Listing not found"));

    	        // Optional: Check if the userId matches the Listing's userId
    	        if (!post.getListingId().equals(userId)) {
    	            return "User not authorized to update this listing";
    	        }

    	        // Update the post's fields
    	        post.setDescription(body.getDescription());

    	        // Update media details if provided
    	        if (body.getMediaPaths() != null) {
    	            List<MediaUrlDetails> mediaUrlDetails = new ArrayList<>();
    	            for (MediaDetailsForRequest bodyMedia : body.getMediaPaths()) {
    	                MediaUrlDetails entyMedia = new MediaUrlDetails();
    	                entyMedia.setContentType(bodyMedia.getContentType());
    	                entyMedia.setType(bodyMedia.getType());
    	                entyMedia.setUrl(""); // You can set the URL if needed
    	                mediaUrlDetails.add(entyMedia);
    	            }
    	            post.setMediaPaths(mediaUrlDetails);
    	        }

    	        // Update timestamps
    	        LocalDateTime currentDateTime = LocalDateTime.now();
    	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	        String formattedDateTime = currentDateTime.format(formatter);
    	        post.setUpdatedAt(formattedDateTime); // Update the updatedAt field

    	        // Save the updated post
    	        listingRepository.save(post);
    	        return "Listing updated successfully";
    	    } catch (Exception e) {
    	        // Handle exceptions, e.g., log error
    	        return "Error updating post: " + e.getMessage();
    	    }
    	}
	    
	    
		 public ListingCollection getListingById(String listingId) {
		        return listingRepository.findByListingId(listingId);
		    }
		 
		 public List<ListingResponse> getListings(String filterType, String category, Boolean isFree, Boolean isDiscount, Integer pageIndex, Integer pageSize, String searchTerm) {
		        PageRequest pageable = PageRequest.of(pageIndex, pageSize);
		        List<ListingResponse> listings = listingRepository.findListings( category, isFree, isDiscount, searchTerm, pageable);	        
		        return listings;
		    }
	 

		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
	 
}
