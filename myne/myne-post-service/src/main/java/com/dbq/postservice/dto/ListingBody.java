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

@Validated
//@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T05:09:37.000195453Z[GMT]")

public class ListingBody {

	@JsonProperty("zipCode")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private String zipCode = null;

	@JsonProperty("title")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private String title = null;

	@JsonProperty("description")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private String description = null;

	@JsonProperty("isFree")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private Boolean isFree = null;

	@JsonProperty("price")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private Float price = null;

	@JsonProperty("condition")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private String condition = null;

	@JsonProperty("isDiscount")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private Boolean isDiscount = null;

	@JsonProperty("discountAmount")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private Double discountAmount = null;

	@JsonProperty("mediaPaths")
	@Valid
	private List<MediaDetailsForRequest> mediaPaths = null;

	@JsonProperty("createdAt")
	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private String createdAt = null;

	@JsonProperty("updatedAt")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private String updatedAt = null;

	@JsonProperty("pickupLocation")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private String pickupLocation = null;

	@JsonProperty("category")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private String category = null;
	
	@JsonProperty("creatorId")

	@JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
	@JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
	private String creatorId = null;


	public String getCategory() {
		return category;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public Boolean getIsDiscount() {
		return isDiscount;
	}

	public ListingBody zipCode(String zipCode) {

		this.zipCode = zipCode;
		return this;
	}

	/**
	 * Zip code of the creator
	 * 
	 * @return zipCode
	 **/

	@Schema(example = "12345", description = "Zip code of the creator")

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public ListingBody title(String title) {

		this.title = title;
		return this;
	}

	/**
	 * Title of the listing
	 * 
	 * @return title
	 **/

	@Schema(example = "sofa", description = "Title of the listing")

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ListingBody description(String description) {

		this.description = description;
		return this;
	}

	/**
	 * Description of the listing
	 * 
	 * @return description
	 **/

	@Schema(example = "Gently used bicycle for sale.", description = "Description of the listing")

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ListingBody isFree(Boolean isFree) {

		this.isFree = isFree;
		return this;
	}

	/**
	 * Indicates if the item is free
	 * 
	 * @return isFree
	 **/

	@Schema(example = "false", description = "Indicates if the item is free")

	public Boolean isIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	public ListingBody price(Float price) {

		this.price = price;
		return this;
	}

	/**
	 * Price of the item
	 * 
	 * @return price
	 **/

	@Schema(example = "100", description = "Price of the item")

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public ListingBody condition(String condition) {

		this.condition = condition;
		return this;
	}

	/**
	 * Condition of the item (Used, Not used, With warranty)
	 * 
	 * @return condition
	 **/

	@Schema(example = "Used", description = "Condition of the item (Used, Not used, With warranty)")

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public ListingBody isDiscount(Boolean isDiscount) {

		this.isDiscount = isDiscount;
		return this;
	}

	/**
	 * Indicates if there is a discount
	 * 
	 * @return isDiscount
	 **/

	@Schema(example = "true", description = "Indicates if there is a discount")

	public Boolean isIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(Boolean isDiscount) {
		this.isDiscount = isDiscount;
	}

	public ListingBody discountAmount(Double discountAmount) {

		this.discountAmount = discountAmount;
		return this;
	}

	/**
	 * Amount of the discount
	 * 
	 * @return discountAmount
	 **/

	@Schema(example = "30", description = "Amount of the discount")

	@Valid
	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public ListingBody mediaPaths(List<MediaDetailsForRequest> mediaPaths) {

		this.mediaPaths = mediaPaths;
		return this;
	}

	public ListingBody addMediaPathsItem(MediaDetailsForRequest mediaPathsItem) {
		if (this.mediaPaths == null) {
			this.mediaPaths = new ArrayList<MediaDetailsForRequest>();
		}
		this.mediaPaths.add(mediaPathsItem);

		return this;
	}

	/**
	 * List of media associated with the listing
	 * 
	 * @return mediaPaths
	 **/

	@Schema(description = "List of media associated with the listing")
	@Valid
	public List<MediaDetailsForRequest> getMediaPaths() {
		return mediaPaths;
	}

	public void setMediaPaths(List<MediaDetailsForRequest> mediaPaths) {
		this.mediaPaths = mediaPaths;
	}

	public ListingBody createdAt(String createdAt) {

		this.createdAt = createdAt;
		return this;
	}

	/**
	 * Creation timestamp
	 * 
	 * @return createdAt
	 **/

	@Schema(example = "2024-10-15T12:34:56Z", description = "Creation timestamp")

	@Valid
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public ListingBody updatedAt(String updatedAt) {

		this.updatedAt = updatedAt;
		return this;
	}

	/**
	 * Updated timestamp
	 * 
	 * @return updatedAt
	 **/

	@Schema(example = "2024-10-15T12:34:56Z", description = "Updated timestamp")

	@Valid
	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ListingBody pickupLocation(String pickupLocation) {

		this.pickupLocation = pickupLocation;
		return this;
	}

	/**
	 * Location where the item can be picked up
	 * 
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
		ListingBody ListingBody = (ListingBody) o;
		return Objects.equals(this.zipCode, ListingBody.zipCode) && Objects.equals(this.title, ListingBody.title)
				&& Objects.equals(this.description, ListingBody.description)
				&& Objects.equals(this.isFree, ListingBody.isFree) && Objects.equals(this.price, ListingBody.price)
				&& Objects.equals(this.condition, ListingBody.condition)
				&& Objects.equals(this.isDiscount, ListingBody.isDiscount)
				&& Objects.equals(this.discountAmount, ListingBody.discountAmount)
				&& Objects.equals(this.mediaPaths, ListingBody.mediaPaths)
				&& Objects.equals(this.createdAt, ListingBody.createdAt)
				&& Objects.equals(this.updatedAt, ListingBody.updatedAt)
				&& Objects.equals(this.pickupLocation, ListingBody.pickupLocation);
	}

	@Override
	public int hashCode() {
		return Objects.hash( zipCode, title, description, isFree, price, condition,
				isDiscount, discountAmount, mediaPaths, createdAt, updatedAt, pickupLocation);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ListingBody {\n");
		
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
		sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
