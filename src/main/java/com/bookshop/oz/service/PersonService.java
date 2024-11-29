package com.bookshop.oz.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.mapper.PersonMapper;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.UserAuthority;
import com.bookshop.oz.model.enumeration.Authority;
import com.bookshop.oz.repository.PersonRepository;
import com.bookshop.oz.repository.UserAuthorityRepository;
import com.bookshop.oz.security.PersonDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService implements UserDetailsService {
	private final PersonRepository peopleRepository;
	private final UserAuthorityRepository userAuthorityRepository;
	private final PasswordEncoder passwordEncoder;
	private final PersonMapper personMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> person = peopleRepository.findByEmail(username);
		if (person.isEmpty())
			throw new UsernameNotFoundException("User with email " + username + " is not found");
		return new PersonDetails(person.get());
	}

	public Optional<Person> findUserByUsername(String username) {
		return peopleRepository.findByEmail(username);
	}

	@Transactional
	public void register(PersonDTORegister personDTO) {
		Person person = personMapper.PersonDTORegisterToPerson(personDTO);
		person.setBpassword(passwordEncoder.encode(personDTO.getPassword()));
		peopleRepository.save(person);
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setPerson(person);
		userAuthority.setUserAuthority(Authority.ROLE_CUSTOMER);
		userAuthorityRepository.save(userAuthority);
	}
}
