package com.dbq.postservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.client.S3StorageClient;
import com.dbq.postservice.db.model.AdsCollection;
import com.dbq.postservice.db.repository.AdsRepository;
import com.dbq.postservice.dto.AdsBody;
import com.dbq.postservice.dto.MediaUrlDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdsService {
	private static final Logger log = LoggerFactory.getLogger(AdsService.class);
	

  private final AdsRepository adsRepository;
  private final S3StorageClient s3StorageClient;
 
	 
  public String createAdss(MultipartFile[] files, AdsBody model) {
	  
	 AdsCollection adsCollection = new AdsCollection(); 
	 
	 LocalDateTime currentDateTime = LocalDateTime.now();

     // Format the date and time (optional)
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     String formattedDateTime = currentDateTime.format(formatter);
	 
     adsCollection.setCreaterId(model.getUserId());
     adsCollection.setTitle(model.getTitle());
	 adsCollection.setDescription(model.getDescription());
	 adsCollection.setPrivacy(model.getPrivacy());
	 adsCollection.setHyperLink(model.getHyperLink());
	 adsCollection.setCreatedAt(formattedDateTime);
	 
	 AdsCollection saveAds= adsRepository.save(adsCollection);
	 
	 uploadFilesAsync(files, saveAds.getId(), saveAds);
      
	 return " Advertisement has been created successfully";

  }
  @Async
  public void uploadFilesAsync(MultipartFile[] files, String postId, AdsCollection savedPost) {
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
      
      synchronized (savedPost) {
          
       savedPost.setMediaDetails(list);
        
       adsRepository.save(savedPost);
      }
  }
	
}
