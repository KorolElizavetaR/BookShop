package com.bookshop.oz.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.exception.LocationNotFoundException;
import com.bookshop.oz.mapper.PersonMapper;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.enumeration.Authority;
import com.bookshop.oz.repository.LocationPointRepository;
import com.bookshop.oz.repository.PersonRepository;
import com.bookshop.oz.util.PersonDetailsSecurity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService implements UserDetailsService {
	private final PersonRepository peopleRepository;
	private final LocationPointRepository locationPointRepository;

	private final PasswordEncoder passwordEncoder;

	private final PersonMapper personMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> person = peopleRepository.findByEmail(username);
		if (person.isEmpty())
			throw new UsernameNotFoundException("User with email " + username + " is not found");
		return new PersonDetailsSecurity(person.get());
	}

	public Optional<Person> findUserByUsername(String username) {
		return peopleRepository.findByEmail(username);
	}

	@Transactional
	public void register(PersonDTORegister personDTO) {
		Person person = personMapper.getPersonFromPersonDTORegister(personDTO);
		person.setBpassword(passwordEncoder.encode(personDTO.getPassword()))
				.setAutorities(Collections.singletonList(Authority.ROLE_CUSTOMER));
		peopleRepository.save(person);
	}

	@Transactional(readOnly = false)
	public void changeLocationPoint(String locationPointID, Person person) {
		LocationPoint locationPoint = locationPointRepository.findById(locationPointID)
				.orElseThrow(() -> new LocationNotFoundException());
		person.setLocationPoint(locationPoint);
	}
}
