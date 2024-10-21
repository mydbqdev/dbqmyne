package com.dbq.postservice.dto;

import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import io.swagger.v3.oas.annotations.media.Schema;

public class MediaDetailsForRequest {

	@JsonProperty("contentType")

	  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
	  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
	  private String contentType = null;

	  @JsonProperty("type")

	  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
	  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
	  private String type = null;

	  @JsonProperty("uploadFile")

	  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
	  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
	  private MultipartFile uploadFile = null;


	  public MediaDetailsForRequest contentType(String contentType) { 

	    this.contentType = contentType;
	    return this;
	  }

	  /**
	   * The MIME type of the uploaded media.
	   * @return contentType
	   **/
	  
	  @Schema(example = "image/jpeg", description = "The MIME type of the uploaded media.")
	  
	  public String getContentType() {  
	    return contentType;
	  }



	  public void setContentType(String contentType) { 
	    this.contentType = contentType;
	  }

	  public MediaDetailsForRequest type(String type) { 

	    this.type = type;
	    return this;
	  }

	  /**
	   * The type of the media (e.g., image, video).
	   * @return type
	   **/
	  
	  @Schema(example = "image", description = "The type of the media (e.g., image, video).")
	  
	  public String getType() {  
	    return type;
	  }



	  public void setType(String type) { 
	    this.type = type;
	  }

	  public MediaDetailsForRequest uploadFile(MultipartFile uploadFile) { 

	    this.uploadFile = uploadFile;
	    return this;
	  }

	  /**
	   * The content of the uploaded file in a secure format.
	   * @return uploadFile
	   **/
	  
	  @Schema(example = "sadcwe$#C$#RC$T@ygb67u$", description = "The content of the uploaded file in a secure format.")
	  
	  public MultipartFile getUploadFile() {  
	    return uploadFile;
	  }



	  public void setUploadFile(MultipartFile uploadFile) { 
	    this.uploadFile = uploadFile;
	  }

	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    MediaDetailsForRequest adsMediaDetails = (MediaDetailsForRequest) o;
	    return Objects.equals(this.contentType, adsMediaDetails.contentType) &&
	        Objects.equals(this.type, adsMediaDetails.type) &&
	        Objects.equals(this.uploadFile, adsMediaDetails.uploadFile);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(contentType, type, uploadFile);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class AdsMediaDetails {\n");
	    
	    sb.append("    contentType: ").append(toIndentedString(contentType)).append("\n");
	    sb.append("    type: ").append(toIndentedString(type)).append("\n");
	    sb.append("    uploadFile: ").append(toIndentedString(uploadFile)).append("\n");
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
