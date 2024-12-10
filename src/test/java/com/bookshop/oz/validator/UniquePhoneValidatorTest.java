package com.bookshop.oz.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bookshop.oz.model.Person;
import com.bookshop.oz.service.PersonService;
import com.bookshop.oz.util.AuthUtil;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UniquePhoneValidatorTest {
	@Autowired
	private UniquePhoneValidator validator;

	@MockBean
	private PersonService peopleService;
	@MockBean
	private AuthUtil authUtil;

	private final Person person;
	
	{
		person = new Person().setFirstName("A1").setBpassword("123").setPhone("+375(29)123-45-67").setPersonId(1);
	}
	
	/**
	 * Сценарий: изменение текущего телефона. Пользователь вошел в систему и пытается изменить свои данные. 
	 * При этом номер телефона автоматически проходит валидацию.
	 */
	@Test
	void testUserChangesInfo() {
		when(authUtil.isLoggedIntoSystem()).thenReturn(true);
		when(authUtil.getPersonFromAuth()).thenReturn(person);
		when(peopleService.findUserByPhone("+375(29)123-45-67")).thenReturn(Optional.of(person));
		boolean isValid = validator.isValid("+375(29)123-45-67", null);
		assertTrue(isValid);
	}
	
	/**
	 * Сценарий: изменение текущего телефона. Пользователь регистрируется.
	 * Такой номер уже есть в системе.
	 */
	@Test
	void testRegUser_invalid() {
		when(authUtil.isLoggedIntoSystem()).thenReturn(false);
		when(peopleService.findUserByPhone("+375(29)123-45-67")).thenReturn(Optional.of(person));
		boolean isValid = validator.isValid("+375(29)123-45-67", null);
		assertFalse(isValid);
	}
	
	@Test
	void testRegUser_valid() {
		when(authUtil.isLoggedIntoSystem()).thenReturn(false);
		when(peopleService.findUserByPhone("+375(29)124-44-67")).thenReturn(Optional.empty());
		boolean isValid = validator.isValid("+375(29)124-44-67", null);
		assertTrue(isValid);
	}
}
