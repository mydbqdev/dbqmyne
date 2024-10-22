package com.dbq.postservice.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
 * ListingResponse
 */
@Validated
//@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T05:09:37.000195453Z[GMT]")


public class ListingResponse   {
  @JsonProperty("listingId")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String listingId = null;

  @JsonProperty("creatorId")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String creatorId = null;

  @JsonProperty("creatorName")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String creatorName = null;

  @JsonProperty("zipCode")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String zipCode = null;

  @JsonProperty("title")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String title = null;

  @JsonProperty("description")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String description = null;

  @JsonProperty("isFree")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Boolean isFree = null;

  @JsonProperty("price")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Float price = null;

  @JsonProperty("condition")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String condition = null;

  @JsonProperty("isDiscount")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Boolean isDiscount = null;

  @JsonProperty("discountAmount")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private BigDecimal discountAmount = null;

  @JsonProperty("mediaPaths")
  @Valid
  private List<MediaUrlDetails> mediaPaths = null;
  @JsonProperty("createdAt")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private OffsetDateTime createdAt = null;

  @JsonProperty("pickupLocation")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String pickupLocation = null;


  public ListingResponse listingId(String listingId) { 

    this.listingId = listingId;
    return this;
  }

  /**
   * Unique identifier for the listing
   * @return listingId
   **/
  
  @Schema(example = "listing_id_1", description = "Unique identifier for the listing")
  
  public String getListingId() {  
    return listingId;
  }



  public void setListingId(String listingId) { 
    this.listingId = listingId;
  }

  public ListingResponse creatorId(String creatorId) { 

    this.creatorId = creatorId;
    return this;
  }

  /**
   * ID of the user who created the listing
   * @return creatorId
   **/
  
  @Schema(example = "43", description = "ID of the user who created the listing")
  
  public String getCreatorId() {  
    return creatorId;
  }



  public void setCreatorId(String creatorId) { 
    this.creatorId = creatorId;
  }

  public ListingResponse creatorName(String creatorName) { 

    this.creatorName = creatorName;
    return this;
  }

  /**
   * Name of the user who created the listing
   * @return creatorName
   **/
  
  @Schema(example = "madhan", description = "Name of the user who created the listing")
  
  public String getCreatorName() {  
    return creatorName;
  }



  public void setCreatorName(String creatorName) { 
    this.creatorName = creatorName;
  }

  public ListingResponse zipCode(String zipCode) { 

    this.zipCode = zipCode;
    return this;
  }

  /**
   * Zip code of the creator
   * @return zipCode
   **/
  
  @Schema(example = "12345", description = "Zip code of the creator")
  
  public String getZipCode() {  
    return zipCode;
  }



  public void setZipCode(String zipCode) { 
    this.zipCode = zipCode;
  }

  public ListingResponse title(String title) { 

    this.title = title;
    return this;
  }

  /**
   * Title of the listing
   * @return title
   **/
  
  @Schema(example = "sofa", description = "Title of the listing")
  
  public String getTitle() {  
    return title;
  }



  public void setTitle(String title) { 
    this.title = title;
  }

  public ListingResponse description(String description) { 

    this.description = description;
    return this;
  }

  /**
   * Description of the listing
   * @return description
   **/
  
  @Schema(example = "Gently used bicycle for sale.", description = "Description of the listing")
  
  public String getDescription() {  
    return description;
  }



  public void setDescription(String description) { 
    this.description = description;
  }

  public ListingResponse isFree(Boolean isFree) { 

    this.isFree = isFree;
    return this;
  }

  /**
   * Indicates if the item is free
   * @return isFree
   **/
  
  @Schema(example = "false", description = "Indicates if the item is free")
  
  public Boolean isIsFree() {  
    return isFree;
  }



  public void setIsFree(Boolean isFree) { 
    this.isFree = isFree;
  }

  public ListingResponse price(Float price) { 

    this.price = price;
    return this;
  }

  /**
   * Price of the item
   * @return price
   **/
  
  @Schema(example = "100", description = "Price of the item")
  
  public Float getPrice() {  
    return price;
  }



  public void setPrice(Float price) { 
    this.price = price;
  }

  public ListingResponse condition(String condition) { 

    this.condition = condition;
    return this;
  }

  /**
   * Condition of the item (Used, Not used, With warranty)
   * @return condition
   **/
  
  @Schema(example = "Used", description = "Condition of the item (Used, Not used, With warranty)")
  
  public String getCondition() {  
    return condition;
  }



  public void setCondition(String condition) { 
    this.condition = condition;
  }

  public ListingResponse isDiscount(Boolean isDiscount) { 

    this.isDiscount = isDiscount;
    return this;
  }

