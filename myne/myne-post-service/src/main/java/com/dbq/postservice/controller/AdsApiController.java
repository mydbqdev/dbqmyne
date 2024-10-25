package com.dbq.postservice.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.dto.AdsBody;
import com.dbq.postservice.interfeces.AdsApi;
import com.dbq.postservice.service.AdsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.RequiredArgsConstructor;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T08:00:26.821343862Z[GMT]")
@RestController
@RequestMapping("/v1/post")
@RequiredArgsConstructor
public class AdsApiController implements AdsApi {

    private static final Logger log = LoggerFactory.getLogger(AdsApiController.class);

    private Gson gson = new GsonBuilder().create();
    private final ObjectMapper objectMapper;

    
    private final AdsService adsService;


	@Override
	public ResponseEntity<Object> adsPost(MultipartFile[] files, @Valid String body) {
	
		try {
			System.out.println("body>>"+body);
			
			AdsBody adsBody = gson.fromJson(body,AdsBody.class);

	    	return new ResponseEntity<Object>("{\"status\":\"" + adsService.createAdss(files,adsBody) + "\"}", HttpStatus.OK) {};

    } catch (IllegalArgumentException e) { 
        log.error("Bad Request: Invalid arguments provided", e);
        return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (UnsupportedOperationException e) { 
        log.error("Not Implemented: This operation is not supported", e);
        return new ResponseEntity<>("This operation is not supported", HttpStatus.NOT_IMPLEMENTED);
    } catch (Exception e) { 
        log.error("An unexpected error occurred", e);
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
	}

}
