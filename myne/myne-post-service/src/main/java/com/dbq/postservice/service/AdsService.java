package com.dbq.postservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dbq.postservice.db.model.AdsCollection;
import com.dbq.postservice.db.repository.AdsRepository;
import com.dbq.postservice.dto.AdsBody;
import com.dbq.postservice.dto.MediaDetailsForRequest;
import com.dbq.postservice.dto.MediaUrlDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdsService {

  private final AdsRepository adsRepository;
 
	 
  public String createAdss(AdsBody model) {
	  
	 AdsCollection adsCollection = new AdsCollection(); 
	 List<MediaUrlDetails> mediaPathDetails = new ArrayList<MediaUrlDetails>();
	 
	 LocalDateTime currentDateTime = LocalDateTime.now();

     // Format the date and time (optional)
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     String formattedDateTime = currentDateTime.format(formatter);
	 
	 adsCollection.setDescription(model.getDescription());
	 adsCollection.setPrivacy(model.getPrivacy());
	 adsCollection.setHyperLink(model.getHyperLink());
	 adsCollection.setCreatedAt(formattedDateTime);
	 
	 for (MediaDetailsForRequest media : model.getMediaDetails()) {
		 
		 MediaUrlDetails mediaDetails = new MediaUrlDetails();
		 
		 mediaDetails.setContentType(media.getContentType());
		 mediaDetails.setType(media.getType());
		 mediaDetails.setUrl("");	 
		
	}
	 adsCollection.setMediaDetails(mediaPathDetails);

	 adsRepository.save(adsCollection);
	  
      
	 return "Advertisement created successfully";

  }
	 
	
}
