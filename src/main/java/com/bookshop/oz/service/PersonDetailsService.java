package com.bookshop.oz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookshop.oz.model.Person;
import com.bookshop.oz.repository.PersonRepository;
import com.bookshop.oz.security.PersonDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonDetailsService {
	@Autowired
	private final PersonRepository peopleRepository;

	public PersonDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		Optional<Person> person = peopleRepository.findByEmail(email);
		if (person.isEmpty())
			throw new UsernameNotFoundException("User with email " + email + " is not found");
		return new PersonDetails(person.get());
	}	
}
