package com.bookshop.oz.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bookshop.oz.model.Person;

import jakarta.annotation.PostConstruct;

@Component
public class AuthUtil {

	public boolean isLoggedIntoSystem() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return !(auth.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ANONYMOUS")));
	}

	public Person getPersonFromAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetailsSecurity personDetails = (PersonDetailsSecurity) auth.getPrincipal();
		return personDetails.getPerson();
	}
	
	public boolean isCustomer() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return !(auth.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_CUSTOMER")));
	}
}
