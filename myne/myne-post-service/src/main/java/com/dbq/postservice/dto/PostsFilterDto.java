package com.dbq.postservice.dto;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PostsFilterDto
 */
@Validated
public class PostsFilterDto {

    @JsonProperty("filterType")
    @JsonInclude(JsonInclude.Include.NON_ABSENT) // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL) // FAIL setting if the value is null
    private String filterType = null;

    @JsonProperty("pageIndex")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonSetter(nulls = Nulls.FAIL)
    private Integer pageIndex = null;

    @JsonProperty("pageSize")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonSetter(nulls = Nulls.FAIL)
    private Integer pageSize = null;

    @JsonProperty("zipCode")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonSetter(nulls = Nulls.FAIL)
    private String zipCode = null;

    public PostsFilterDto filterType(String filterType) {
        this.filterType = filterType;
        return this;
    }

    @Schema(example = "recent", description = "Type of filter to apply on posts.")
    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public PostsFilterDto pageIndex(Integer pageIndex) {
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

    public PostsFilterDto pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Schema(example = "10", description = "Number of posts to retrieve per page.")
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PostsFilterDto zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    @Schema(example = "435234", description = "Zip code for filtering posts.")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostsFilterDto that = (PostsFilterDto) o;
        return Objects.equals(filterType, that.filterType) &&
               Objects.equals(pageIndex, that.pageIndex) &&
               Objects.equals(pageSize, that.pageSize) &&
               Objects.equals(zipCode, that.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterType, pageIndex, pageSize, zipCode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PostsFilterDto {\n");
        sb.append("    filterType: ").append(toIndentedString(filterType)).append("\n");
        sb.append("    pageIndex: ").append(toIndentedString(pageIndex)).append("\n");
        sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
        sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
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
