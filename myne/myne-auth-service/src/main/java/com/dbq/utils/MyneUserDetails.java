package com.dbq.utils;

import org.springframework.security.core.userdetails.UserDetails;

public interface MyneUserDetails extends UserDetails {
	String getZipcode();
	String getId();
}
