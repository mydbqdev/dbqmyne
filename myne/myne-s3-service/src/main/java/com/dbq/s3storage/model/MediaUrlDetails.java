package com.dbq.s3storage.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

//import com.dbq.postservice.config.NotUndefined;

/**
 * MediaUrlDetails
 */
@Validated
//@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T08:00:26.821343862Z[GMT]")


public class MediaUrlDetails   {
  @JsonProperty("contentType")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String contentType = null;

  @JsonProperty("type")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String type = null;

  @JsonProperty("url")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String url = null;


  public MediaUrlDetails contentType(String contentType) { 

    this.contentType = contentType;
    return this;
  }

  /**
   * The MIME type of the content.
   * @return contentType
   **/
  
 // @Schema(example = "image/jpeg", description = "The MIME type of the content.")
  
  public String getContentType() {  
    return contentType;
  }



  public void setContentType(String contentType) { 
    this.contentType = contentType;
  }

  public MediaUrlDetails type(String type) { 

    this.type = type;
    return this;
  }

  /**
   * The type of the content (e.g., image, video).
   * @return type
   **/
  
  //@Schema(example = "image", description = "The type of the content (e.g., image, video).")
  
  public String getType() {  
    return type;
  }



  public void setType(String type) { 
    this.type = type;
  }

  public MediaUrlDetails url(String url) { 

    this.url = url;
    return this;
  }

  /**
   * The URL where the content can be accessed.
   * @return url
   **/
  
 // @Schema(example = "https://my-bucket.s3.us-west-2.amazonaws.com/images/photo.jpg", description = "The URL where the content can be accessed.")
  
  public String getUrl() {  
    return url;
  }



  public void setUrl(String url) { 
    this.url = url;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MediaUrlDetails MediaUrlDetails = (MediaUrlDetails) o;
    return Objects.equals(this.contentType, MediaUrlDetails.contentType) &&
        Objects.equals(this.type, MediaUrlDetails.type) &&
        Objects.equals(this.url, MediaUrlDetails.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contentType, type, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MediaUrlDetails {\n");
    
    sb.append("    contentType: ").append(toIndentedString(contentType)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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
