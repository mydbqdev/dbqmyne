package com.dbq.postservice.db.model;

import java.util.ArrayList;
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
@Document(collection = "myne-posts")
public class PostCollection {
	@Id
	private String postId ;
	private String userId ;
	private String zipCode ;
	private String description ;
	private String[] likdUserIds;
	private List<MediaUrlDetails> mediaDetails = new ArrayList<MediaUrlDetails>() ; 
	private String createdAt ;
	private String updatedAt ;
}
