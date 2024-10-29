package com.dbq.postservice.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import io.swagger.v3.oas.annotations.media.Schema;

public class ListingFilterDto {
    @JsonProperty("listingType")
    @JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
    private String listingType = null;

    @JsonProperty("filterType")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonSetter(nulls = Nulls.FAIL)
    private String filterType = null;

    @JsonProperty("userId")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonSetter(nulls = Nulls.FAIL)
    private String userId = null;

    @JsonProperty("pageIndex")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonSetter(nulls = Nulls.FAIL)
    private Integer pageIndex = null;

    @JsonProperty("pageSize")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonSetter(nulls = Nulls.FAIL)
    private Integer pageSize = null;

    @JsonProperty("searchContent")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonSetter(nulls = Nulls.FAIL)
    private String searchContent = null;

    public ListingFilterDto listingType(String listingType) {
        this.listingType = listingType;
        return this;
    }

    @Schema(example = "apartment", description = "Type of listing to filter.")
    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public ListingFilterDto filterType(String filterType) {
        this.filterType = filterType;
        return this;
    }

    @Schema(example = "recent", description = "Type of filter to apply on listings.")
    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public ListingFilterDto userId(String userId) {
        this.userId = userId;
        return this;
    }

    @Schema(example = "user123", description = "User ID for filtering listings.")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ListingFilterDto pageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    @Schema(example = "0", description = "Index of the page to retrieve.")
    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public ListingFilterDto pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Schema(example = "10", description = "Number of listings to retrieve per page.")
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public ListingFilterDto searchContent(String searchContent) {
        this.searchContent = searchContent;
        return this;
    }

    @Schema(example = "2-bedroom apartment", description = "Content to search for in listings.")
    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingFilterDto that = (ListingFilterDto) o;
        return Objects.equals(listingType, that.listingType) &&
               Objects.equals(filterType, that.filterType) &&
               Objects.equals(userId, that.userId) &&
               Objects.equals(pageIndex, that.pageIndex) &&
               Objects.equals(pageSize, that.pageSize) &&
               Objects.equals(searchContent, that.searchContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingType, filterType, userId, pageIndex, pageSize, searchContent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ListingFilterDto {\n");
        sb.append("    listingType: ").append(toIndentedString(listingType)).append("\n");
        sb.append("    filterType: ").append(toIndentedString(filterType)).append("\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    pageIndex: ").append(toIndentedString(pageIndex)).append("\n");
        sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
        sb.append("    searchContent: ").append(toIndentedString(searchContent)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
