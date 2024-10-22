package com.dbq.postservice.db.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dbq.postservice.dto.MediaUrlDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "listings")
public class ListingCollection {
	@Id
	private String listingId;
	private String category;
	private String creatorId;
	private String creatorName;
	private String zipCode;
	private String title;
	private String description;
	private boolean isFree;
	private double price;
	private String condition;
	private boolean isDiscount;
	private double discountAmount;
	private List<MediaUrlDetails> mediaPaths;
	private String createdAt;
	private String updatedAt;
	private String pickupLocation;

}
