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

	@PostMapping(value="/upload/{bucketName}/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MediaUrlDetails> uploadFile(@PathVariable String bucketName, @PathVariable String postId,
			@RequestPart("file") MultipartFile file
			) {
		return ResponseEntity.ok().body(
				minioService.uploadFile(bucketName,postId,file, file.getOriginalFilename()));

	}
	@PostMapping(value="/upload/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MediaUrlDetails> uploadFile( @PathVariable String postId,@RequestPart("file") MultipartFile file	) {
		return ResponseEntity.ok().body(minioService.uploadFile(postId,file));
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

}