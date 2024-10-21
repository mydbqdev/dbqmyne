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
@Document(collection = "myne-ads")
public class AdsCollection {
	@Id
	private String id;
	private String description;
	private List<MediaUrlDetails> mediaDetails;
	private String privacy ;
	private String hyperLink ;
	private String createdAt ;

}