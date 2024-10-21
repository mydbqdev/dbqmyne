package com.dbq.authservice.request;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * RegisterRequest
 */
@Validated
//@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-10-18T08:00:26.821343862Z[GMT]")

@Getter    
public class RegisterRequest {
  @JsonProperty("userEmail")

  private String userEmail = null;

  @JsonProperty("password")

  private String password = null;

  @JsonProperty("userFirstName")

  private String userFirstName = null;

  @JsonProperty("userLastName")

  private String userLastName = null;

  @JsonProperty("zipCode")

  private String zipCode = null;


  public RegisterRequest userEmail(String userEmail) { 

    this.userEmail = userEmail;
    return this;
  }

  /**
   * The email address of the user.
   * @return userEmail
   **/
  
  @Schema(example = "madhan@gmail.com", required = true, description = "The email address of the user.")
  
  @NotNull
  public String getUserEmail() {  
    return userEmail;
  }



  public void setUserEmail(String userEmail) { 

    this.userEmail = userEmail;
  }

  public RegisterRequest password(String password) { 

    this.password = password;
    return this;
  }

  /**
   * The user's password, must be at least 8 characters long.
   * @return password
   **/
  
  @Schema(example = "Madhan1234", required = true, description = "The user's password, must be at least 8 characters long.")
  
  @NotNull
@Size(min=8)   public String getPassword() {  
    return password;
  }



  public void setPassword(String password) { 

    this.password = password;
  }

  public RegisterRequest userFirstName(String userFirstName) { 

    this.userFirstName = userFirstName;
    return this;
  }

  /**
   * The first name of the user.
   * @return userFirstName
   **/
  
  @Schema(example = "Madhan", required = true, description = "The first name of the user.")
  
  @NotNull
  public String getUserFirstName() {  
    return userFirstName;
  }



  public void setUserFirstName(String userFirstName) { 

    this.userFirstName = userFirstName;
  }

  public RegisterRequest userLastName(String userLastName) { 

    this.userLastName = userLastName;
    return this;
  }

  /**
   * The last name of the user.
   * @return userLastName
   **/
  
  @Schema(example = "Kumar", required = true, description = "The last name of the user.")
  
  @NotNull
  public String getUserLastName() {  
    return userLastName;
  }



  public void setUserLastName(String userLastName) { 

    this.userLastName = userLastName;
  }

  public RegisterRequest zipCode(String zipCode) { 

    this.zipCode = zipCode;
    return this;
  }

  /**
   * The postal code of the user's address.
   * @return zipCode
   **/
  
  @Schema(example = "12345", required = true, description = "The postal code of the user's address.")
  
  @NotNull
  public String getZipCode() {  
    return zipCode;
  }



  public void setZipCode(String zipCode) { 

    this.zipCode = zipCode;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegisterRequest RegisterRequest = (RegisterRequest) o;
    return Objects.equals(this.userEmail, RegisterRequest.userEmail) &&
        Objects.equals(this.password, RegisterRequest.password) &&
        Objects.equals(this.userFirstName, RegisterRequest.userFirstName) &&
        Objects.equals(this.userLastName, RegisterRequest.userLastName) &&
        Objects.equals(this.zipCode, RegisterRequest.zipCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userEmail, password, userFirstName, userLastName, zipCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegisterRequest {\n");
    
    sb.append("    userEmail: ").append(toIndentedString(userEmail)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    userFirstName: ").append(toIndentedString(userFirstName)).append("\n");
    sb.append("    userLastName: ").append(toIndentedString(userLastName)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
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
