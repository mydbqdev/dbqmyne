package com.dbq.authservice.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dbq.authservice.db.models.User;
import com.dbq.utils.MyneUserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements MyneUserDetails {
    
    
    private static final long serialVersionUID = 1L;
	private String id;
    private String username;
    
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String zipcode;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String id,String username, String password,String zipcode,
        Collection<? extends GrantedAuthority> authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      this.zipcode = zipcode;
      this.authorities = authorities;
    }

    public static CustomUserDetails build(User user) {
      List<GrantedAuthority> authorities = user.getRoles().stream()
          .map(role -> new SimpleGrantedAuthority(role.toString()))
          .collect(Collectors.toList());

      return new CustomUserDetails(
          user.getId(), 
          user.getUserEmail(),
          user.getPassword(), 
          user.getZipCode(),
          authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
    }

    public String getId() {
      return id;
    }
  
    @Override
    public String getPassword() {
      return password;
    }

    @Override
    public String getUsername() {
      return username;
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      CustomUserDetails user = (CustomUserDetails) o;
      return Objects.equals(id, user.id);
    }

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	



}
