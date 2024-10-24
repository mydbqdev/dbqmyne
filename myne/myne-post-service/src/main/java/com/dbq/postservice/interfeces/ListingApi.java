package com.dbq.postservice.interfeces;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.dto.ListingBody;
import com.dbq.postservice.dto.ListingFilterDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T05:09:37.000195453Z[GMT]")
@Validated
public interface ListingApi {

	@Operation(summary = "Create a for sale/free post", description = "Creates a new listing with the provided details.", tags = {
			"Listing Controller" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing created successfully", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
	@RequestMapping(value = "/listings/save",
            produces = { "application/json" }, 
            consumes = {"multipart/form-data"}, 
            method = {RequestMethod.PUT,RequestMethod.POST})
	ResponseEntity<Object> listingsuserIdSavePost(
			@Parameter(in = ParameterIn.PATH, description = "The ID of the user creating the listing.", required = true, schema = @Schema()) @RequestParam("files") MultipartFile[] files,
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @RequestParam("listingInfo") @Valid String body);

	@Operation(summary = "Retrieve a specific listing by ID", description = "Retrieves the details of a listing based on the provided listing ID.", tags = {
			"Listing Controller" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing retrieved successfully", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "404", description = "Listing not found", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
	@RequestMapping(value = "/getlisting/{listing_id}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<Object> getlistingListingIdGet(
			@Parameter(in = ParameterIn.PATH, description = "The unique ID of the listing to retrieve.", required = true, schema = @Schema()) @PathVariable("listing_id") String listingId);

//	@Operation(summary = "Retrieve For Sale/Free Items", description = "Retrieves a list of for-sale or free items based on the specified filters and search term.", tags = {
//			"Listing Controller" })
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "List of listings retrieved successfully", content = @Content(mediaType = "application/json")),
//
//			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json")),
//
//			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
//	@RequestMapping(value = "/getlistings", produces = { "application/json" }, method = RequestMethod.GET)
//	ResponseEntity<Object> getlistingsGet(
//			@NotNull @Parameter(in = ParameterIn.QUERY, description = "The type of listings to filter (e.g., \"my Listings\").", required = true, schema = @Schema()) @Valid @RequestParam(value = "filterType", required = true) String filterType,
//			@Parameter(in = ParameterIn.QUERY, description = "Category to filter listings (e.g., \"Electronics\").", schema = @Schema()) @Valid @RequestParam(value = "category", required = false) String category,
//			@Parameter(in = ParameterIn.QUERY, description = "Filter for free items.", schema = @Schema()) @Valid @RequestParam(value = "isFree", required = false) Boolean isFree,
//			@Parameter(in = ParameterIn.QUERY, description = "Filter for discounted items.", schema = @Schema()) @Valid @RequestParam(value = "isDiscount", required = false) Boolean isDiscount,
//			@Parameter(in = ParameterIn.QUERY, description = "Index of the page to retrieve (for pagination).", schema = @Schema()) @Valid @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
//			@Parameter(in = ParameterIn.QUERY, description = "Size of the page to retrieve (for pagination).", schema = @Schema()) @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
//			@Parameter(in = ParameterIn.QUERY, description = "The value to search for in the listings (e.g., \"laptop\", \"furniture\").", schema = @Schema()) @Valid @RequestParam(value = "searchTerm", required = false) String searchTerm);

	
	@Operation(summary = "Retrieve listings", 
	           description = "Fetches listings based on the provided filter criteria.", 
	           tags = { "Listing Controller" })
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Listings retrieved successfully", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
	@RequestMapping(value = "/getlistings", 
	                produces = { "application/json" }, 
	                method = {RequestMethod.PUT,RequestMethod.POST})
	ResponseEntity<Object> getlistingsGet(
	    @Parameter(description = "Filter criteria for retrieving listings.", required = true, schema = @Schema(implementation = ListingFilterDto.class)) 
	    @RequestBody ListingFilterDto listingFilter);

	
	@Operation(summary = "Delete a listing", description = "Deletes a listing using its unique ID.", tags = {
			"Listing Controller" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing deleted successfully", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "400", description = "Invalid request (e.g., listingId not provided or incorrect format)", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "404", description = "Listing not found", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
	@RequestMapping(value = "/listings/{user_id}/{listing_id}/delete", produces = {
			"application/json" },
	method = {RequestMethod.DELETE,RequestMethod.POST})
	ResponseEntity<Object> listingsUserIdListingIdDeleteDelete(
			@Parameter(in = ParameterIn.PATH, description = "The unique identifier of the user.", required = true, schema = @Schema()) @PathVariable("user_id") String userId,
			@Parameter(in = ParameterIn.PATH, description = "The unique identifier of the listing to be deleted.", required = true, schema = @Schema()) @PathVariable("listing_id") String listingId);

	@Operation(summary = "Update a listing", description = "Updates the details of a listing using its unique ID.", tags = {
			"Listing Controller" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing updated successfully", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "404", description = "Listing not found", content = @Content(mediaType = "application/json")),

			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
	@RequestMapping(value = "/updatelistings/{userId}/{listingId}",
//	produces = { "application/json" }, consumes = {
//			"application/json" },
			method = {RequestMethod.PUT,RequestMethod.POST})
	ResponseEntity<Object> updatelistingsUserIdListingIdPut(
			@Parameter(in = ParameterIn.PATH, description = "The unique identifier of the user updating the listing.", required = true, schema = @Schema()) @PathVariable("userId") String userId,
			@Parameter(in = ParameterIn.PATH, description = "The unique identifier of the listing to be updated.", required = true, schema = @Schema()) @PathVariable("listingId") String listingId,
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @ModelAttribute ListingBody body);

	
	
	@Operation(summary = "Search for listings", description = "Retrieves listings based on the provided title search term.", tags = {
    "Listing Controller" })
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Listings retrieved successfully", content = @Content(mediaType = "application/json")),
    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
@RequestMapping(value = "/listings/search",
    produces = { "application/json" },
	method = {RequestMethod.GET,RequestMethod.POST})
ResponseEntity<?> searchListings(
    @Parameter(in = ParameterIn.QUERY, description = "The title to search for listings.", required = true, schema = @Schema()) @RequestParam String title);

	
	
	@Operation(summary = "Search for listings for free", 
	           description = "Retrieves listings based on the provided title search term for items that are free.", 
	           tags = { "Listing Controller" })
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Listings retrieved successfully", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
	    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
	@RequestMapping(value = "/listings/search/free", 
	                produces = { "application/json" }, 
	                method = {RequestMethod.PUT,RequestMethod.POST})
	ResponseEntity<?> searchListingsbyforfree(
	    @Parameter(in = ParameterIn.QUERY, description = "The title to search for listings for free.", required = true, schema = @Schema()) 
	    @RequestParam String title);

	
	
	
}
