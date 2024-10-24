/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.63).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.dbq.postservice.interfeces;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dbq.postservice.dto.ErrorResponse500;
import com.dbq.postservice.dto.PostsFilterDto;
import com.dbq.postservice.dto.PostsResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T08:00:26.821343862Z[GMT]")
@Validated
public interface PostsApi {
	
	   @Operation(summary = "Create a new post", description = "Allows a user to create a new post with a description, privacy settings, and media (image or video).", tags={ "Posts Controller" })
	    @ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Post created successfully", content = @Content(mediaType = "application/json")),
	        
	        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
	        
	        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json")),
	        
	        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse500.class))) })
	    @RequestMapping(value = "/posts/save",
	        produces = { "application/json" }, 
	        consumes = {"multipart/form-data"}, 
	        method = RequestMethod.POST)
	    ResponseEntity<Object> savePosts(@Parameter(in = ParameterIn.DEFAULT, description = "The ID of the user creating the post.", required=true, schema=@Schema()) @RequestParam("files") MultipartFile[] files	, 
	    		@Parameter(in = ParameterIn.DEFAULT, description = "The content of the post to create, including description, privacy settings, and media paths.", required=true, schema=@Schema()) @RequestParam("postInfo") @Valid String body
	);

//    @Operation(summary = "Get all posts", description = "Fetches all posts along with their details, including comments and media.", tags={ "Posts Controller" })
//    @ApiResponses(value = { 
//        @ApiResponse(responseCode = "200", description = "List of all posts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostsResponse.class))),
//        
//        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
//        
//        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json")),
//        
//        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse500.class))) })
//    @RequestMapping(value = "/posts",
//        produces = { "application/json" }, 
//        method = {RequestMethod.GET,RequestMethod.POST})
//    ResponseEntity<Object> getPosts(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "filterType", required = true) String filterType
//, @Parameter(in = ParameterIn.QUERY, description = "Index of the page to retrieve (for pagination)." ,schema=@Schema()) @Valid @RequestParam(value = "pageIndex", required = false) Integer pageIndex
//, @Parameter(in = ParameterIn.QUERY, description = "Size of the page to retrieve (for pagination)." ,schema=@Schema()) @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize
//, @Parameter(in = ParameterIn.QUERY, description = "Zip code of the user." ,schema=@Schema()) @Valid @RequestParam(value = "zipCode", required = false) String zipCode
//, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "searchTerm", required = false) String searchTerm
//);
    
    @Operation(summary = "Get all posts", description = "Fetches all posts along with their details, including comments and media.", tags={ "Posts Controller" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "List of all posts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostsResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse500.class))) })
    @RequestMapping(value = "/getPosts",
      produces = { "application/json" }, 
        method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity<Object> getPosts(@RequestBody @Valid PostsFilterDto postsFilter) ;


    @Operation(summary = "Delete a post", description = "Deletes an existing post based on the provided post ID.", tags={ "Posts Controller" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Post deleted successfully", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse500.class))) })
    @RequestMapping(value = "/posts/{userId}/{postId}",
        produces = { "application/json" }, 
        		 method = {RequestMethod.DELETE,RequestMethod.POST})
    ResponseEntity<Object> deletePosts(@Parameter(in = ParameterIn.PATH, description = "Allows a user to delete an existing listing .", required=true, schema=@Schema()) @PathVariable("userId") String userId
, @Parameter(in = ParameterIn.PATH, description = "The ID of the post to delete", required=true, schema=@Schema()) @PathVariable("postId") String postId
);


    @Operation(summary = "Update an existing post", description = "Allows a user to update an existing post with a new description and media paths.", tags={ "Posts Controller" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Post created successfully", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse500.class))) })
    @RequestMapping(value = "/posts/{userId}/{postId}",
        produces = { "application/json" }, 
         consumes = {"multipart/form-data"}, 
        		 method = {RequestMethod.PUT,RequestMethod.POST})
    ResponseEntity<Object> updatePosts(@Parameter(in = ParameterIn.PATH, description = "The ID of the user updating the post.", required=true, schema=@Schema()) @PathVariable("userId") String userId
, @Parameter(in = ParameterIn.PATH, description = "The ID of the post to update", required=true, schema=@Schema()) @PathVariable("postId") String postId,
@Parameter(in = ParameterIn.DEFAULT, description = "The ID of the user creating the post.", required=true, schema=@Schema()) @RequestParam("files") MultipartFile[] files	, 
@Parameter(in = ParameterIn.DEFAULT, description = "The content of the post to create, including description, privacy settings, and media paths.", required=true, schema=@Schema()) @RequestParam("postInfo") @Valid String body
);

    
    @Operation(summary = "Like a post", description = "Allows a user to like a specific post.", tags={ "Posts Controller" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfully liked the post", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json")),
        
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse500.class))) })
    @RequestMapping(value = "/posts/{userId}/{postId}/like",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Object> likePost(@Parameter(in = ParameterIn.PATH, description = "The ID of the user updating the post.", required=true, schema=@Schema()) @PathVariable("userId") String userId
, @Parameter(in = ParameterIn.PATH, description = "The ID of the post to update", required=true, schema=@Schema()) @PathVariable("postId") String postId
);
 
    @Operation(summary = "Search for posts", 
            description = "Retrieves posts based on the provided filter criteria.", 
            tags = { "Post Controller" })
 @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(mediaType = "application/json")),
     @ApiResponse(responseCode = "400", description = "Bad request - Invalid input", content = @Content(mediaType = "application/json")),
     @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json")) })
 @RequestMapping(value = "/posts/search", 
                 produces = { "application/json" }, 
                 method = {RequestMethod.GET,RequestMethod.POST})
 ResponseEntity<List<PostsResponse>> searchPosts(
     @Parameter(description = "Filter criteria for retrieving posts.", required = true, schema = @Schema(implementation = PostsFilterDto.class)) 
     @RequestBody PostsFilterDto postsFilter);


}