  /**
   * Indicates if there is a discount
   * @return isDiscount
   **/
  
  @Schema(example = "true", description = "Indicates if there is a discount")
  
  public Boolean isIsDiscount() {  
    return isDiscount;
  }



  public void setIsDiscount(Boolean isDiscount) { 
    this.isDiscount = isDiscount;
  }

  public ListingResponse discountAmount(BigDecimal discountAmount) { 

    this.discountAmount = discountAmount;
    return this;
  }

  /**
   * Amount of the discount
   * @return discountAmount
   **/
  
  @Schema(example = "30", description = "Amount of the discount")
  
@Valid
  public BigDecimal getDiscountAmount() {  
    return discountAmount;
  }



  public void setDiscountAmount(BigDecimal discountAmount) { 
    this.discountAmount = discountAmount;
  }

  public ListingResponse mediaPaths(List<MediaUrlDetails> mediaPaths) { 

    this.mediaPaths = mediaPaths;
    return this;
  }

  public ListingResponse addMediaPathsItem(MediaUrlDetails mediaPathsItem) {
    if (this.mediaPaths == null) {
      this.mediaPaths = new ArrayList<MediaUrlDetails>();
    }
    this.mediaPaths.add(mediaPathsItem);
    return this;
  }

  /**
   * List of media associated with the listing
   * @return mediaPaths
   **/
  
  @Schema(description = "List of media associated with the listing")
  @Valid
  public List<MediaUrlDetails> getMediaPaths() {  
    return mediaPaths;
  }



  public void setMediaPaths(List<MediaUrlDetails> mediaPaths) { 
    this.mediaPaths = mediaPaths;
  }

  public ListingResponse createdAt(OffsetDateTime createdAt) { 

    this.createdAt = createdAt;
    return this;
  }

  /**
   * Creation timestamp
   * @return createdAt
   **/
  
  @Schema(example = "2024-10-15T12:34:56Z", description = "Creation timestamp")
  
@Valid
  public OffsetDateTime getCreatedAt() {  
    return createdAt;
  }



  public void setCreatedAt(OffsetDateTime createdAt) { 
    this.createdAt = createdAt;
  }

  public ListingResponse pickupLocation(String pickupLocation) { 

    this.pickupLocation = pickupLocation;
    return this;
  }

  /**
   * Location where the item can be picked up
   * @return pickupLocation
   **/
  
  @Schema(example = "45 Raju St, Springfield, USA", description = "Location where the item can be picked up")
  
  public String getPickupLocation() {  
    return pickupLocation;
  }



  public void setPickupLocation(String pickupLocation) { 
    this.pickupLocation = pickupLocation;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListingResponse ListingResponse = (ListingResponse) o;
    return Objects.equals(this.listingId, ListingResponse.listingId) &&
        Objects.equals(this.creatorId, ListingResponse.creatorId) &&
        Objects.equals(this.creatorName, ListingResponse.creatorName) &&
        Objects.equals(this.zipCode, ListingResponse.zipCode) &&
        Objects.equals(this.title, ListingResponse.title) &&
        Objects.equals(this.description, ListingResponse.description) &&
        Objects.equals(this.isFree, ListingResponse.isFree) &&
        Objects.equals(this.price, ListingResponse.price) &&
        Objects.equals(this.condition, ListingResponse.condition) &&
        Objects.equals(this.isDiscount, ListingResponse.isDiscount) &&
        Objects.equals(this.discountAmount, ListingResponse.discountAmount) &&
        Objects.equals(this.mediaPaths, ListingResponse.mediaPaths) &&
        Objects.equals(this.createdAt, ListingResponse.createdAt) &&
        Objects.equals(this.pickupLocation, ListingResponse.pickupLocation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(listingId, creatorId, creatorName, zipCode, title, description, isFree, price, condition, isDiscount, discountAmount, mediaPaths, createdAt, pickupLocation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListingResponse {\n");
    
    sb.append("    listingId: ").append(toIndentedString(listingId)).append("\n");
    sb.append("    creatorId: ").append(toIndentedString(creatorId)).append("\n");
    sb.append("    creatorName: ").append(toIndentedString(creatorName)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    isFree: ").append(toIndentedString(isFree)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    condition: ").append(toIndentedString(condition)).append("\n");
    sb.append("    isDiscount: ").append(toIndentedString(isDiscount)).append("\n");
    sb.append("    discountAmount: ").append(toIndentedString(discountAmount)).append("\n");
    sb.append("    mediaPaths: ").append(toIndentedString(mediaPaths)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    pickupLocation: ").append(toIndentedString(pickupLocation)).append("\n");
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
