package com.dbq.postservice.client;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.dto.MediaDetailsForRequest;
import com.dbq.postservice.dto.MediaUrlDetails;


@FeignClient(name = "s3-storage", path = "/v1/s3-storage")
public interface S3StorageClient {
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadImageToFIleSystem(@RequestPart("image") MultipartFile file);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteImageFromFileSystem(@PathVariable String id);
    
    @PostMapping("/uploadNew/{bucketName}")
	CompletableFuture<List<MediaUrlDetails>> uploadFilesToS3(
	        @RequestBody List<MediaDetailsForRequest> body,
	        @PathVariable String bucketName) ;
    
}