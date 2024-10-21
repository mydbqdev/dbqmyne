package com.dbq.postservice.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import io.swagger.v3.oas.annotations.media.Schema;

public class AdsBody {

	 @JsonProperty("description")

	  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
	  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
	  private String description = null;

	  @JsonProperty("mediaDetails")
	  @Valid
	  private List<MediaDetailsForRequest> mediaDetails = null;
	  @JsonProperty("privacy")

	  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
	  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
	  private String privacy = null;

	  @JsonProperty("hyperLink")

	  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
	  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
	  private String hyperLink = null;


	  public AdsBody description(String description) { 

	    this.description = description;
	    return this;
	  }

	  /**
	   * The description of the advertisement.
	   * @return description
	   **/
	  
	  @Schema(example = "Brand new bicycle for sale!", description = "The description of the advertisement.")
	  
	  public String getDescription() {  
	    return description;
	  }



	  public void setDescription(String description) { 
	    this.description = description;
	  }

	  public AdsBody mediaDetails(List<MediaDetailsForRequest> mediaDetails) { 

	    this.mediaDetails = mediaDetails;
	    return this;
	  }

	  public AdsBody addMediaDetailsItem(MediaDetailsForRequest mediaDetailsItem) {
	    if (this.mediaDetails == null) {
	      this.mediaDetails = new ArrayList<MediaDetailsForRequest>();
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
	  public List<MediaDetailsForRequest> getMediaDetails() {  
	    return mediaDetails;
	  }



	  public void setMediaDetails(List<MediaDetailsForRequest> mediaDetails) { 
	    this.mediaDetails = mediaDetails;
	  }

	  public AdsBody privacy(String privacy) { 

	    this.privacy = privacy;
	    return this;
	  }

	  /**
	   * The privacy setting for the advertisement.
	   * @return privacy
	   **/
	  
	  @Schema(example = "public", description = "The privacy setting for the advertisement.")
	  
	  public String getPrivacy() {  
	    return privacy;
	  }



	  public void setPrivacy(String privacy) { 
	    this.privacy = privacy;
	  }

	  public AdsBody hyperLink(String hyperLink) { 

	    this.hyperLink = hyperLink;
	    return this;
	  }

	  /**
	   * The hyperlink associated with the advertisement.
	   * @return hyperLink
	   **/
	  
	  @Schema(example = "http://example.com/store", description = "The hyperlink associated with the advertisement.")
	  
	  public String getHyperLink() {  
	    return hyperLink;
	  }



	  public void setHyperLink(String hyperLink) { 
	    this.hyperLink = hyperLink;
	  }

	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    AdsBody adsBody = (AdsBody) o;
	    return Objects.equals(this.description, adsBody.description) &&
	        Objects.equals(this.mediaDetails, adsBody.mediaDetails) &&
	        Objects.equals(this.privacy, adsBody.privacy) &&
	        Objects.equals(this.hyperLink, adsBody.hyperLink);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(description, mediaDetails, privacy, hyperLink);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class AdsBody {\n");
	    
	    sb.append("    description: ").append(toIndentedString(description)).append("\n");
	    sb.append("    mediaDetails: ").append(toIndentedString(mediaDetails)).append("\n");
	    sb.append("    privacy: ").append(toIndentedString(privacy)).append("\n");
	    sb.append("    hyperLink: ").append(toIndentedString(hyperLink)).append("\n");
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
