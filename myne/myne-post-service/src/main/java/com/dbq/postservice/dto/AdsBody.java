package com.dbq.postservice.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

public class AdsBody {

    @JsonProperty("userId")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String userId = null;

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String description = null;

    @JsonProperty("privacy")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String privacy = null;

    @JsonProperty("hyperLink")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String hyperLink = null;

    @JsonProperty("title")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String title = null;

    public AdsBody title(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AdsBody userId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AdsBody description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AdsBody privacy(String privacy) {
        this.privacy = privacy;
        return this;
    }

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

    public String getHyperLink() {
        return hyperLink;
    }

    public void setHyperLink(String hyperLink) {
        this.hyperLink = hyperLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdsBody adsBody = (AdsBody) o;
        return Objects.equals(this.userId, adsBody.userId) &&
               Objects.equals(this.description, adsBody.description) &&
               Objects.equals(this.privacy, adsBody.privacy) &&
               Objects.equals(this.hyperLink, adsBody.hyperLink) &&
               Objects.equals(this.title, adsBody.title); // Include title in equals
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, description, privacy, hyperLink, title); // Include title in hashCode
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AdsBody {\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    privacy: ").append(toIndentedString(privacy)).append("\n");
        sb.append("    hyperLink: ").append(toIndentedString(hyperLink)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n"); // Include title in toString
        sb.append("}");
        return sb.toString();
    }

    // Helper method for formatting output
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
