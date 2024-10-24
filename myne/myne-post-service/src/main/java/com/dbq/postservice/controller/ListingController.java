package com.dbq.postservice.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.db.model.ListingCollection;
import com.dbq.postservice.dto.ListingBody;
import com.dbq.postservice.dto.ListingResponse;
import com.dbq.postservice.interfeces.ListingApi;
import com.dbq.postservice.service.ListingService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.RequiredArgsConstructor;


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T08:00:26.821343862Z[GMT]")
@RestController
@RequestMapping("/v1/post")
@RequiredArgsConstructor
public class ListingController implements ListingApi {

	private static final Logger log = LoggerFactory.getLogger(ListingController.class);
    private Gson gson = new GsonBuilder().create();
   
	private final ListingService listingService;
    private final ModelMapper modelMapper;


	@Override
	public ResponseEntity<Object> listingsuserIdSavePost(MultipartFile[] files, String body) {

		try {
			ListingBody pbody = gson.fromJson(body,ListingBody.class);		
			return new ResponseEntity<Object>(listingService.createListing(files, pbody), HttpStatus.OK) {};
		} catch (Exception e) {
			log.error("Couldn't serialize response for content type application/json", e);
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<Object> listingsUserIdListingIdDeleteDelete(String userId, String listingId) {
		ResponseEntity<Object> entity = null;

		try {
			String message = listingService.deleteListings(userId, listingId);

			String listData = "{\"status\":\"" + message + "\"}";
			entity = new ResponseEntity<Object>(listData, HttpStatus.OK) {
			};
			return entity;
		} catch (Exception e) {
			log.error("Couldn't serialize response for content type application/json", e);
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);

		}
	}

	public ResponseEntity<Object> getlistingListingIdGet(@PathVariable("listing_id") String listingId) {
		try {
			ListingCollection response = listingService.getListingById(listingId);
			if (response != null) {
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Error retrieving listing with ID: {}", listingId, e);
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> updatelistingsUserIdListingIdPut(String userId, String listingId,
			@Valid ListingBody body) {

		try {
			
			return new ResponseEntity<Object>(listingService.updateListings(userId, listingId, body), HttpStatus.OK) {};
			
		} catch (Exception e) {
			log.error("Couldn't serialize response for content type application/json", e);
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Object> getlistingsGet(@NotNull @Valid String filterType, @Valid String category,
			@Valid Boolean isFree, @Valid Boolean isDiscount, @Valid Integer pageIndex, @Valid Integer pageSize,
			@Valid String searchTerm) {

		ResponseEntity<Object> entity = null;

		try {
			List<ListingResponse> posts = listingService.getListings(filterType, category, isFree, isDiscount,
					pageIndex, pageSize, searchTerm);
			entity = new ResponseEntity<Object>(posts, HttpStatus.OK) {
			};
			return entity;
		} catch (Exception e) {
			log.error("Couldn't serialize response for content type application/json", e);
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}

	}

}
