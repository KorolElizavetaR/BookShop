package com.bookshop.oz.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.security.PersonDetails;

@Component
public class BookAvailabibltyChecker {

	public boolean isAvailable(BookProductDTO bookProductDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return false;
		}
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		
		
		LocationPoint personLocation = person.getLocationPoint();
	}

}
