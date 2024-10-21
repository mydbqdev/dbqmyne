package com.dbq.postservice.db.model;

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
public class MediaPathDetails1 {

	private String contentType ;
	private String type ;
	private String url ;
	 
	@Override
	public String toString() {
		return "MediaPathDetails [contentType=" + contentType + ", type=" + type + ", url=" + url + "]";
	}
	 
	 
}
