package com.dbq.postservice.dto;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

//import com.dbq.postservice.config.NotUndefined;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * UserProfileDetails
 */
@Validated
//@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T08:00:26.821343862Z[GMT]")


public class UserProfileDetails   {
  @JsonProperty("userId")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String userId = null;

  @JsonProperty("userFirstName")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String userFirstName = null;

  @JsonProperty("userLastName")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String userLastName = null;

  @JsonProperty("zipCode")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String zipCode = null;

  @JsonProperty("address")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String address = null;

  @JsonProperty("profilePicturePath")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String profilePicturePath = null;

  @JsonProperty("bio")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String bio = null;


  public UserProfileDetails userId(String userId) { 

    this.userId = userId;
    return this;
  }

  /**
   * The unique identifier of the user.
   * @return userId
   **/
  
  @Schema(example = "1", description = "The unique identifier of the user.")
  
  public String getUserId() {  
    return userId;
  }



  public void setUserId(String userId) { 
    this.userId = userId;
  }

  public UserProfileDetails userFirstName(String userFirstName) { 

    this.userFirstName = userFirstName;
    return this;
  }

  /**
   * The updated first name of the user.
   * @return userFirstName
   **/
  
  @Schema(example = "Madhan", description = "The updated first name of the user.")
  
  public String getUserFirstName() {  
    return userFirstName;
  }



  public void setUserFirstName(String userFirstName) { 
    this.userFirstName = userFirstName;
  }

  public UserProfileDetails userLastName(String userLastName) { 

    this.userLastName = userLastName;
    return this;
  }

  /**
   * The updated last name of the user.
   * @return userLastName
   **/
  
  @Schema(example = "Kumar", description = "The updated last name of the user.")
  
  public String getUserLastName() {  
    return userLastName;
  }



  public void setUserLastName(String userLastName) { 
    this.userLastName = userLastName;
  }

  public UserProfileDetails zipCode(String zipCode) { 

    this.zipCode = zipCode;
    return this;
  }

  /**
   * The postal code of the user's address.
   * @return zipCode
   **/
  
  @Schema(example = "12345", description = "The postal code of the user's address.")
  
  public String getZipCode() {  
    return zipCode;
  }



  public void setZipCode(String zipCode) { 
    this.zipCode = zipCode;
  }

  public UserProfileDetails address(String address) { 

    this.address = address;
    return this;
  }

  /**
   * The updated address of the user.
   * @return address
   **/
  
  @Schema(example = "123 Main St, Springfield, USA", description = "The updated address of the user.")
  
  public String getAddress() {  
    return address;
  }



  public void setAddress(String address) { 
    this.address = address;
  }

  public UserProfileDetails profilePicturePath(String profilePicturePath) { 

    this.profilePicturePath = profilePicturePath;
    return this;
  }

  /**
   * Get profilePicturePath
   * @return profilePicturePath
   **/
  
  @Schema(description = "")
  
@Valid
  public String getProfilePicturePath() {  
    return profilePicturePath;
  }



  public void setProfilePicturePath(String profilePicturePath) { 
    this.profilePicturePath = profilePicturePath;
  }

  public UserProfileDetails bio(String bio) { 

    this.bio = bio;
    return this;
  }

  /**
   * The updated biography of the user.
   * @return bio
   **/
  
  @Schema(example = "Dog lover and local volunteer.", description = "The updated biography of the user.")
  
  public String getBio() {  
    return bio;
  }



  public void setBio(String bio) { 
    this.bio = bio;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserProfileDetails UserProfileDetails = (UserProfileDetails) o;
    return Objects.equals(this.userId, UserProfileDetails.userId) &&
        Objects.equals(this.userFirstName, UserProfileDetails.userFirstName) &&
        Objects.equals(this.userLastName, UserProfileDetails.userLastName) &&
        Objects.equals(this.zipCode, UserProfileDetails.zipCode) &&
        Objects.equals(this.address, UserProfileDetails.address) &&
        Objects.equals(this.profilePicturePath, UserProfileDetails.profilePicturePath) &&
        Objects.equals(this.bio, UserProfileDetails.bio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, userFirstName, userLastName, zipCode, address, profilePicturePath, bio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserProfileDetails {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    userFirstName: ").append(toIndentedString(userFirstName)).append("\n");
    sb.append("    userLastName: ").append(toIndentedString(userLastName)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    profilePicturePath: ").append(toIndentedString(profilePicturePath)).append("\n");
    sb.append("    bio: ").append(toIndentedString(bio)).append("\n");
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
