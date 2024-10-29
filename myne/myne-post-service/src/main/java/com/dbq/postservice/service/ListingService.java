package com.dbq.postservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.client.S3StorageClient;
import com.dbq.postservice.client.UserStorageClient;
import com.dbq.postservice.db.model.AdsCollection;
import com.dbq.postservice.db.model.ListingCollection;
import com.dbq.postservice.db.repository.AdsRepository;
import com.dbq.postservice.db.repository.ListingRepository;
import com.dbq.postservice.dto.ListingBody;
import com.dbq.postservice.dto.ListingFilterDto;
import com.dbq.postservice.dto.ListingResponse;
import com.dbq.postservice.dto.MediaUrlDetails;
import com.dbq.postservice.dto.PostsFilterDto;
import com.dbq.postservice.dto.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListingService {
	 private static final Logger log = LoggerFactory.getLogger(ListingService.class);
	
	private final S3StorageClient s3StorageClient;
	private final AdsRepository adsRepository;
	private final UserStorageClient userStorageClient;
	private final ListingRepository listingRepository;

	public Object createListing(MultipartFile[] files, ListingBody model) {
		
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
			listingCollection.setDiscount(model.getDiscount());
			listingCollection.setDiscountAmount(model.getDiscountAmount());
			listingCollection.setFree(model.getFree());
			listingCollection.setPickupLocation(model.getPickupLocation());
			listingCollection.setPrice(model.getPrice());
			listingCollection.setTitle(model.getTitle());
			listingCollection.setCategory(model.getCategory());
			
			savedCollection = listingRepository.save(listingCollection);
            String listingId =savedCollection.getListingId();
            
            savedCollection=uploadFilesAsync(files, listingId, savedCollection);
            
            return "Listing has been created successfully";
		} catch (Exception e) {
			// Handle exceptions, e.g., log error
			return "Error creating Listing: " + e.getMessage();
		}
	}

    @Async
    public ListingCollection uploadFilesAsync(MultipartFile[] files, String postId, ListingCollection savedListing) {
        List<MediaUrlDetails> list = new ArrayList<>();
        ListingCollection updateListing=new ListingCollection();
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
        	
        	updateListing=listingRepository.save(savedListing);
          
        }
        return updateListing;
    }
	
	public String deleteListings(String userId, String listingId) {

		ListingCollection list = listingRepository.findById(listingId).orElse(null);
		if (list == null) {
			return "Listing not found";
		}

		if (!list.getListingId().equals(userId)) {
			return "User not authorized to delete this Listing";
		}

		listingRepository.deleteById(listingId);
		return "Listing  deleted successfully";
	}

	public Object updateListings(String userId, String listingId, ListingBody body) {
		try {
		
			ListingCollection post = listingRepository.findById(listingId)
					.orElseThrow(() -> new RuntimeException("Listing not found"));

			if (!post.getCreatorId().equals(userId)) {
				return "User not authorized to update this listing";
			}

			post.setDescription(body.getDescription());
			post.setFree(body.getFree());
			post.setPrice(body.getPrice());
			post.setDiscount(body.getDiscount());
			post.setDiscountAmount(body.getDiscountAmount());
			post.setPickupLocation(body.getPickupLocation());

			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = currentDateTime.format(formatter);
			post.setUpdatedAt(formattedDateTime); // Update the updatedAt field

			
			return listingRepository.save(post);
		} catch (Exception e) {
			return "Error updating post: " + e.getMessage();
		}
	}

	public ListingCollection getListingById(String listingId) {
		return listingRepository.findByListingId(listingId);
	}

	
	public List<ListingResponse> getListingsbysearchterm(PostsFilterDto listingFilter) {
	    List<ListingResponse> list = new ArrayList<>();
	    List<ListingCollection> posts;

	    Pageable pageable = PageRequest.of(listingFilter.getPageIndex(), listingFilter.getPageSize());

	    String searchContent = listingFilter.getSearchContent();
	    if (searchContent == null || searchContent.trim().isEmpty()) {
	        throw new IllegalArgumentException("Search term cannot be null or empty");
	    }
	    
	    String titlefinal = Pattern.quote(searchContent.trim());

	    switch (listingFilter.getFilterType()) {
	        case "forSale":
	            posts = listingRepository.findByListingtitle(titlefinal, pageable);
	            break;

	        case "forfree":
	            posts = listingRepository.findByListingtitleisfree(titlefinal, pageable);
	            break;

	        default:
	            posts = listingRepository.findAll(pageable).getContent();
	            break;
	    }

	    if (!posts.isEmpty()) {
	        list = getListingsAds(posts);
	    }

	    return list;
	}

	
	
    public List<ListingResponse> getListings(ListingFilterDto listingFilter) {
    	
    	List<ListingResponse> list = new ArrayList<ListingResponse>();
    	List<ListingCollection> posts = new ArrayList<ListingCollection>();
    	
    	Pageable pageable = PageRequest.of(listingFilter.getPageIndex(), listingFilter.getPageSize());
    	
    	switch (listingFilter.getListingType()) {
	
    	case "forFree":
    		
    		if("yourlistings".equals(listingFilter.getFilterType()) && !"".equals(listingFilter.getUserId()) && null!=listingFilter.getUserId()   ){
    			
                posts = listingRepository.findFreeListingsByUserId(listingFilter.getUserId(), pageable);
    		}else 
   			 posts = listingRepository.findAllFreeListings(pageable) ;		
    		break;
    		
		case "forSale":
				
			if("yourlistings".equals(listingFilter.getFilterType()) && !"".equals(listingFilter.getUserId()) && null!=listingFilter.getUserId()   ){
    			
                posts = listingRepository.findNotFreeListingsByUserId(listingFilter.getUserId(), pageable);

    		}else 			
			 posts = listingRepository.findAllNotFreeListings(pageable);		
			break;
			
		case "discount":
			if("yourlistings".equals(listingFilter.getFilterType()) && !"".equals(listingFilter.getUserId()) && null!=listingFilter.getUserId()   ){
    			
                posts = listingRepository.findDiscountedListingsByUserId(listingFilter.getUserId(), pageable);

    		}else 			
			 posts = listingRepository.findAllNotFreeListings(pageable);		
			break;
			
		default:
			
    		posts = listingRepository.findAll(pageable).getContent();		
			break;
		}
    	
    	list=getListingsAds(posts);

        return list; 
    }
	
    public List<ListingResponse> getListingsAds(List<ListingCollection> listings) {
		
    	List<String> userIds =new ArrayList<>();
    	List<ListingResponse> list = new ArrayList<>();
    	String userName ="";
    	
    	List<String> getuserIds= listings.stream().map(d->d.getCreatorId()).toList();
    	if(!getuserIds.isEmpty()) {
    		userIds.addAll(getuserIds);
    	}
    	ListingResponse adsres = new ListingResponse();
    	
    	List<AdsCollection> ads= adsRepository.findAll();
    	
    	AdsCollection  randomAd = (null !=ads && ads.size()>0)? (ads.stream()
                           .skip(new Random().nextInt(ads.size()))
                           .findFirst()
                           .orElse(null)):null;
    	
    	if(null !=randomAd && null !=randomAd.getCreaterId() )	 
    	{userIds.add(randomAd.getCreaterId());}
    		
    	ResponseEntity<List<User>> usersDetails= userStorageClient.getUserIdsUserDetails(userIds);
    	   	 
    	List<User>user =usersDetails.getBody();
    			   
    	if(null !=randomAd ) {
    			
    		   userName =user.stream()
        			    .filter(d -> d.getId().equals(randomAd.getCreaterId()))
        			    .map(m -> String.join(" ", m.getUserFirstName(), m.getUserLastName()))
        			    .findFirst()  
        			    .orElse("");
    		   
    			adsres.creatorName(userName);
    			adsres.setTitle(randomAd.getTitle());
    			adsres.setDescription(randomAd.getDescription());
    			adsres.createdAt(randomAd.getCreatedAt());
    			adsres.setMediaPaths(randomAd.getMediaDetails());
    			adsres.adsHyperLink(randomAd.getHyperLink());
    			
    			
    	}

									
		for (ListingCollection listing : listings) {
			
		ListingResponse response = new ListingResponse();
		
		response.setListingId(listing.getListingId());
		response.setZipCode(listing.getZipCode());
		response.setTitle(listing.getTitle());
		response.setDescription(listing.getDescription());
		response.setFree(listing.isFree());
		response.setPrice(listing.getPrice());
		response.setCondition(listing.getCondition());
		response.setdiscount(listing.isDiscount());
		response.setDiscountAmount(listing.getDiscountAmount());
		response.setMediaPaths(listing.getMediaPaths());
		response.setCreatedAt(listing.getCreatedAt());
		response.setPickupLocation(listing.getPickupLocation());
		response.setCreatorId(listing.getCreatorId());
		
		userName = user.stream()
			    .filter(d -> d.getId().equals(listing.getCreatorId()))
			    .map(m -> String.join(" ", m.getUserFirstName(), m.getUserLastName()))
			    .findFirst()  
			    .orElse("");
		
		response.setCreatorName(userName);
		
		list.add(response);
		}
		
		
		if( null != adsres && null != adsres.getCreatedAt() && !"".equals(adsres.getCreatedAt()))
			list.add(adsres);
		
		Collections.shuffle(list);
		
		return list; 
		}
}
