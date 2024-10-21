package com.dbq.postservice.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

//import com.dbq.postservice.config.NotUndefined;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PostsBody
 */
@Validated
//@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T08:00:26.821343862Z[GMT]")


public class PostsBody   {
	
  @JsonProperty("zipCode")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String zipCode = null; 
	
  @JsonProperty("description")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String description = null;

  @JsonProperty("privacy")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String privacy = null;

  @JsonProperty("mediaDetails")
  @Valid
  private List<MediaDetailsForRequest> mediaDetails = null;

  public PostsBody zipCode(String zipCode) { 

	    this.zipCode = zipCode;
	    return this;
  }
  
  /**
   * The zip code of the user creating the post.
   * @return zipCode
   **/
  
  @Schema(example = "435234", description = "The zip code of the user creating the post.")
  
  public String getZipCode() {  
	return zipCode;
  }


  public void setZipCode(String zipCode) { 
	this.zipCode = zipCode;
  }
  
  public PostsBody description(String description) { 

    this.description = description;
    return this;
  }

  /**
   * The content of the post.
   * @return description
   **/
  
  @Schema(example = "My first post!", description = "The content of the post.")
  
  public String getDescription() {  
    return description;
  }



  public void setDescription(String description) { 
    this.description = description;
  }

  public PostsBody privacy(String privacy) { 

    this.privacy = privacy;
    return this;
  }

  /**
   * The privacy setting for the post.
   * @return privacy
   **/
  
  @Schema(example = "public", description = "The privacy setting for the post.")
  
  public String getPrivacy() {  
    return privacy;
  }



  public void setPrivacy(String privacy) { 
    this.privacy = privacy;
  }

  public PostsBody mediaDetails(List<MediaDetailsForRequest> mediaDetails) { 

    this.mediaDetails = mediaDetails;
    return this;
  }

  public PostsBody addMediaDetailsItem(MediaDetailsForRequest mediaDetailsItem) {
    if (this.mediaDetails == null) {
      this.mediaDetails = new ArrayList<MediaDetailsForRequest>();
    }
    this.mediaDetails.add(mediaDetailsItem);
    return this;
  }

  /**
   * Get mediaDetails
   * @return mediaDetails
   **/
  
  @Schema(description = "")
  @Valid
  public List<MediaDetailsForRequest> getMediaDetails() {  
    return mediaDetails;
  }



  public void setMediaDetails(List<MediaDetailsForRequest> mediaDetails) { 
    this.mediaDetails = mediaDetails;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostsBody PostsBody = (PostsBody) o;
    return Objects.equals(this.zipCode, PostsBody.zipCode) &&
    	Objects.equals(this.description, PostsBody.description) &&
        Objects.equals(this.privacy, PostsBody.privacy) &&
        Objects.equals(this.mediaDetails, PostsBody.mediaDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, privacy, mediaDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostsBody {\n");
    
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    privacy: ").append(toIndentedString(privacy)).append("\n");
    sb.append("    mediaDetails: ").append(toIndentedString(mediaDetails)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
