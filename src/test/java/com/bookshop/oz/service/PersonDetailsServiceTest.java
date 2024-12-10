package com.bookshop.oz.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bookshop.oz.model.Person;
import com.bookshop.oz.repository.PersonRepository;

@SpringBootTest
public class PersonDetailsServiceTest {

	@MockBean
	private PersonRepository personRepository;

	@Autowired
	private PersonService personDetailsService;

	private final String email;
	private final Person mockPerson;

	{
		email = "example@ex.com";
		mockPerson = new Person().setEmail(email);
	}

	@Test
	void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {
		when(personRepository.findByEmail(email)).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> personDetailsService.loadUserByUsername(email));
	}

	@Test
	void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
		when(personRepository.findByEmail(email)).thenReturn(Optional.of(mockPerson));
		UserDetails result = personDetailsService.loadUserByUsername(email);
		assertNotNull(result);
		assertEquals(email, result.getUsername());
		verify(personRepository, times(1)).findByEmail(email);
	}
}
