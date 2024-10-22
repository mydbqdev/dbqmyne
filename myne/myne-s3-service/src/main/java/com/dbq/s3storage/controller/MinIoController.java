package com.dbq.s3storage.controller;

import lombok.RequiredArgsConstructor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.s3storage.exc.GenericErrorResponse;
import com.dbq.s3storage.model.MediaDetailsForRequest;
import com.dbq.s3storage.model.MediaUrlDetails;
import com.dbq.s3storage.model.MimeTypes;
import com.dbq.s3storage.service.MinIoService;

@RestController
@RequestMapping("/v1/s3-storage")
@RequiredArgsConstructor
public class MinIoController {
	private final MinIoService minioService;

	@PostMapping("/bucket/{bucketName}")
	public ResponseEntity<String> createBucket(@PathVariable String bucketName) {
		minioService.createBucket(bucketName);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/upload/{bucketName}/{contentType}")
	public ResponseEntity<String> uploadFileToS3(@RequestPart("file") MultipartFile file,
			@PathVariable String bucketName, @PathVariable String contentType) {
		try {
			return ResponseEntity.ok().body(
					minioService.uploadFile(bucketName, new BufferedInputStream(file.getInputStream()), contentType));
		} catch (IOException e) {
			throw GenericErrorResponse.builder().message("Unable to convert file to stream in upload file")
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping("/download/{bucketName}/{id}")
    public ResponseEntity<Resource> downloadImageFromFileSystem(@PathVariable String bucketName,@PathVariable String id) {
    	Map<String,Object> map = minioService.downloadFile(bucketName, id);
    	MediaType mediaType=null;
    	
    	switch(map.get("mime-type").toString())
    	{
    		case  	MimeTypes.MIME_IMAGE_JPEG:
	    			mediaType = MediaType.IMAGE_JPEG;
	    			break;
    		case	MimeTypes.MIME_IMAGE_PNG:
    				mediaType = MediaType.IMAGE_PNG;
    				break;
    		case	MimeTypes.MIME_IMAGE_GIF:
    				mediaType = MediaType.IMAGE_GIF;
    				break;
    		case	MimeTypes.MIME_APPLICATION_PDF:
					mediaType = MediaType.APPLICATION_PDF;
					break; 				
    		case    MimeTypes.MIME_VIDEO_X_MSVIDEO:
    		case	MimeTypes.MIME_VIDEO_X_MS_WMV:
    		case	MimeTypes.MIME_VIDEO_VND_MPEGURL:
    		case	MimeTypes.MIME_VIDEO_QUICKTIME:
    		case	MimeTypes.MIME_VIDEO_MPEG:
    			mediaType = MediaType.APPLICATION_OCTET_STREAM;
    			break;	
    		default:
    			mediaType = MediaType.APPLICATION_OCTET_STREAM;
    			break; 
    	}
    	
        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(Long.valueOf(map.get("file-size").toString()))
                .body(new InputStreamResource((InputStream)map.get("file-content")));
    }

	@DeleteMapping("/delete/{bucketName}/{id}")
	public ResponseEntity<Void> deleteFileFromBucket(@PathVariable String bucketName, @PathVariable String id) {
		minioService.deleteFile(bucketName, id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/delete/{bucketName}")
	public ResponseEntity<Void> deleteBucket(@PathVariable String bucketName) {
		minioService.deleteBucket(bucketName);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/uploadNew/{bucketName}")
	public CompletableFuture<List<MediaUrlDetails>> uploadFilesToS3(
	        @RequestBody List<MediaDetailsForRequest> body,
	        @PathVariable String bucketName) {

	    List<CompletableFuture<MediaUrlDetails>> futures = new ArrayList<>();

	    try {
	        for (MediaDetailsForRequest element : body) {
	            // Asynchronously upload each file
	            CompletableFuture<MediaUrlDetails> future = CompletableFuture.supplyAsync(() -> {
	                MediaUrlDetails details = new MediaUrlDetails();
	                try {
	                    details.setContentType(element.getContentType());
	                    details.setType(element.getType());
	                    // Upload file and set URL
	                    details.setUrl(minioService.uploadFile(
	                        bucketName,
	                        new BufferedInputStream(element.getUploadFile().getInputStream()),
	                        element.getContentType())
	                    );
	                } catch (Exception e) {
	                    // Handle exception (logging, etc.)
	                    throw new RuntimeException("Error uploading file", e);
	                }
	                return details;
	            });
	            futures.add(future);
	        }

	        // Combine all futures and return the result when all are complete
	        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
	                .thenApply(v -> futures.stream()
	                        .map(CompletableFuture::join)  // Ensure each future completes
	                        .collect(Collectors.toList()));

	    } catch (Exception e) {
	        throw GenericErrorResponse.builder()
	                .message("Unable to upload files to S3")
	                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	                .build();
	    }
	}

}