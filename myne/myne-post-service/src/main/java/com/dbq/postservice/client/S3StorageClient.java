package com.dbq.postservice.client;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.PathParam;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.config.FeignConfig;
import com.dbq.postservice.dto.MediaDetailsForRequest;
import com.dbq.postservice.dto.MediaUrlDetails;


@FeignClient(name = "MYNE-S3-SERVICE", 
             path = "/v1/s3-storage", 
            		 configuration = FeignConfig.class)
public interface S3StorageClient {
    @PostMapping(value = "/upload/{bucketName}/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<MediaUrlDetails> uploadFile(@PathVariable  String bucketName,@PathVariable String postId,@RequestPart("file") MultipartFile file);

    @DeleteMapping("/delete/{bucketName}/{id}")
    ResponseEntity<Void> deleteFile(@PathVariable  String bucketName,@PathVariable String id);
    
    @GetMapping("/download/{bucketName}/{id}")
    ResponseEntity<Void> downloadFile(@PathVariable  String bucketName,@PathVariable  String id);
    
    @PostMapping(value = "/upload/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<MediaUrlDetails> uploadFile(@PathVariable String postId,@RequestPart("file") MultipartFile file);

    
}