/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.63).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.dbq.postservice.interfeces;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T08:00:26.821343862Z[GMT]")
@Validated
public interface AdsApi {

    @Operation(summary = "Create an advertisement", description = "Creates a new advertisement with the provided details.", tags={ "Ads Controller" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Advertisement created successfully", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
 //   @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/ads",
      produces = { "application/json" }, 
      consumes = {"multipart/form-data"},
    method = RequestMethod.POST)
    ResponseEntity<Object> adsPost(@Parameter(in = ParameterIn.DEFAULT, description = "The ID of the user creating the post.", required=false, schema=@Schema()) @RequestParam("files") MultipartFile[] files	, 
    		@Parameter(in = ParameterIn.DEFAULT, description = "The content of the post to create, including description, privacy settings, and media paths.", required=true, schema=@Schema()) @RequestParam("adsInfo") @Valid String body
);

    

}

