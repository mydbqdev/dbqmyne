package com.dbq.postservice.service;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadService {

//    private final RestTemplate restTemplate;

    @Async
    public void uploadFileAsync(String url, String bucketName, String contentType, MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create the request entity
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            body.add("file", new InputStreamResource(file.getInputStream()));
        } catch (IOException e) {
            // Handle file input stream exception
            e.printStackTrace();
            return; // Exit if we can't read the file
        }
        
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Make the REST call asynchronously
//        ResponseEntity<String> response = restTemplate.postForEntity(
//                String.format("/upload/{bucketName}/{contentType}", url, bucketName, contentType),
//                requestEntity,
//                String.class
//        );

        // Handle the response as needed
//        if (response.getStatusCode().is2xxSuccessful()) {
//            System.out.println("File uploaded successfully: " + response.getBody());
//        } else {
//            System.out.println("Failed to upload file: " + response.getStatusCode());
//        }
    }
}
