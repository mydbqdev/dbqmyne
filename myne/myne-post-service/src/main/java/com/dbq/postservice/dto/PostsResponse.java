package com.dbq.postservice.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PostsResponse
 */
@Validated
//@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T08:00:26.821343862Z[GMT]")


public class PostsResponse   {
  @JsonProperty("postId")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String postId = null;

  @JsonProperty("userId")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String userId = null;

  @JsonProperty("creatorName")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String creatorName = null;

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

  @JsonProperty("likeCount")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Integer likeCount = null;

  @JsonProperty("isLiked")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Boolean isLiked = null;

  @JsonProperty("commentsCount")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Integer commentsCount = null;

  @JsonProperty("mediaDetails")
  @Valid
  private List<MediaUrlDetails> mediaDetails = null;
  @JsonProperty("createdAt")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String createdAt = null;

  @JsonProperty("updatedAt")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String updatedAt = null;


  public PostsResponse postId(String postId) { 

    this.postId = postId;
    return this;
  }

  /**
   * The ID of the post
   * @return postId
   **/
  
  @Schema(example = "postId_1", description = "The ID of the post")
  
  public String getPostId() {  
    return postId;
  }



  public void setPostId(String postId) { 
    this.postId = postId;
  }

  public PostsResponse userId(String userId) { 

    this.userId = userId;
    return this;
  }

  /**
   * The ID of the user who created the post
   * @return userId
   **/
  
  @Schema(example = "userId_23", description = "The ID of the user who created the post")
  
  public String getUserId() {  
    return userId;
  }



  public void setUserId(String userId) { 
    this.userId = userId;
  }

  public PostsResponse creatorName(String creatorName) { 

    this.creatorName = creatorName;
    return this;
  }

  /**
   * Name of the user who created the post
   * @return creatorName
   **/
  
  @Schema(example = "madhan", description = "Name of the user who created the post")
  
  public String getCreatorName() {  
    return creatorName;
  }



  public void setCreatorName(String creatorName) { 
    this.creatorName = creatorName;
  }

  public PostsResponse zipCode(String zipCode) { 

    this.zipCode = zipCode;
    return this;
  }

  /**
   * The Zip code of the user who created the post
   * @return zipCode
   **/
  
  @Schema(example = "234523", description = "The Zip code of the user who created the post")
  
  public String getZipCode() {  
    return zipCode;
  }



  public void setZipCode(String zipCode) { 
    this.zipCode = zipCode;
  }

  public PostsResponse description(String description) { 

    this.description = description;
    return this;
  }

  /**
   * The content of the post
   * @return description
   **/
  
  @Schema(example = "My first post!", description = "The content of the post")
  
  public String getDescription() {  
    return description;
  }



  public void setDescription(String description) { 
    this.description = description;
  }

  public PostsResponse privacy(String privacy) { 

    this.privacy = privacy;
    return this;
  }

  /**
   * Privacy level of the post
   * @return privacy
   **/
  
  @Schema(example = "public", description = "Privacy level of the post")
  
  public String getPrivacy() {  
    return privacy;
  }



  public void setPrivacy(String privacy) { 
    this.privacy = privacy;
  }

  public PostsResponse likeCount(Integer likeCount) { 

    this.likeCount = likeCount;
    return this;
  }

  /**
   * like count of the post
   * @return likeCount
   **/
  
  @Schema(example = "12", description = "like count of the post")
  
  public Integer getLikeCount() {  
    return likeCount;
  }



  public void setLikeCount(Integer likeCount) { 
    this.likeCount = likeCount;
  }

  public PostsResponse isLiked(Boolean isLiked) { 

    this.isLiked = isLiked;
    return this;
  }

  /**
   * like deatils of the post
   * @return isLiked
   **/
  
  @Schema(example = "true", description = "like deatils of the post")
  
  public Boolean isIsLiked() {  
    return isLiked;
  }



  public void setIsLiked(Boolean isLiked) { 
    this.isLiked = isLiked;
  }

  public PostsResponse commentsCount(Integer commentsCount) { 

    this.commentsCount = commentsCount;
    return this;
  }

  /**
   * comments count of the post
   * @return commentsCount
   **/
  
  @Schema(example = "12", description = "comments count of the post")
  
  public Integer getCommentsCount() {  
    return commentsCount;
  }



  public void setCommentsCount(Integer commentsCount) { 
    this.commentsCount = commentsCount;
  }

  public PostsResponse mediaDetails(List<MediaUrlDetails> mediaDetails) { 

    this.mediaDetails = mediaDetails;
    return this;
  }

  public PostsResponse addMediaDetailsItem(MediaUrlDetails mediaDetailsItem) {
    if (this.mediaDetails == null) {
      this.mediaDetails = new ArrayList<MediaUrlDetails>();
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
  public List<MediaUrlDetails> getMediaDetails() {  
    return mediaDetails;
  }



  public void setMediaDetails(List<MediaUrlDetails> mediaDetails) { 
    this.mediaDetails = mediaDetails;
  }

  public PostsResponse createdAt(String createdAt) { 

    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of when the post was created
   * @return createdAt
   **/
  
  @Schema(example = "2024-10-15T00:00Z", description = "Timestamp of when the post was created")
  
@Valid
  public String getCreatedAt() {  
    return createdAt;
  }



  public void setCreatedAt(String createdAt) { 
    this.createdAt = createdAt;
  }

  public PostsResponse updatedAt(String updatedAt) { 

    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Timestamp of when the post was updated
   * @return updatedAt
   **/
  
  @Schema(example = "2024-10-15T00:00Z", description = "Timestamp of when the post was updated")
  
@Valid
  public String getUpdatedAt() {  
    return updatedAt;
  }



  public void setUpdatedAt(String updatedAt) { 
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostsResponse PostsResponse = (PostsResponse) o;
    return Objects.equals(this.postId, PostsResponse.postId) &&
        Objects.equals(this.userId, PostsResponse.userId) &&
        Objects.equals(this.creatorName, PostsResponse.creatorName) &&
        Objects.equals(this.zipCode, PostsResponse.zipCode) &&
        Objects.equals(this.description, PostsResponse.description) &&
        Objects.equals(this.privacy, PostsResponse.privacy) &&
        Objects.equals(this.likeCount, PostsResponse.likeCount) &&
        Objects.equals(this.isLiked, PostsResponse.isLiked) &&
        Objects.equals(this.commentsCount, PostsResponse.commentsCount) &&
        Objects.equals(this.mediaDetails, PostsResponse.mediaDetails) &&
        Objects.equals(this.createdAt, PostsResponse.createdAt) &&
        Objects.equals(this.updatedAt, PostsResponse.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(postId, userId, creatorName, zipCode, description, privacy, likeCount, isLiked, commentsCount, mediaDetails, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostsResponse {\n");
    
    sb.append("    postId: ").append(toIndentedString(postId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    creatorName: ").append(toIndentedString(creatorName)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    privacy: ").append(toIndentedString(privacy)).append("\n");
    sb.append("    likeCount: ").append(toIndentedString(likeCount)).append("\n");
    sb.append("    isLiked: ").append(toIndentedString(isLiked)).append("\n");
    sb.append("    commentsCount: ").append(toIndentedString(commentsCount)).append("\n");
    sb.append("    mediaDetails: ").append(toIndentedString(mediaDetails)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
